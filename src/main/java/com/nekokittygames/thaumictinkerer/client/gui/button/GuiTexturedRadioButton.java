package com.nekokittygames.thaumictinkerer.client.gui.button;

import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Textured version of a radio button
 */
public class GuiTexturedRadioButton extends GuiTexturedButton implements IRadioButton {
    private List<IRadioButton> linkedButtons;

    /**
     * @param buttonId        id of the button
     * @param x               xPos of the button
     * @param y               yPos of the button
     * @param textureLocation texture Location for the button
     * @param enabled         is the button enabled?
     */
    public GuiTexturedRadioButton(int buttonId, int x, int y, ResourceLocation textureLocation, boolean enabled, List<IRadioButton> linkedButtons) {
        super(buttonId, x, y, textureLocation, enabled);
        this.linkedButtons = linkedButtons;
    }

    /**
     * enable a single button from a button click
     */
    @Override
    public void enableFromClick() {
        setEnabled(true);
        for (IRadioButton button : linkedButtons)
            if (button != this)
                button.updateStatus(this);
    }

    /**
     * Updates the button based on another's changed status
     *
     * @param otherButton button who's status changed
     */
    @Override
    public void updateStatus(IRadioButton otherButton) {
        if (otherButton.isEnabled())
            setEnabled(false);
    }

    /**
     * @return is this button enabled?
     */
    @Override
    public boolean isEnabled() {
        return isButtonEnabled();
    }

    /**
     * @param enabled Is the button enabled?
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.setButtonEnabled(enabled);
    }
}
