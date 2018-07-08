package com.nekokittygames.thaumictinkerer.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockGas extends TTBlock {

    public BlockGas(String name) {
        super(name, Material.AIR);
        //(0, 0, 0, 0, 0, 0);
        setTickRandomly(false);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    /*@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

        int meta = par1World.getBlockMetadata(par2, par3, par4);
        if (meta != 0) {
            setAt(par1World, par2 - 1, par3, par4, meta - 1);
            setAt(par1World, par2 + 1, par3, par4, meta - 1);

            setAt(par1World, par2, par3 - 1, par4, meta - 1);
            setAt(par1World, par2, par3 + 1, par4, meta - 1);

            setAt(par1World, par2, par3, par4 - 1, meta - 1);
            setAt(par1World, par2, par3, par4 + 1, meta - 1);

            // Just in case...
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);

            placeParticle(par1World, par2, par3, par4);
        }
    }*/

    public void placeParticle(World world, int par2, int par3, int par4) {
        // NO-OP, override
    }

    private void setAt(World world, BlockPos pos, int meta) {
        /*if (world.isAirBlock(x, y, z) && world.getBlock(x, y, z) != this) {
            if (!world.isRemote)
                world.setBlock(x, y, z, this, meta, 2);
            world.scheduleBlockUpdate(x, y, z, this, 10);
        }*/
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess access, BlockPos pos, Entity entity) {
        return false;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)  {
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
        return null;
    }

    @Override
    public boolean isAir(IBlockState state, IBlockAccess access, BlockPos pos) {
        return true;
    }
}