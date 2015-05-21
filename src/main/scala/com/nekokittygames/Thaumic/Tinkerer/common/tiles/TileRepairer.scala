package com.nekokittygames.Thaumic.Tinkerer.common.tiles

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockRepairer
import net.minecraft.block.BlockPistonBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{EnumFacing, BlockPos}
import net.minecraft.world.World
import thaumcraft.api.aspects.{Aspect, IEssentiaTransport}
import thaumcraft.api.wands.IWandable

/**
 * Created by fiona on 21/05/2015.
 */
class TileRepairer extends TileEntity with IWandable with IEssentiaTransport{
  override def onWandStoppedUsing(itemStack: ItemStack, world: World, entityPlayer: EntityPlayer, i: Int): Unit = {}

  override def onUsingWandTick(itemStack: ItemStack, entityPlayer: EntityPlayer, i: Int): Unit = {}

  override def onWandRightClick(world: World, itemStack: ItemStack, entityPlayer: EntityPlayer, blockPos: BlockPos, enumFacing: EnumFacing): Boolean = {
    if(entityPlayer.isSneaking){
      world.setBlockState(blockPos,world.getBlockState(blockPos).withProperty(BlockRepairer.FACING,BlockPistonBase.getFacingFromEntity(world,blockPos,entityPlayer)))
    }else{
      world.setBlockState(blockPos,world.getBlockState(blockPos).withProperty(BlockRepairer.FACING,BlockPistonBase.getFacingFromEntity(world,blockPos,entityPlayer).getOpposite))
    }
    true
  }

  override def addEssentia(aspect: Aspect, i: Int, enumFacing: EnumFacing): Int = 0

  override def takeEssentia(aspect: Aspect, i: Int, enumFacing: EnumFacing): Int = 0

  override def setSuction(aspect: Aspect, i: Int): Unit = {}

  override def getSuctionType(enumFacing: EnumFacing): Aspect = null

  override def getSuctionAmount(enumFacing: EnumFacing): Int = 0

  override def getEssentiaAmount(enumFacing: EnumFacing): Int = 0

  override def getEssentiaType(enumFacing: EnumFacing): Aspect = null

  override def isConnectable(enumFacing: EnumFacing): Boolean = true

  override def canOutputTo(enumFacing: EnumFacing): Boolean = true

  override def canInputFrom(enumFacing: EnumFacing): Boolean = true

  override def getMinimumSuction: Int = 0
}
