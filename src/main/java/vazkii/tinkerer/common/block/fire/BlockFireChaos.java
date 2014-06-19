package vazkii.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.HashMap;

public class BlockFireChaos extends BlockFireBase {
	@Override
	public String getBlockName() {
		return LibBlockNames.BLOCK_FIRE_CHAOS;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return null;
	}

	@Override
	public int tickRate(World p_149738_1_) {
		return 1;
	}

	@Override
	public HashMap<Block, Block> getBlockTransformation() {
		HashMap<Block, Block> result = new HashMap<Block, Block>();
		result.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireAir.class), Blocks.fire);
		result.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireWater.class), Blocks.fire);
		result.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireEarth.class), Blocks.fire);
		result.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireFire.class), Blocks.fire);
		result.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireOrder.class), Blocks.fire);

		return result;
	}
}
