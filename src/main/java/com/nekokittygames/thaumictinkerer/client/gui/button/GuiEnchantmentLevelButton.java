package com.nekokittygames.thaumictinkerer.client.gui.button;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiEnchantmentLevelButton extends GuiButton {


    public boolean plus;

    public GuiEnchantmentLevelButton(int buttonId, int x, int y,boolean plus) {
        super(buttonId, x, y,7,7, "");
        this.plus=plus;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!enabled)
            return;

        int xPos = 200 + (plus ? 7 : 0);

        mc.getTextureManager().bindTexture(LibClientResources.GUI_ENCHANTER);
        drawTexturedModalRect(this.x, this.y, xPos, 0, 7, 7);
    }

}
