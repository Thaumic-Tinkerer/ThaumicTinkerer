package vazkii.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.oredict.OreDictionary;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.HashMap;

public class BlockFireOrder extends BlockFireBase {
	@Override
	public String getBlockName() {
		return LibBlockNames.BLOCK_FIRE_ORDER;
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
	public int getDecayChance() {
		return 3;
	}

	private HashMap<Block, Block> oreDictinaryOresCache;

	public HashMap<Block, Block> getOreDictionaryOres() {
		if (oreDictinaryOresCache == null) {
			HashMap<Block, Block> result = new HashMap<Block, Block>();
			for (String ore : OreDictionary.getOreNames()) {
				if (ore.startsWith("ore")) {
					for (String block : OreDictionary.getOreNames()) {
						if (block.startsWith("block") && block.regionMatches(5, ore, 3, 10)) {
							if (OreDictionary.getOres(block).size() > 0 && OreDictionary.getOres(ore).size() > 0) {
								result.put(((ItemBlock) OreDictionary.getOres(ore).get(0).getItem()).field_150939_a, ((ItemBlock) OreDictionary.getOres(block).get(0).getItem()).field_150939_a);
							}
						}
					}
				}
			}

			oreDictinaryOresCache = result;
		}
		return oreDictinaryOresCache;
	}

	@Override
	public HashMap<Block, Block> getBlockTransformation() {
		HashMap<Block, Block> result = new HashMap<Block, Block>();
		result.put(Blocks.lit_redstone_ore, Blocks.redstone_block);
		result.put(Blocks.redstone_ore, Blocks.redstone_block);
		result.put(Blocks.lapis_ore, Blocks.lapis_block);
		result.put(Blocks.iron_ore, Blocks.iron_block);
		result.put(Blocks.emerald_ore, Blocks.emerald_block);
		result.put(Blocks.diamond_ore, Blocks.diamond_block);
		result.put(Blocks.coal_ore, Blocks.coal_block);
		result.put(Blocks.gold_ore, Blocks.gold_block);
		result.put(Blocks.quartz_ore, Blocks.quartz_block);
		result.putAll(getOreDictionaryOres());
		return result;
	}
}
