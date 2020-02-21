/*
 * Copyright (c) 2020. Katrina Knight (nekosune).
 */

package com.nekokittygames.thaumictinkerer.client.gui.button;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.jetbrains.annotations.NotNull;

/**
 * The Gui enchantment level button.
 */
public class GuiEnchantmentLevelButton extends GuiButton {


    /**
     * Is this a plus or minus button.
     */
    public boolean plus;

    /**
     * Instantiates a new enchantment level button.
     *
     * @param buttonId the button id
     * @param x        the X position of the button
     * @param y        the Y position of the button
     * @param plus     is this button a plus or minus
     */
    public GuiEnchantmentLevelButton(int buttonId, int x, int y, boolean plus) {
        super(buttonId, x, y, 7, 7, "");
        this.plus = plus;
    }


    /**
     *
     * Draws the button to screen
     *
     * @param mc {@link Minecraft} instance
     * @param mouseX X Position of the mouse
     * @param mouseY Y Position of the mouse
     * @param partialTicks number of ticks since last draw
     */
    @Override
    public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!enabled)
            return;

        int xPos = 200 + (plus ? 7 : 0);

        mc.getTextureManager().bindTexture(LibClientResources.GUI_ENCHANTER);
        drawTexturedModalRect(this.x, this.y, xPos, 0, 7, 7);
    }

}
