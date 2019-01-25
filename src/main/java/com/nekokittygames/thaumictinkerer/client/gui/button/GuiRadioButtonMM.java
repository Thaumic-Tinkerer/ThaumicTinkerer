package com.nekokittygames.thaumictinkerer.client.gui.button;

import java.util.List;

public class GuiRadioButtonMM extends GuiButtonMM implements IRadioButton {

    private List<IRadioButton> linkedButtons;

    public GuiRadioButtonMM(int par1, int par2, int par3, boolean enabled, List<IRadioButton> linkedButtons) {
        super(par1, par2, par3, enabled);
        this.linkedButtons = linkedButtons;
    }

    @Override
    public void enableFromClick() {
        setEnabled(true);
        for (IRadioButton button : linkedButtons)
            if (button != this)
                button.updateStatus(this);
    }

    @Override
    public void updateStatus(IRadioButton otherButton) {
        if (otherButton.isEnabled())
            setEnabled(false);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
