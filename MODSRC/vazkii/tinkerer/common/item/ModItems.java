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
import vazkii.tinkerer.common.item.foci.ItemFocusDislocation;
import vazkii.tinkerer.common.item.foci.ItemFocusFlight;
import vazkii.tinkerer.common.item.foci.ItemFocusSmelt;
import vazkii.tinkerer.common.item.foci.ItemFocusTelekinesis;
import vazkii.tinkerer.common.lib.LibItemIDs;
import vazkii.tinkerer.common.lib.LibItemNames;

public final class ModItems {

	public static Item darkQuartz;
	public static Item connector;
	public static Item gaseousLight;
	public static Item gaseousShadow;
	public static Item gasRemover;
	public static Item spellCloth;
	public static Item focusFlight;
	public static Item focusDislocation;
	public static Item cleansingTalisman;
	public static Item brightNitor;
	public static Item focusTelekinesis;
	public static Item soulMould;
	public static Item xpTalisman;
	public static Item focusSmelt;
	
	public static void initItems() {
		darkQuartz = new ItemMod(LibItemIDs.idDarkQuartz).setUnlocalizedName(LibItemNames.DARK_QUARTZ);
		connector = new ItemConnector(LibItemIDs.idConnector).setUnlocalizedName(LibItemNames.CONNECTOR);
		gaseousLight = new ItemGas(LibItemIDs.idGaseousLight, ModBlocks.gaseousLight).setUnlocalizedName(LibItemNames.GASEOUS_LIGHT);
		gaseousShadow = new ItemGas(LibItemIDs.idGaseousShadow, ModBlocks.gaseousShadow).setUnlocalizedName(LibItemNames.GASEOUS_SHADOW);
		gasRemover = new ItemGasRemover(LibItemIDs.idGasRemover).setUnlocalizedName(LibItemNames.GAS_REMOVER);
		spellCloth = new ItemSpellCloth(LibItemIDs.idSpellCloth).setUnlocalizedName(LibItemNames.SPELL_CLOTH);
		focusFlight = new ItemFocusFlight(LibItemIDs.idFocusFlight).setUnlocalizedName(LibItemNames.FOCUS_FLIGHT);
		focusDislocation = new ItemFocusDislocation(LibItemIDs.idFocusDislocation).setUnlocalizedName(LibItemNames.FOCUS_DISLOCATION);
		cleansingTalisman = new ItemCleansingTalisman(LibItemIDs.idCleansingTalisman).setUnlocalizedName(LibItemNames.CLEANSING_TALISMAN);
		brightNitor = new ItemBrightNitor(LibItemIDs.idBrightNitor).setUnlocalizedName(LibItemNames.BRIGHT_NTIOR);
		focusTelekinesis = new ItemFocusTelekinesis(LibItemIDs.idFocusTelekinesis).setUnlocalizedName(LibItemNames.FOCUS_TELEKINESIS);
		soulMould = new ItemSoulMould(LibItemIDs.idSoulMould).setUnlocalizedName(LibItemNames.SOUL_MOULD);
		xpTalisman = new ItemXPTalisman(LibItemIDs.idXPTalisman).setUnlocalizedName(LibItemNames.XP_TALISMAN);
		focusSmelt = new ItemFocusSmelt(LibItemIDs.idFocusSmelt).setUnlocalizedName(LibItemNames.FOCUS_SMELT);
	}
}
