package com.nekokittygames.Thaumic.Tinkerer.common.tiles

import codechicken.lib.inventory.InventoryNBT
import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockRepairer
import net.minecraft.block.BlockPistonBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{ChatComponentText, IChatComponent, EnumFacing, BlockPos}
import net.minecraft.world.World
import thaumcraft.api.aspects.{AspectList, IAspectContainer, Aspect, IEssentiaTransport}
import thaumcraft.api.wands.IWandable



/**
 * Created by fiona on 21/05/2015.
 */
class TileRepairer extends TileEntity with IWandable with IEssentiaTransport with IAspectContainer with IInventory{
  var inventory:InventoryNBT=new InventoryNBT(1,new NBTTagCompound)

  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)

  }
  override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
    inventory=new InventoryNBT(1,compound.getCompoundTag("inventory"))
  }

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

  override def isConnectable(enumFacing: EnumFacing): Boolean = {
    val blockfacing:EnumFacing=worldObj.getBlockState(pos).getValue(BlockRepairer.FACING).asInstanceOf[EnumFacing]
    blockfacing==enumFacing
  }

  override def canOutputTo(enumFacing: EnumFacing): Boolean = true

  override def canInputFrom(enumFacing: EnumFacing): Boolean = true

  override def getMinimumSuction: Int = 0

  override def doesContainerAccept(aspect: Aspect): Boolean = false

  override def doesContainerContain(aspectList: AspectList): Boolean = false

  override def addToContainer(aspect: Aspect, i: Int): Int = 0

  override def containerContains(aspect: Aspect): Int = 0

  override def takeFromContainer(aspect: Aspect, i: Int): Boolean = false

  override def takeFromContainer(aspectList: AspectList): Boolean = false

  override def getAspects: AspectList = new AspectList().add(Aspect.TOOL,1)

  override def setAspects(aspectList: AspectList): Unit = {}

  override def doesContainerContainAmount(aspect: Aspect, i: Int): Boolean = false

  override def decrStackSize(index: Int, count: Int): ItemStack = null

  override def closeInventory(player: EntityPlayer): Unit = {}

  override def getSizeInventory: Int = 1

  override def getInventoryStackLimit: Int = 1

  override def clear(): Unit = {}

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = false

  override def getStackInSlotOnClosing(index: Int): ItemStack = null

  override def openInventory(player: EntityPlayer): Unit = {}

  override def getFieldCount: Int = 0

  override def getField(id: Int): Int = 0

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = {}

  override def isUseableByPlayer(player: EntityPlayer): Boolean = true

  override def getStackInSlot(index: Int): ItemStack = null

  override def setField(id: Int, value: Int): Unit = {}

  override def getDisplayName: IChatComponent = new ChatComponentText(getCommandSenderName)

  override def getCommandSenderName: String = "repairer"

  override def hasCustomName: Boolean = false
}
