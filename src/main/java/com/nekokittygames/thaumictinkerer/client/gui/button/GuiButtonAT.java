package com.nekokittygames.thaumictinkerer.client.gui.button;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonAT extends GuiButton {
    public boolean enabled = false;


    public GuiButtonAT(int par1, int par2, int par3, boolean enabled) {
        super(par1, par2, par3, 13, 13, "");
        this.enabled = enabled;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(LibClientResources.GUI_ANIMATION_TABLET);
        int y = enabled ? 13 : 0;
        drawTexturedModalRect(x, this.y, 176, y, width, height);
    }
}
