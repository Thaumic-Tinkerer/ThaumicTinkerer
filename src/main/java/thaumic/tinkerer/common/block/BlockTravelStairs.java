package thaumic.tinkerer.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.quartz.BlockDarkQuartz;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererBlock;
import thaumic.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipeMulti;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;

/**
 * Created by Katrina on 16/04/2015.
 */
public class BlockTravelStairs extends BlockStairs implements ITTinkererBlock {

    public BlockTravelStairs() {
        super(ConfigBlocks.blockCosmeticSolid,2);
        setCreativeTab(ModCreativeTab.INSTANCE);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return LibBlockNames.TRAVEL_STAIRS;
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
    public Class<? extends ItemBlock> getItemBlock() {
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return null;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererRecipeMulti(
                new ThaumicTinkererCraftingBenchRecipe("PAVETRAVEL", new ItemStack(this, 4),
                        "  Q", " QQ", "QQQ",
                        'Q', new ItemStack(ConfigBlocks.blockCosmeticSolid,2))
        );
    }
    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity e) {
        if(world.getBlock(x, y, z) == this) {
            if(e instanceof EntityLivingBase) {
                if(world.isRemote) {
                    Thaumcraft.proxy.blockSparkle(world, x, y, z, '?', 5);
                }

                ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 40, 1));
                ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.jump.id, 40, 0));
            }

            super.onEntityWalking(world, x, y, z, e);
        }
    }
}
