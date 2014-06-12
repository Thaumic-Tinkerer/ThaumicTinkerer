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
 * File Created @ [8 Sep 2013, 18:57:25 (GMT)]
 */
package vazkii.tinkerer.common.block.transvector;

import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import vazkii.tinkerer.common.block.BlockCamo;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvector;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;

public class BlockTransvectorInterface extends BlockCamo {

	public BlockTransvectorInterface() {
		super(Material.iron);
		setHardness(3F);
		setResistance(10F);
	}

	@Override
	public TileTransvector createNewTileEntity(World var1, int var2) {
		return new TileTransvectorInterface();
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.INTERFACE;
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
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_INTERFACE, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.ORDER, 4), -4, 2, 1, new ItemStack(ModBlocks.interfase)).setParents(LibResearch.KEY_DARK_QUARTZ)
				.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_INTERFACE), new ResearchPage("1"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_INTERFACE + "1"), new ResearchPage("2"));

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_INTERFACE, LibResearch.KEY_INTERFACE, new ItemStack(ModBlocks.interfase), new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16),
				"BRB", "LEL", "BRB",
				'B', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
				'E', new ItemStack(Items.ender_pearl),
				'L', new ItemStack(Items.dye, 1, 4),
				'R', new ItemStack(Items.redstone));
	}
}