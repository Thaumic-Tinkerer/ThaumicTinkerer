package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityAnimationTablet;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAnimationTablet extends TTTileEntity<TileEntityAnimationTablet> {
    public BlockAnimationTablet() {
        super(LibBlockNames.ANIMATION_TABLET, Material.IRON, true);
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
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityAnimationTablet();
    }


    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        // worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)), 2);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityAnimationTablet) {
            TileEntityAnimationTablet animationTablet = (TileEntityAnimationTablet) te;
            animationTablet.setFacing(EnumFacing.getDirectionFromEntityLiving(pos, placer));
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityAnimationTablet) {
            TileEntityAnimationTablet animationTablet = (TileEntityAnimationTablet) te;
            if (player.isSneaking()) {
                player.sendStatusMessage(new TextComponentTranslation("ttmisc.debug.redstone", animationTablet.getRedstonePowered()), true);
                return true;
            }


            player.openGui(ThaumicTinkerer.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return false;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
    }
}
