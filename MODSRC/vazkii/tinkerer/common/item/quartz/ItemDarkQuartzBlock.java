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

import net.minecraft.item.ItemMultiTextureTile;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.lib.LibBlockNames;

public class ItemDarkQuartzBlock extends ItemMultiTextureTile {

	public ItemDarkQuartzBlock(int par1) {
		super(par1, ModBlocks.darkQuartz, new String[] { "" } );
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() >= 3 ? "" : LibBlockNames.DARK_QUARTZ_BLOCK_NAMES[par1ItemStack.getItemDamage()];
	}
}