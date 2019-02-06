package com.nekokittygames.thaumictinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Gui button that uses a preset texture rather then inbuilt
 */
public class GuiTexturedButton extends GuiButton {
    private ResourceLocation textureLocation;
    private boolean buttonEnabled;

    /**
     * @param buttonId        id of the button
     * @param x               xPos of the button
     * @param y               yPos of the button
     * @param textureLocation texture Location for the button
     * @param enabled         is the button enabled?
     */
    public GuiTexturedButton(int buttonId, int x, int y, ResourceLocation textureLocation, boolean enabled) {
        super(buttonId, x, y, 13, 13, "");
        this.textureLocation = textureLocation;
        this.buttonEnabled = enabled;
    }

    /**
     * @return the button's texture location
     */
    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    /**
     * @param textureLocation the required texture location
     */
    public void setTextureLocation(ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }

    /**
     * Is the button enabled?
     *
     * @return status of the button
     */
    public boolean isButtonEnabled() {
        return buttonEnabled;
    }

    /**
     * Sets the enabled status of the button
     *
     * @param buttonEnabled status of the button
     */
    public void setButtonEnabled(boolean buttonEnabled) {
        this.buttonEnabled = buttonEnabled;
    }

    /**
     * @param mc           Minecraft instance
     * @param mouseX       xPos of the mouse
     * @param mouseY       yPos of the mouse
     * @param partialTicks how many ticks
     */
    @Override
    public void drawButton(@NotNull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(textureLocation);
        int y = buttonEnabled ? 13 : 0;
        drawTexturedModalRect(x, this.y, 176, y, width, height);
    }
}
