package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityCamoflage;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class TTCamoBlock< T extends TileEntityCamoflage> extends TTTileEntity<T> {


    public TTCamoBlock(String name, Material blockMaterialIn, MapColor blockMapColorIn, boolean preserveTileEntity) {
        super(name, blockMaterialIn, blockMapColorIn, preserveTileEntity);
    }


    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te=worldIn.getTileEntity(pos);
        if(te instanceof TileEntityCamoflage)
        {
            TileEntityCamoflage camo= (TileEntityCamoflage) te;
            if(camo.getBlockCopy()!=null)
                return camo.getBlockCopy();
        }
        return super.getActualState(state,worldIn,pos);
    }

    public TTCamoBlock(String name, Material materialIn, boolean preserveTileEntity) {
        super(name, materialIn, preserveTileEntity);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
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
        TileEntity te=world.getTileEntity(pos);
        if(te instanceof TileEntityCamoflage) {
            TileEntityCamoflage camo = (TileEntityCamoflage) te;
            if (camo.getBlockCopy() != null)
                return camo.getBlockCopy().getLightValue();
        }
        return super.getLightValue(state,world,pos);
    }
}
