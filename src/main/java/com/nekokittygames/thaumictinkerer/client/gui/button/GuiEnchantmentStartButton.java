/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.gui.button;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * The type Gui enchantment start button.
 */
public class GuiEnchantmentStartButton extends GuiButton {

    /**
     * Instantiates a new Gui enchantment start button.
     *
     * @param buttonId the button id
     * @param x        the x
     * @param y        the y
     */
    public GuiEnchantmentStartButton(int buttonId, int x, int y) {
        super(buttonId, x, y, "");
        this.width=15;
    }

    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(LibClientResources.GUI_ENCHANTER);
        drawTexturedModalRect(x, y, 176, enabled ? 39 : 24, 15, 15);
    }
}
