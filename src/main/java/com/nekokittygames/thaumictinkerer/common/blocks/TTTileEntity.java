package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityThaumicTinkerer;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class TTTileEntity<T extends TileEntity> extends TTBlock {

    /**
     * Should the {@link TileEntity} be preserved until after {@link #getDrops} has been called?
     */
    private final boolean preserveTileEntity;

    public TTTileEntity(String name, Material blockMaterialIn, MapColor blockMapColorIn, final boolean preserveTileEntity) {
        super(name, blockMaterialIn, blockMapColorIn);
        this.setTickRandomly(false);
        this.preserveTileEntity = preserveTileEntity;
    }

    public TTTileEntity(String name, Material materialIn, final boolean preserveTileEntity) {
        super(name, materialIn);

        this.preserveTileEntity = preserveTileEntity;
    }

    @Override
    public boolean hasTileEntity(final IBlockState state) {
        return true;
    }

    @Override
    public abstract TileEntity createTileEntity(World world, IBlockState state);

    /**
     * Get the {@link TileEntity} at the specified position.
     *
     * @param world The World
     * @param pos   The position
     * @return The TileEntity
     */
    @SuppressWarnings("unchecked")
    @Nullable
    protected T getTileEntity(final IBlockAccess world, final BlockPos pos) {
        return (T) world.getTileEntity(pos);
    }

    @Override
    public boolean removedByPlayer(final IBlockState state, final World world, final BlockPos pos, final EntityPlayer player, final boolean willHarvest) {
        // If it will harvest, delay deletion of the block until after getDrops
        if(preserveTileEntity && willHarvest && !player.capabilities.isCreativeMode)
            return true;
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void harvestBlock(final World world, final EntityPlayer player, final BlockPos pos, final IBlockState state, @Nullable final TileEntity te, final ItemStack stack) {
        super.harvestBlock(world, player, pos, state, te, stack);

        if (preserveTileEntity) {
            this.onBlockHarvested(world,pos,state,player);
            world.setBlockToAir(pos);
            world.removeTileEntity(pos);
        }
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }


    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(world, pos, state, random);
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityThaumicTinkerer) {
                TileEntityThaumicTinkerer base = (TileEntityThaumicTinkerer) tile;
                if (base.respondsToPulses()) {
                    base.activateOnPulse();
                }
            }
        }

    }

    public void customNeighborsChanged(World world, BlockPos pos) {
        this.updateRedstone(world, pos);

    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        customNeighborsChanged(worldIn, pos);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
        if (world instanceof World)
            customNeighborsChanged((World) world, pos);
    }

    public void updateRedstone(World world, BlockPos pos) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityThaumicTinkerer) {
                TileEntityThaumicTinkerer base = (TileEntityThaumicTinkerer) tile;
                boolean powered = world.getRedstonePowerFromNeighbors(pos) > 0;
                boolean wasPowered = base.getRedstonePowered();
                if (powered && !wasPowered) {
                    if (base.respondsToPulses()) {
                        world.scheduleUpdate(pos, this, 4);
                    }
                    base.setRedstonePowered(true);
                } else if (!powered && wasPowered) {
                    base.setRedstonePowered(false);
                }
            }
        }

    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        this.updateRedstone(worldIn, pos);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityThaumicTinkerer) {
            TileEntityThaumicTinkerer base = (TileEntityThaumicTinkerer) tile;
            return base.canRedstoneConnect();
        }
        return false;
    }
}
