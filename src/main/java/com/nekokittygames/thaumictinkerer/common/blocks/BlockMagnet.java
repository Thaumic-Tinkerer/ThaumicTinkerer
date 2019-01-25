package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMagnet;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import thaumcraft.api.casters.IInteractWithCaster;

import java.util.Objects;

import static net.minecraft.block.BlockPistonBase.getFacing;

public abstract class BlockMagnet<T extends TileEntityMagnet> extends TTTileEntity<T> implements IInteractWithCaster {
    private static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyEnum<MagnetPull> POLE = PropertyEnum.create("pole", MagnetPull.class);

    protected BlockMagnet(String name) {
        super(name, Material.IRON, true);
        setDefaultState(this.getBlockState().getBaseState().withProperty(POLE, MagnetPull.PULL).withProperty(FACING, EnumFacing.NORTH));
    }


    @NotNull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, POLE, FACING);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, Objects.requireNonNull(getFacing(meta))).withProperty(POLE, ((meta & 8) > 0) ? MagnetPull.PUSH : MagnetPull.PULL);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = state.getValue(FACING).getIndex();
        if (state.getValue(POLE) == MagnetPull.PULL) {
            i |= 8;
        }

        return i;
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


    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return super.rotateBlock(world, pos, axis);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(POLE, MagnetPull.PULL);
    }

    @Override
    public boolean onCasterRightClick(World world, ItemStack itemStack, EntityPlayer entityPlayer, BlockPos blockPos, EnumFacing enumFacing, EnumHand enumHand) {
        IBlockState current = world.getBlockState(blockPos);
        world.setBlockState(blockPos, current.withProperty(POLE, current.getValue(POLE) == MagnetPull.PULL ? MagnetPull.PUSH : MagnetPull.PULL));
        return true;
    }

    public enum MagnetPull implements IStringSerializable {
        PULL("pull"),
        PUSH("push");

        private String name;

        MagnetPull(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public enum MagnetType implements IStringSerializable {
        ITEM("item"),
        MOB("mob");

        private String name;

        MagnetType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
