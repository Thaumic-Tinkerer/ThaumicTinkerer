/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [13 May 2013, 21:47:52 (GMT)]
 */
package vazkii.tinkerer.client.gui.button;

public interface IRadioButton {

	public void enableFromClick();

	public void updateStatus(IRadioButton otherButton);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

}
