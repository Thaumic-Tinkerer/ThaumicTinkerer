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
 * File Created @ [8 Sep 2013, 16:02:19 (GMT)]
 */
package vazkii.tinkerer.common.block.quartz;

import net.minecraft.block.BlockStairs;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;

public class BlockDarkQuartzStairs extends BlockStairs {

	public BlockDarkQuartzStairs(int par1) {
		super(par1, ModBlocks.darkQuartz, 0);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}
}