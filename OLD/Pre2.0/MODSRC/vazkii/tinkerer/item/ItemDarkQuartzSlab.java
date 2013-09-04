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
 * File Created @ [7 May 2013, 17:19:52 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.lib.LibBlockNames;

public class ItemDarkQuartzSlab extends ItemSlab {

	public ItemDarkQuartzSlab(int par1) {
		super(par1, (BlockHalfSlab) ModBlocks.darkQuartzSlab, (BlockHalfSlab) ModBlocks.darkQuartzSlabFull, par1 == ModBlocks.darkQuartzSlabFull.blockID);
	}



	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return LibBlockNames.DARK_QUARTZ_SLAB_D;
	}

}