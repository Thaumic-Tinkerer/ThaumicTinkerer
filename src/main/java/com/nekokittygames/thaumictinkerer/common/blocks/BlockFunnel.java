package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityFunnel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFunnel extends TTTileEntity<TileEntityFunnel> {

    public static final PropertyBool JAR= PropertyBool.create("jar");

    public BlockFunnel() {
        super(LibBlockNames.FUNNEL, Material.ROCK,true);
        setHardness(3.0F);
        setResistance(8.0f);
        setDefaultState(this.getBlockState().getBaseState().withProperty(JAR,false));

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
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this,JAR);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te= (TileEntity) worldIn.getTileEntity(pos);
        if(te instanceof TileEntityFunnel)
        {
            TileEntityFunnel funnel=(TileEntityFunnel)te;
            if(funnel.getInventory().getStackInSlot(0)== ItemStack.EMPTY)
                return state.withProperty(JAR,false);
            else
                return state.withProperty(JAR,true);
        }
        return state.withProperty(JAR,false);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.blockState.getBaseState().withProperty(JAR,false);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.getBlockState(pos.down()).getBlock()== Blocks.HOPPER;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityFunnel();
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te=worldIn.getTileEntity(pos);
        if(te instanceof TileEntityFunnel)
        {
            TileEntityFunnel funnel= (TileEntityFunnel) te;
            ItemStack stack=funnel.getInventory().getStackInSlot(0);
            if(stack==ItemStack.EMPTY)
            {
                ItemStack playerStack=playerIn.getHeldItem(hand);
                if(funnel.isItemValidForSlot(0,playerStack))
                {
                    funnel.getInventory().insertItem(0,playerStack.copy(),false);
                    playerStack.setCount(playerStack.getCount()-1);
                    if (playerStack.isEmpty()) {
                        playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, ItemStack.EMPTY);
                    }
                    funnel.markDirty();
                    return true;
                }
            }
            else
            {
                if(!playerIn.inventory.addItemStackToInventory(stack.copy()))
                {
                    playerIn.dropItem(stack,false);

                }
                funnel.getInventory().setStackInSlot(0,ItemStack.EMPTY);
                funnel.markDirty();
                return true;
            }
        }
        return false;
    }
}
