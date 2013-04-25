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
 * File Created @ [24 Apr 2013, 22:54:50 (GMT)]
 */
package vazkii.tinkerer.util.helper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibMisc;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ModCreativeTab extends CreativeTabs {

	public static final ModCreativeTab INSTANCE = new ModCreativeTab();

	public ModCreativeTab() {
		super(LibMisc.MOD_ID);
		LanguageRegistry.instance().addStringLocalization("itemGroup." + LibMisc.MOD_ID, LibMisc.MOD_NAME);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return ModItems.wandTinkerer;
	}
}
