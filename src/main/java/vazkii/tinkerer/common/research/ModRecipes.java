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
 * File Created @ [4 Sep 2013, 17:02:48 (GMT)]
 */
package vazkii.tinkerer.common.research;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.common.config.Config;
import vazkii.tinkerer.common.core.handler.ConfigHandler;

public final class ModRecipes {

	public static void initRecipes() {
		initCraftingRecipes();
		initArcaneRecipes();
		initInfusionRecipes();
		initCrucibleRecipes();
	}

	private static void initCraftingRecipes() {

	}

	private static void initArcaneRecipes() {

		if (Config.allowMirrors) {

		}
	}

	private static void initInfusionRecipes() {

		if (ConfigHandler.enableKami) {

			if (Config.allowMirrors) {

			}
		}
	}

	private static void initCrucibleRecipes() {

	}

	public static Object oreDictOrStack(ItemStack stack, String oreDict) {
		return OreDictionary.getOres(oreDict).isEmpty() && ConfigHandler.useOreDictMetal ? stack : oreDict;
	}

}
