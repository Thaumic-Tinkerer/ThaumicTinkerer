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

public class BlockFireAir extends BlockFireBase {
    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_AIR;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if(!ConfigHandler.enableFire)
            return null;
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FIRE_AER, new AspectList().add(Aspect.FIRE, 5).add(Aspect.AIR, 5), 3, -7, 2, new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_AER)).setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if(!ConfigHandler.enableFire)
            return null;
        return new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_FIRE_AER, new ItemStack(this), new ItemStack(ConfigItems.itemShard, 1, 0), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.AIR, 5));
    }

    @Override
    public HashMap<BlockTuple, BlockTuple> getBlockTransformation() {
        HashMap<BlockTuple, BlockTuple> result = new HashMap<BlockTuple, BlockTuple>();
        result.put(new BlockTuple(Blocks.log), new BlockTuple(Blocks.sand));
        result.put(new BlockTuple(Blocks.leaves), new BlockTuple(Blocks.sandstone));
        result.put(new BlockTuple(Blocks.leaves2), new BlockTuple(Blocks.sandstone));
        result.put(new BlockTuple(Blocks.log2), new BlockTuple(Blocks.sand));
        result.put(new BlockTuple(Blocks.ice), new BlockTuple(Blocks.glass));
        if (ConfigHandler.enableCake) {
            result.put(new BlockTuple(Blocks.water), new BlockTuple(Blocks.cake));
        }
        result.put(new BlockTuple(Blocks.dirt), new BlockTuple(Blocks.sand));
        result.put(new BlockTuple(Blocks.grass), new BlockTuple(Blocks.sand));
        return result;
    }

    @Override
    public HashMap<thaumic.tinkerer.common.core.helper.BlockTuple, thaumic.tinkerer.common.core.helper.BlockTuple> getBlockTransformation(World w, int x, int y, int z) {
        return getBlockTransformation();
    }

}

