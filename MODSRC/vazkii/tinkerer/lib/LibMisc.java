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
 * File Created @ [24 Apr 2013, 20:38:31 (GMT)]
 */
package vazkii.tinkerer.lib;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import thaumcraft.common.Config;

public final class LibMisc {

	public static final String MOD_ID = "ThaumicTinkerer";
	public static final String MOD_NAME = "Thaumic Tinkerer";
	public static final String MOD_VERSION = "1.0.7";

	public static final String DEPENDENCIES = "required-after:Thaumcraft";

	public static final String COMMON_PROXY = "vazkii.tinkerer.core.proxy.TTCommonProxy";
	public static final String CLIENT_PROXY = "vazkii.tinkerer.core.proxy.TTClientProxy";

	public static final short CRAFTING_META_WILDCARD = Short.MAX_VALUE;
	public static final short TAG_META_WILDCARD = -1;

	public static final float MODEL_DEFAULT_RENDER_SCALE = 0.0625F;
	
	public static final int[] filterItems = new int[] {
		Item.beefRaw.itemID,			// Adult Cow
		Config.itemNuggetBeef.itemID,		// Baby Cow
		Item.porkRaw.itemID, 			// Adult Pig
		Config.itemNuggetPork.itemID, 		// Baby Pig
		Item.chickenRaw.itemID, 		// Adult Chicken
		Config.itemNuggetChicken.itemID,	// Baby Chicken
		Item.silk.itemID,			// Baby Sheep
		Item.pumpkinPie.itemID,			// Bat
		Item.fishRaw.itemID,			// Adult Ocelot
		Item.fishCooked.itemID,			// Baby Ocelot
		Item.stick.itemID, 			// Adult Wolf
		Item.cookie.itemID,			// Baby Wolf
		
	};
	public static final int[] filterBlocks = new int[] {
		Block.cloth.blockID,			// Adult Sheep
		Block.mushroomRed.blockID,		// Adult Mooshroom
		Block.mushroomBrown.blockID,		// Baby Mooshroom
		
	};

	public static final String[] filterItemNames = new String[] {
		"Adult Cow",
		"Baby Cow",
		"Adult Pig",
		"Baby Pig",
		"Adult Chicken",
		"Baby Chicken",
		"Baby Sheep",
		"Bat",
		"Adult Ocelot",
		"Baby Ocelot",
		"Adult Wolf", 
		"Baby Wolf",
		
	};
	public static final String[] filterBlockNames = new String[] {
		"Adult Sheep", 
		"Adult Mooshroom", 
		"Baby Mooshroom",
		
	};
}
