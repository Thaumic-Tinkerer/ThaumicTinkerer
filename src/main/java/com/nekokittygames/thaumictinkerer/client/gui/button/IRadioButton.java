package com.nekokittygames.thaumictinkerer.client.gui.button;

public interface IRadioButton {
    public void enableFromClick();

    public void updateStatus(IRadioButton otherButton);

    public boolean isEnabled();

    public void setEnabled(boolean enabled);
}
