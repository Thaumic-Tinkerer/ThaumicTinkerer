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
 * File Created @ [4 Sep 2013, 16:57:28 (GMT)]
 */
package vazkii.tinkerer.common.item;

import net.minecraft.item.Item;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.lib.LibItemIDs;
import vazkii.tinkerer.common.lib.LibItemNames;

public final class ModItems {

	public static Item darkQuartz;
	public static Item connector;
	public static Item gaseousLight;
	public static Item gaseousShadow;
	public static Item gasRemover;
	public static Item spellCloth;

	public static void initItems() {
		darkQuartz = new ItemMod(LibItemIDs.idDarkQuartz).setUnlocalizedName(LibItemNames.DARK_QUARTZ);
		connector = new ItemConnector(LibItemIDs.idConnector).setUnlocalizedName(LibItemNames.CONNECTOR);
		gaseousLight = new ItemGas(LibItemIDs.idGaseousLight, ModBlocks.gaseousLight).setUnlocalizedName(LibItemNames.GASEOUS_LIGHT);
		gaseousShadow = new ItemGas(LibItemIDs.idGaseousShadow, ModBlocks.gaseousShadow).setUnlocalizedName(LibItemNames.GASEOUS_SHADOW);
		gasRemover = new ItemGasRemover(LibItemIDs.idGasRemover).setUnlocalizedName(LibItemNames.GAS_REMOVER);
		spellCloth = new ItemSpellCloth(LibItemIDs.idSpellCloth).setUnlocalizedName(LibItemNames.SPELL_CLOTH);
	}

}
