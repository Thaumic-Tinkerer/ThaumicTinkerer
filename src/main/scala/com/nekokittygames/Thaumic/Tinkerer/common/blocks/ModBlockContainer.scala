package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import net.minecraft.block.{Block, ITileEntityProvider}
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import net.minecraft.world.World

/**
 * Created by Katrina on 17/05/2015.
 */
trait ModBlockContainer extends Block with ITileEntityProvider {


  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = getTileClass.newInstance()

  def getTileClass: Class[_ <: TileEntity] = null

  override def breakBlock(worldIn: World, pos: BlockPos, state: IBlockState): Unit =
  {
    super.breakBlock(worldIn, pos, state)
    worldIn.removeTileEntity(pos)
  }

  override def onBlockEventReceived(worldIn: World, pos: BlockPos, state: IBlockState, eventID: Int, eventParam: Int): Boolean =
  {
    super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam)
    val tileEntity:TileEntity=worldIn.getTileEntity(pos)
    if (tileEntity == null) false else tileEntity.receiveClientEvent(eventID, eventParam)
  }
}