package vazkii.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.ItemBrightNitor;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererCrucibleRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.HashMap;

public class BlockFireAir extends BlockFireBase {
	@Override
	public String getBlockName() {
		return LibBlockNames.BLOCK_FIRE_AIR;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FIRE_AER, new AspectList().add(Aspect.FIRE, 5).add(Aspect.AIR, 5), 2, -8, 2, new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
				.setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_AER)).setSecondary();
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_FIRE_AER, new ItemStack(this), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemBrightNitor.class)), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.AIR, 5));
	}

	@Override
	public HashMap<Block, Block> getBlockTransformation() {
		HashMap<Block, Block> result = new HashMap<Block, Block>();
		result.put(Blocks.log, Blocks.sand);
		result.put(Blocks.leaves, Blocks.sandstone);
		result.put(Blocks.leaves2, Blocks.sandstone);
		result.put(Blocks.log2, Blocks.sand);
		result.put(Blocks.ice, Blocks.glass);
		result.put(Blocks.water, Blocks.cake);
		result.put(Blocks.dirt, Blocks.sand);
		return result;
	}
}

