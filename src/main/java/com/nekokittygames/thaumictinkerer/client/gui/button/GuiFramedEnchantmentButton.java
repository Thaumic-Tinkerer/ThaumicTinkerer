package com.nekokittygames.thaumictinkerer.client.gui.button;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.GuiEnchanter;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;

public class GuiFramedEnchantmentButton extends GuiEnchantmentButton {


    public GuiFramedEnchantmentButton(GuiEnchanter parent, int par1, int par2, int par3) {
        super(parent, par1, par2, par3);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (dontRender() || parent.getEnchanter().getEnchantments().isEmpty() || parent.getEnchanter().getLevels().isEmpty())
            return;

        mc.getTextureManager().bindTexture(LibClientResources.GUI_ENCHANTER);
        drawTexturedModalRect(x - 4, y - 4, 176, 0, 24, 24);

        int index = parent.getEnchanter().getEnchantments().indexOf(Enchantment.getEnchantmentID(enchant));
        if (index != -1) {
            int level = parent.getEnchanter().getLevels().get(index);
            Minecraft.getMinecraft().fontRenderer.drawString(ThaumicTinkerer.proxy.localize("enchantment.level." + level), x + 26, y + 8, 0xFFFFFF, true);
        }
        super.drawButton(mc, mouseX, mouseY, partialTicks);
    }


}
