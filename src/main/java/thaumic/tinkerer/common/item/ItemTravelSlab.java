package thaumic.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockTravelSlab;
import thaumic.tinkerer.common.block.quartz.BlockDarkQuartzSlab;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.registry.ITTinkererItem;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;

/**
 * Created by Katrina on 14/04/2015.
 */
public class ItemTravelSlab extends ItemSlab implements ITTinkererItem {

    public ItemTravelSlab(Block par1) {
        super(par1, (BlockSlab) ThaumicTinkerer.registry.getFirstBlockFromClass(BlockTravelSlab.class), (BlockSlab) ThaumicTinkerer.registry.getBlockFromClass(BlockTravelSlab.class).get(1), false);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getItemName() {
        return LibBlockNames.TRAVEL_SLAB;
    }

    @Override
    public boolean shouldRegister() {
        return false;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }
}
