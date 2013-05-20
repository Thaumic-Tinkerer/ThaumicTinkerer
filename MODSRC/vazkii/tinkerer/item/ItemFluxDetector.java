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
 * File Created @ [20 May 2013, 22:15:39 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.util.Icon;
import thaumcraft.common.items.ItemThaumometer;
import vazkii.tinkerer.util.helper.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFluxDetector extends ItemThaumometer {

	public ItemFluxDetector(int par1) {
		super(par1);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return iconRing;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return false;
	}
}
