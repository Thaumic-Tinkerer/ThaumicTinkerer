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
 * File Created @ [25 Apr 2013, 11:46:35 (GMT)]
 */
package vazkii.tinkerer.block;

import net.minecraft.block.Block;
import vazkii.tinkerer.lib.LibBlockIDs;
import vazkii.tinkerer.lib.LibBlockNames;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class ModBlocks {

	public static Block glowstoneGas;

	public static void initBlocks() {
		glowstoneGas = new BlockGlowstoneGas(LibBlockIDs.idGlowstoneGas).setUnlocalizedName(LibBlockNames.GLOWSTONE_GAS);

		registerBlocks();
		nameBlocks();
		registerObjectTags();
	}

	private static void registerBlocks() {
		GameRegistry.registerBlock(glowstoneGas, LibBlockNames.GLOWSTONE_GAS);
	}

	private static void nameBlocks() {
		LanguageRegistry.addName(glowstoneGas, LibBlockNames.GLOWSTONE_GAS_D);
	}

	private static void registerObjectTags() {

	}

}
