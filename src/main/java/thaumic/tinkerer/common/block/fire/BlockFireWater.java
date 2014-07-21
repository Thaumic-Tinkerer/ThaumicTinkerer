package thaumic.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.ItemBrightNitor;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererCrucibleRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.HashMap;

public class BlockFireWater extends BlockFireBase {
	public BlockFireWater() {
		super();
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.BLOCK_FIRE_WATER;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FIRE_AQUA, new AspectList().add(Aspect.FIRE, 5).add(Aspect.WATER, 5), 2, -2, 2, new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
				.setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_AQUA)).setSecondary();
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_FIRE_AQUA, new ItemStack(this), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.WATER, 5));
	}

	@Override
	public HashMap<Block, Block> getBlockTransformation() {
		HashMap<Block, Block> result = new HashMap<Block, Block>();
		result.put(Blocks.sand, Blocks.ice);
		result.put(Blocks.netherrack, Blocks.snow);
		result.put(Blocks.soul_sand, Blocks.ice);
		result.put(Blocks.glowstone, Blocks.ice);
		result.put(Blocks.lava, Blocks.obsidian);
		result.put(Blocks.flowing_lava, Blocks.obsidian);
		return result;
	}
}
