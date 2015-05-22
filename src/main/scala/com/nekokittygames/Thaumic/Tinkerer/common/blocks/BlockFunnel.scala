package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileFunnel
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.{BlockState, IBlockState}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{BlockPos, EnumFacing, EnumWorldBlockLayer}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fml.relauncher.SideOnly

import scala.util.Random

/**
 * Created by Katrina on 18/05/2015.
 */
object BlockFunnel extends {
  val JAR:PropertyBool=PropertyBool.create("jar")
} with ModBlockContainer(Material.iron)
{
  setHardness(3.0F)
  setResistance(8.0f)
  setStepSound(Block.soundTypeStone)
  setBlockBounds(0f, 0f, 0f, 1f, 1f / 8f, 1f)
  setDefaultState(getDefaultState.withProperty(JAR, false))
  setUnlocalizedName(LibNames.FUNNEL)
  val random: Random = new Random()

  override def createBlockState(): BlockState = new BlockState(this,JAR)


  override def getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState =
    {
      val te:TileEntity=worldIn.getTileEntity(pos)
      if(te.isInstanceOf[TileFunnel])
        {
          if(te.asInstanceOf[TileFunnel].inventory==null)
            state.withProperty(JAR,false)
          else
            state.withProperty(JAR,true)
        }
      else
        state.withProperty(JAR,false)
    }

  override def getMetaFromState(state: IBlockState): Int = {0}


  override def getStateFromMeta(meta: Int): IBlockState = {getDefaultState}

  @SideOnly(net.minecraftforge.fml.relauncher.Side.CLIENT)
  override def getBlockLayer: EnumWorldBlockLayer = EnumWorldBlockLayer.TRANSLUCENT

  override def isOpaqueCube(): Boolean = false

  override def isFullCube(): Boolean = false


  override def getTileClass: Class[_ <: TileEntity] = classOf[TileFunnel]

  override def canPlaceBlockAt(worldIn: World, pos: BlockPos): Boolean = {
    return worldIn.getBlockState(pos.down(1)).getBlock == Blocks.hopper
  }

  override def onNeighborChange(world: IBlockAccess, pos: BlockPos, neighbor: BlockPos): Unit = {
    if (world.getBlockState(pos.down(1)).getBlock != Blocks.hopper) {
      dropBlockAsItem(world.asInstanceOf[World], pos, world.getBlockState(pos), 0)
      world.asInstanceOf[World].setBlockToAir(pos)
    }
  }

  override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    val funnel = worldIn.getTileEntity(pos).asInstanceOf[TileFunnel]
    val itemStack = funnel.inventory

    if (itemStack == null) {
      val playrStack = playerIn.getCurrentEquippedItem
      if (playrStack != null && funnel.canInsertItem(0, playrStack, EnumFacing.DOWN)) {
        funnel.setInventorySlotContents(0, playrStack.splitStack(1))
        if (playrStack.stackSize <= 0) {
          playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null)
        }
        funnel.markDirty()
        return true
      }
    }
    else {
      if (!playerIn.inventory.addItemStackToInventory(itemStack))
        playerIn.dropPlayerItemWithRandomChoice(itemStack, false)
      funnel.setInventorySlotContents(0, null)
      funnel.markDirty()
      return true
    }

    return false
  }
}
