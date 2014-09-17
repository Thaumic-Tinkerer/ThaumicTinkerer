package thaumic.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
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

public class BlockFireEarth extends BlockFireBase {
	public BlockFireEarth() {
		super();
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.BLOCK_FIRE_EARTH;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FIRE_TERRA, new AspectList().add(Aspect.FIRE, 5).add(Aspect.EARTH, 5), 4, -6, 2, new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
				.setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_TERRA)).setSecondary();
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_FIRE_TERRA, new ItemStack(this), new ItemStack(ConfigItems.itemShard, 1, 3), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.EARTH, 5));
    }

	@Override
	public HashMap<Block, Block> getBlockTransformation() {
		HashMap<Block, Block> result = new HashMap<Block, Block>();
		result.put(Blocks.sand, Blocks.dirt);
		result.put(Blocks.gravel, Blocks.clay);
		result.put(Blocks.nether_brick, Blocks.planks);
		result.put(Blocks.nether_brick_fence, Blocks.fence);
		result.put(Blocks.nether_brick_stairs, Blocks.oak_stairs);
		result.put(Blocks.cactus, Blocks.log);
		result.put(Blocks.snow_layer, Blocks.tallgrass);
		result.put(Blocks.stone, Blocks.dirt);
		result.put(Blocks.mob_spawner, Blocks.iron_block);
		result.put(Blocks.log, Blocks.dirt);

		result.put(Blocks.log2, Blocks.dirt);

		result.put(Blocks.leaves, Blocks.dirt);
		result.put(Blocks.leaves2, Blocks.dirt);
		result.put(Blocks.cobblestone, Blocks.dirt);
		result.put(Blocks.planks, Blocks.dirt);
		result.put(Blocks.glass, Blocks.dirt);
		return result;
	}
}
