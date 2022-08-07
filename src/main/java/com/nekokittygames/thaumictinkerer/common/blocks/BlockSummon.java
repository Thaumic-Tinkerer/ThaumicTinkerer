/*
 * Copyright (c) 2022. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntitySummon;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSummon extends TTTileEntity {
    public static final AxisAlignedBB AXIS_ALIGNED_BB=new AxisAlignedBB(0.0f,0.0f,0.0f,1.0f,(1f/16f)*2f,1.0f);
    public BlockSummon() {
        super(LibBlockNames.SUMMONER,Material.ROCK,true);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntitySummon();
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
    public AxisAlignedBB getBoundingBox(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
        return AXIS_ALIGNED_BB;
    }
}
