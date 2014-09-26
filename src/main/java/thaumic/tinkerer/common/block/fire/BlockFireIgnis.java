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

public class BlockFireIgnis extends BlockFireBase {

    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_FIRE;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FIRE_IGNIS, new AspectList().add(Aspect.FIRE, 10), 4, -4, 2, new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_IGNIS)).setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_FIRE_IGNIS, new ItemStack(this), new ItemStack(ConfigItems.itemShard, 1, 1), new AspectList().add(Aspect.FIRE, 10).add(Aspect.AIR, 5));
    }

    @Override
    public HashMap<Block, Block> getBlockTransformation() {
        HashMap<Block, Block> result = new HashMap<Block, Block>();
        result.put(Blocks.grass, Blocks.netherrack);
        result.put(Blocks.dirt, Blocks.netherrack);
        result.put(Blocks.sand, Blocks.soul_sand);
        result.put(Blocks.gravel, Blocks.soul_sand);
        result.put(Blocks.clay, Blocks.glowstone);
        result.put(Blocks.coal_ore, Blocks.quartz_ore);
        result.put(Blocks.iron_ore, Blocks.quartz_ore);
        result.put(Blocks.diamond_ore, Blocks.quartz_ore);
        result.put(Blocks.emerald_ore, Blocks.quartz_ore);
        result.put(Blocks.gold_block, Blocks.quartz_ore);
        result.put(Blocks.lapis_ore, Blocks.quartz_ore);
        result.put(Blocks.redstone_ore, Blocks.quartz_ore);
        result.put(Blocks.lit_redstone_ore, Blocks.quartz_ore);
        result.put(Blocks.water, Blocks.lava);
        result.put(Blocks.wheat, Blocks.nether_wart);
        result.put(Blocks.potatoes, Blocks.nether_wart);
        result.put(Blocks.carrots, Blocks.nether_wart);
        result.put(Blocks.red_flower, Blocks.brown_mushroom);
        result.put(Blocks.yellow_flower, Blocks.yellow_flower);
        return result;
    }
}
