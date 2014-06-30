package vazkii.tinkerer.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import vazkii.tinkerer.common.block.tile.TileCamo;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;

/**
 * Created by nekosune on 28/06/14.
 */
public class BlockRPlacer extends  BlockCamo {
    protected BlockRPlacer() {
        super(Material.rock);
    }

    @Override
    public TileCamo createNewTileEntity(World world, int var2) {
        return null;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return "RemotePlacer";
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return true;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_REMOTE_PLACER, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.MOTION, 1).add(Aspect.SENSES, 1), -6, 3, 3, new ItemStack(this)).setParents(LibResearch.KEY_ANIMATION_TABLET).setConcealed()
                .setPages(new ResearchPage("0"), new ResearchPage("1"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_REMOTE_PLACER));
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_REMOTE_PLACER,LibResearch.KEY_REMOTE_PLACER,new ItemStack(this),new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 5),"ses","sds","sss",'s', ConfigBlocks.blockStoneDevice,'e', Items.ender_pearl,'d', Blocks.dispenser);
    }
}
