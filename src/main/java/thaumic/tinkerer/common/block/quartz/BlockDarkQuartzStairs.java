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
package thaumic.tinkerer.common.block.quartz;

import net.minecraft.block.BlockStairs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererBlock;
import thaumic.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipeMulti;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;

public class BlockDarkQuartzStairs extends BlockStairs implements ITTinkererBlock {

	public BlockDarkQuartzStairs() {
		super(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), 0);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.DARK_QUARTZ_STAIRS;
	}

	@Override
	public boolean shouldRegister() {
		return true;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlock() {
		return null;
	}

	@Override
	public Class<? extends TileEntity> getTileEntity() {
		return null;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererRecipeMulti(
				new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 5, new ItemStack(this, 4),
						"  Q", " QQ", "QQQ",
						'Q', ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class)),
				new ThaumicTinkererCraftingBenchRecipe("", new ItemStack(this, 4),
						"Q  ", "QQ ", "QQQ",
						'Q', ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class))
		);
	}
}