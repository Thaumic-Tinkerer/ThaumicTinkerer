package com.nekokittygames.thaumictinkerer.client.gui.button;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.GuiEnchanter;
import com.nekokittygames.thaumictinkerer.client.misc.EnchantmentGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiEnchantmentButton extends GuiButton {
    public Enchantment enchant;
    protected GuiEnchanter parent;

    public GuiEnchantmentButton(GuiEnchanter parent, int par1, int par2, int par3) {
        super(par1, par2, par3, 16, 16, "");
        this.parent = parent;
    }

    protected boolean dontRender() {
        return enchant == null || !enabled;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (dontRender())
            return;
        ResourceLocation resourceLocation = EnchantmentGui.getEnchantmentIcon(enchant);
        mc.getTextureManager().bindTexture(resourceLocation);
        GlStateManager.enableBlend();
        drawTexturedModalRect16(x, y, 0, 0, 16, 16);
        GlStateManager.disableBlend();
        if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
            List<String> tooltip = new ArrayList();
            tooltip.add(ChatFormatting.AQUA + ThaumicTinkerer.proxy.localize(enchant.getName()));
            parent.getTooltip().addAll(tooltip);
        }
    }

    public void drawTexturedModalRect16(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 1F / 16F;
        float f1 = 1F / 16F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double) (x + 0), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + 0) * f), (double) ((float) (textureY + height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + width) * f), (double) ((float) (textureY + height) * f1)).endVertex();
        bufferbuilder.pos((double) (x + width), (double) (y + 0), (double) this.zLevel).tex((double) ((float) (textureX + width) * f), (double) ((float) (textureY + 0) * f1)).endVertex();
        bufferbuilder.pos((double) (x + 0), (double) (y + 0), (double) this.zLevel).tex((double) ((float) (textureX + 0) * f), (double) ((float) (textureY + 0) * f1)).endVertex();
        tessellator.draw();
    }
}
