/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [12 Sep 2013, 18:40:22 (GMT)]
 */
package thaumic.tinkerer.client.gui.button;

import java.util.List;

public class GuiButtonMMRadio extends GuiButtonMM implements IRadioButton {

    List<IRadioButton> linkedButtons;

    public GuiButtonMMRadio(int par1, int par2, int par3, boolean enabled, List<IRadioButton> linkedButtons) {
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