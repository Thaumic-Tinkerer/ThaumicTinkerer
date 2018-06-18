package com.nekokittygames.thaumictinkerer.common.blocks.transvector;

import com.nekokittygames.thaumictinkerer.common.blocks.TTCamoBlock;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.transvector.TileEntityTransvectorDislocator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import java.util.Random;

import static net.minecraft.block.BlockPistonBase.getFacing;

public class BlockTransvectorDislocator extends TTCamoBlock<TileEntityTransvectorDislocator> {

    public static final PropertyBool POWERED= PropertyBool.create("powered");
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public BlockTransvectorDislocator() {
        super(LibBlockNames.TRANSVECTOR_DISLOCATOR, Material.WOOD, true);
        setDefaultState(this.getBlockState().getBaseState().withProperty(POWERED,false).withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,POWERED,FACING);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(POWERED, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();
        if ((Boolean)state.getValue(POWERED)) {
            i |= 8;
        }

        return i;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if(!worldIn.isRemote && state.getValue(POWERED) && !worldIn.isBlockPowered(pos))
        {
            worldIn.setBlockState(pos,state.withProperty(POWERED,false),2);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if(!worldIn.isRemote)
        {
            if(state.getValue(POWERED) && !worldIn.isBlockPowered(pos))
            {
                worldIn.scheduleUpdate(pos,this,4);
            }
            else if(!state.getValue(POWERED) && worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos,state.withProperty(POWERED,true),2);
                TileEntity ent=worldIn.getTileEntity(pos);
                if(ent instanceof TileEntityTransvectorDislocator)
                {
                    TileEntityTransvectorDislocator dislocator= (TileEntityTransvectorDislocator) ent;
                    dislocator.receiveRedstonePulse();
                }
            }
        }
    }

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        IBlockState state = world.getBlockState(pos);
        return !(Boolean)state.getValue(POWERED) && super.rotateBlock(world, pos, axis);
    }
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(POWERED, false);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityTransvectorDislocator();
    }
}
