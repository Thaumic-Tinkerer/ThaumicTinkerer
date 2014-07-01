package vazkii.tinkerer.client.gui.button;

import java.util.List;

/**
 * Created by nekosune on 30/06/14.
 */
public class GuiButtonRPRadio extends GuiButtonRP implements IRadioButton {
    List<IRadioButton> linkedButtons;

    public GuiButtonRPRadio(int par1, int par2, int par3, boolean enabled, List<IRadioButton> linkedButtons) {
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
        return buttonEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.buttonEnabled = enabled;
    }
}
