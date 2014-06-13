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
 * File Created @ [8 Sep 2013, 16:12:29 (GMT)]
 */
package vazkii.tinkerer.common.item.quartz;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.quartz.BlockDarkQuartz;
import vazkii.tinkerer.common.lib.LibBlockNames;

//Changed from ItemMultiTextureTile to ItemMultiTexture for 1.7
public class ItemDarkQuartzBlock extends ItemMultiTexture {

	public ItemDarkQuartzBlock(Block par1) {
		super(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), new String[]{ "" });
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() >= 3 ? "" : LibBlockNames.DARK_QUARTZ_BLOCK_NAMES[par1ItemStack.getItemDamage()];
	}
}