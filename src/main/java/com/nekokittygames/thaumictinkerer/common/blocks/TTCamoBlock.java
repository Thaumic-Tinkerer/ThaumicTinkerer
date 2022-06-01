package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityCamoflage;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class TTCamoBlock<T extends TileEntityCamoflage> extends TTTileEntity<T> {


    public TTCamoBlock(String name, Material blockMaterialIn, MapColor blockMapColorIn, boolean preserveTileEntity) {
        super(name, blockMaterialIn, blockMapColorIn, preserveTileEntity);
    }


    public TTCamoBlock(String name, Material materialIn, boolean preserveTileEntity) {
        super(name, materialIn, preserveTileEntity);
    }

    protected static boolean camoflageFromHand(EntityPlayer playerIn, EnumHand hand, TileEntity te) {
        if (te instanceof TileEntityCamoflage) {
            TileEntityCamoflage camo = (TileEntityCamoflage) te;
            ItemStack currentStack = playerIn.getHeldItem(hand);
            if (currentStack != ItemStack.EMPTY && currentStack.getItem() instanceof ItemBlock) {
                    ItemBlock itemBlock = (ItemBlock) currentStack.getItem();
                    camo.setBlockCopy(itemBlock.getBlock().getStateFromMeta(itemBlock.getMetadata(currentStack)));
                    camo.sendUpdates();
                    return true;
                }
            if(currentStack==ItemStack.EMPTY && playerIn.isSneaking())
            {
                camo.clearBlockCopy();
                camo.sendUpdates();
            }
        }
        return false;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityCamoflage) {
            TileEntityCamoflage camo = (TileEntityCamoflage) te;
            if (camo.getBlockCopy() != null)
                return camo.getBlockCopy();
        }
        return super.getActualState(state, worldIn, pos);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isTranslucent(IBlockState state) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityCamoflage) {
            TileEntityCamoflage camo = (TileEntityCamoflage) te;
            if (camo.getBlockCopy() != null)
                return camo.getBlockCopy().getLightValue();
        }
        return super.getLightValue(state, world, pos);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}
