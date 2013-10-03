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
 * File Created @ [8 Sep 2013, 16:13:21 (GMT)]
 */
package vazkii.tinkerer.common.item.quartz;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.item.ItemSlab;
import vazkii.tinkerer.common.block.ModBlocks;

public class ItemDarkQuartzSlab extends ItemSlab {

	public ItemDarkQuartzSlab(int par1) {
		super(par1, (BlockHalfSlab) ModBlocks.darkQuartzSlab, (BlockHalfSlab) ModBlocks.darkQuartzSlabFull, par1 == ModBlocks.darkQuartzSlabFull.blockID);
	}

}

