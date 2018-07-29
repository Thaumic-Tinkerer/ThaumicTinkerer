package com.nekokittygames.thaumictinkerer.client.gui.button;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiEnchantmentStartButton extends GuiButton {
    private boolean enabled;
    public GuiEnchantmentStartButton(int buttonId, int x, int y) {
        super(buttonId, x, y, "");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(LibClientResources.GUI_ENCHANTER);
        drawTexturedModalRect(x , y , 176, enabled?39:24, 15, 15);
    }
}
