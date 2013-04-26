/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [25 Apr 2013, 22:07:40 (GMT)]
 */
package vazkii.tinkerer.potion;

import net.minecraft.potion.Potion;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.util.helper.MiscHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TTPotion extends Potion {

	protected TTPotion(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		MiscHelper.getMc().renderEngine.bindTexture(LibResources.SS_POTIONS);
		return super.getStatusIconIndex();
	}

}
