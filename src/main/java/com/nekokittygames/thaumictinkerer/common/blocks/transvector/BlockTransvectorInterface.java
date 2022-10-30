package com.nekokittygames.thaumictinkerer.common.blocks.transvector;

import com.nekokittygames.thaumictinkerer.common.blocks.TTCamoBlock;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.transvector.TileEntityTransvectorInterface;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockTransvectorInterface extends TTCamoBlock<TileEntityTransvectorInterface> {
    public BlockTransvectorInterface() {
        super(LibBlockNames.TRANSVECTOR_INTERFACE, Material.ROCK, true);

    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityTransvectorInterface();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileEntityTransvectorInterface) {
            TileEntityTransvectorInterface tinterface = (TileEntityTransvectorInterface) te;
            return tinterface.getComparatorValue();
        }
        return super.getComparatorInputOverride(blockState, worldIn, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        return camoflageFromHand(playerIn, hand, te);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return true;
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityTransvectorInterface) {
            TileEntityTransvectorInterface tinterface = (TileEntityTransvectorInterface) te;
            BlockPos remote = tinterface.getTilePos();
            if (remote != null) {
                IBlockState state1 = world.getBlockState(tinterface.getTilePos());
                return state1.getWeakPower(world, remote, side);
            }
        }
        return 0;
    }


}
