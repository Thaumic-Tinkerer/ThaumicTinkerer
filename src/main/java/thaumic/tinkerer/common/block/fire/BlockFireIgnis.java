package thaumic.tinkerer.common.block.fire;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.helper.BlockTuple;
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
        if(!ConfigHandler.enableFire)
            return null;
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FIRE_IGNIS, new AspectList().add(Aspect.FIRE, 10), 4, -4, 2, new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_IGNIS)).setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if(!ConfigHandler.enableFire)
            return null;
        return new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_FIRE_IGNIS, new ItemStack(this), new ItemStack(ConfigItems.itemShard, 1, 1), new AspectList().add(Aspect.FIRE, 10).add(Aspect.AIR, 5));
    }

    @Override
    public HashMap<BlockTuple, BlockTuple> getBlockTransformation() {
        HashMap<BlockTuple, BlockTuple> result = new HashMap<BlockTuple, BlockTuple>();
        result.put(new BlockTuple(Blocks.grass), new BlockTuple(Blocks.netherrack));
        result.put(new BlockTuple(Blocks.dirt), new BlockTuple(Blocks.netherrack));
        result.put(new BlockTuple(Blocks.sand), new BlockTuple(Blocks.soul_sand));
        result.put(new BlockTuple(Blocks.gravel), new BlockTuple(Blocks.soul_sand));
        result.put(new BlockTuple(Blocks.clay), new BlockTuple(Blocks.glowstone));
        result.put(new BlockTuple(Blocks.coal_ore), new BlockTuple(Blocks.quartz_ore));
        result.put(new BlockTuple(Blocks.iron_ore), new BlockTuple(Blocks.quartz_ore));
        result.put(new BlockTuple(Blocks.diamond_ore), new BlockTuple(Blocks.quartz_ore));
        result.put(new BlockTuple(Blocks.emerald_ore), new BlockTuple(Blocks.quartz_ore));
        result.put(new BlockTuple(Blocks.gold_block), new BlockTuple(Blocks.quartz_ore));
        result.put(new BlockTuple(Blocks.lapis_ore), new BlockTuple(Blocks.quartz_ore));
        result.put(new BlockTuple(Blocks.redstone_ore), new BlockTuple(Blocks.quartz_ore));
        result.put(new BlockTuple(Blocks.lit_redstone_ore), new BlockTuple(Blocks.quartz_ore));
        result.put(new BlockTuple(Blocks.water), new BlockTuple(Blocks.lava));
        result.put(new BlockTuple(Blocks.wheat), new BlockTuple(Blocks.nether_wart));
        result.put(new BlockTuple(Blocks.potatoes), new BlockTuple(Blocks.nether_wart));
        result.put(new BlockTuple(Blocks.carrots), new BlockTuple(Blocks.nether_wart));
        result.put(new BlockTuple(Blocks.red_flower), new BlockTuple(Blocks.brown_mushroom));
        result.put(new BlockTuple(Blocks.yellow_flower), new BlockTuple(Blocks.red_mushroom));
        result.put(new BlockTuple(Blocks.tallgrass), new BlockTuple(Blocks.air));
        return result;
    }

    @Override
    public HashMap<thaumic.tinkerer.common.core.helper.BlockTuple, thaumic.tinkerer.common.core.helper.BlockTuple> getBlockTransformation(World w, int x, int y, int z) {
        return getBlockTransformation();
    }
}
