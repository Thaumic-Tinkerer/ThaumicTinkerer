package com.nekokittygames.thaumictinkerer.client.gui.button;

/**
 * Radio button interface
 */
public interface IRadioButton {

    /**
     * enable a single button from a button click
     */
    void enableFromClick();

    /**
     * Updates the button based on another's changed status
     *
     * @param otherButton button who's status changed
     */
    void updateStatus(IRadioButton otherButton);

    /**
     * @return is this button enabled?
     */
    boolean isEnabled();

    /**
     * @param enabled Is the button enabled?
     */
    void setEnabled(boolean enabled);
}
