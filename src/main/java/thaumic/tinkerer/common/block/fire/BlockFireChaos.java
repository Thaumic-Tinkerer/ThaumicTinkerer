package thaumic.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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

public class BlockFireChaos extends BlockFireBase {
    @Override
    public String getBlockName() {
        return LibBlockNames.BLOCK_FIRE_CHAOS;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FIRE_PERDITIO, new AspectList().add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 5), 2, -8, 3, new ItemStack(this)).setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
                .setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_FIRE_PERDITIO)).setSecondary();
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererCrucibleRecipe(LibResearch.KEY_FIRE_PERDITIO, new ItemStack(this), new ItemStack(ConfigItems.itemShard, 1, 5), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.ENTROPY, 5));
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
        result.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireIgnis.class), Blocks.fire);
        result.put(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockFireOrder.class), Blocks.fire);

        return result;
    }
}
