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
import vazkii.tinkerer.common.registry.ITTinkererBlock;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;

public class BlockDarkQuartzStairs extends BlockStairs implements ITTinkererBlock {

	public BlockDarkQuartzStairs() {
		super(ModBlocks.darkQuartz, 0);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return null;
	}

	@Override
	public boolean shouldRegister() {
		return false;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return false;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return null;
	}
}