package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchantmentPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnchantmentPillar extends TTTileEntity<TileEntityEnchantmentPillar> {
    public static final PropertyBool Top = PropertyBool.create("top");
    public static final PropertyInteger Direction = PropertyInteger.create("direction", 0, 7);

    public BlockEnchantmentPillar() {
        super(LibBlockNames.ENCHANTMENT_PILLAR, Material.ROCK, false);
        setDefaultState(this.getBlockState().getBaseState().withProperty(Top, false).withProperty(Direction, 0));
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityEnchantmentPillar();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, Top, Direction);
    }


    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(Direction) + (state.getValue(Top) ? 8 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.blockState.getBaseState();
        if (meta >= 8) {
            state = state.withProperty(Top, true);
            meta -= 8;
        } else {
            state = state.withProperty(Top, false);
        }
        return state.withProperty(Direction, meta);
    }

    @Override
    protected boolean isInCreativeTab() {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        if (!state.getValue(Top))
            return EnumBlockRenderType.MODEL;
        else
            return EnumBlockRenderType.INVISIBLE;
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.sendStatusMessage(new TextComponentString("Direction - " + state.getValue(Direction)), true);
        return true;
    }
}
