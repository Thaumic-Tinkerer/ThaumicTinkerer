package com.nekokittygames.thaumictinkerer.common.blocks;

import static net.minecraft.block.BlockPistonBase.getFacing;

import com.nekokittygames.thaumictinkerer.common.libs.LibBlockNames;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityRepairer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRepairer extends TTTileEntity<TileEntityRepairer> {
  public static final PropertyDirection FACING =
      PropertyDirection.create("facing");
  public static final PropertyBool ACTIVE = PropertyBool.create("active");

  public BlockRepairer() {
    super(LibBlockNames.REPAIRER, Material.IRON, true);
    setDefaultState(this.getBlockState()
                        .getBaseState()
                        .withProperty(ACTIVE, false)
                        .withProperty(FACING, EnumFacing.NORTH));
  }

  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, ACTIVE, FACING);
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
                              EntityLivingBase placer, ItemStack stack) {
    worldIn.setBlockState(
        pos,
        state.withProperty(
            FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)),
        2);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState()
        .withProperty(FACING, getFacing(meta))
        .withProperty(ACTIVE, (meta & 8) > 0);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    int i = ((EnumFacing)state.getValue(FACING)).getIndex();
    if ((Boolean)state.getValue(ACTIVE)) {
      i |= 8;
    }

    return i;
  }

  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn,
                                    BlockPos pos) {
    TileEntity te = worldIn.getTileEntity(pos);
    if (te instanceof TileEntityRepairer) {
      TileEntityRepairer repairer = (TileEntityRepairer)te;
      return state.withProperty(ACTIVE, repairer.isTookLastTick());
    }
    return state;
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos,
                                  IBlockState state, EntityPlayer playerIn,
                                  EnumHand hand, EnumFacing facing, float hitX,
                                  float hitY, float hitZ) {
    TileEntity te = worldIn.getTileEntity(pos);
    if (te instanceof TileEntityRepairer) {
      TileEntityRepairer repairer = (TileEntityRepairer)te;
      ItemStack stack = repairer.getInventory().getStackInSlot(0);
      if (stack.isEmpty()) {
        ItemStack playerStack = playerIn.getHeldItem(hand);
        if (repairer.isItemValidForSlot(0, playerStack)) {
          repairer.getInventory().insertItem(0, playerStack.copy(), false);
          playerStack.setCount(playerStack.getCount() - 1);
          if (playerStack.isEmpty()) {
            playerIn.inventory.setInventorySlotContents(
                playerIn.inventory.currentItem, ItemStack.EMPTY);
          }
          repairer.markDirty();
          return true;
        }
      } else {
        if (!playerIn.inventory.addItemStackToInventory(stack.copy())) {
          playerIn.dropItem(stack, false);
        }
        repairer.getInventory().setStackInSlot(0, ItemStack.EMPTY);
        repairer.markDirty();
        return true;
      }
    }
    return false;
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
    return state.withProperty(FACING,
                              rot.rotate((EnumFacing)state.getValue(FACING)));
  }

  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    return state.withRotation(
        mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
  }

  public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
    IBlockState state = world.getBlockState(pos);
    return !(Boolean)state.getValue(ACTIVE) &&
        super.rotateBlock(world, pos, axis);
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos,
                                          EnumFacing facing, float hitX,
                                          float hitY, float hitZ, int meta,
                                          EntityLivingBase placer) {
    return this.getDefaultState()
        .withProperty(FACING,
                      EnumFacing.getDirectionFromEntityLiving(pos, placer))
        .withProperty(ACTIVE, false);
  }

  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TileEntityRepairer();
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileEntity tileentity = worldIn.getTileEntity(pos);
    if (tileentity instanceof TileEntityRepairer) {
      TileEntityRepairer repairer = (TileEntityRepairer)tileentity;
      if (repairer.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
        InventoryHelper.spawnItemStack(
            worldIn, pos.getX(), pos.getY(), pos.getZ(),
            repairer.getInventory().getStackInSlot(0));
      }
      worldIn.updateComparatorOutputLevel(pos, this);
    }
    super.breakBlock(worldIn, pos, state);
  }
}
