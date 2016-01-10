package com.nekokittygames.Thaumic.Tinkerer.common.tiles

import codechicken.lib.inventory.InventoryNBT
import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockRepairer
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import net.minecraft.block.BlockPistonBase
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{ISidedInventory, IInventory}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.INetHandlerPlayClient
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util._
import net.minecraft.world.World
import thaumcraft.api.aspects.{AspectList, IAspectContainer, Aspect, IEssentiaTransport}
import thaumcraft.api.wands.IWandable



/**
 * Created by fiona on 21/05/2015.
 */
class TileRepairer extends TileEntity with IWandable with IEssentiaTransport with IAspectContainer with ISidedInventory with ITickable{

  override def update(): Unit = ticksExisted=ticksExisted+1

  var inventory:ItemStack=null
  var ticksExisted=0
// Create a custom def to read the inventory, single item only.
  def readCustomNBT(compound: NBTTagCompound)={
    inventory=ItemStack.loadItemStackFromNBT(compound.getCompoundTag("inventory"))
  }
  // Create a custom def to write if the inventory is empty, single item only.
  def writeCustomNBT(compound: NBTTagCompound)={
    val comp=new NBTTagCompound
    if (inventory!=null)
      inventory.writeToNBT(comp)
    compound.setTag("inventory",comp)
  }
  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)
    writeCustomNBT(compound)

  }
  override def getDescriptionPacket: Packet[INetHandlerPlayClient] = {
    super.getDescriptionPacket
    val compound:NBTTagCompound=new NBTTagCompound
    writeCustomNBT(compound)
    new S35PacketUpdateTileEntity(this.pos,1,compound)
  }

  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity): Unit = {
    super.onDataPacket(net, pkt)
    readCustomNBT(pkt.getNbtCompound)
  }

  override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
    readCustomNBT(compound)

  }


  override def shouldRefresh(world: World, pos: BlockPos, oldState: IBlockState, newSate: IBlockState): Boolean = false

  override def onWandStoppedUsing(itemStack: ItemStack, world: World, entityPlayer: EntityPlayer, i: Int): Unit = {}

  override def onUsingWandTick(itemStack: ItemStack, entityPlayer: EntityPlayer, i: Int): Unit = {}

  override def onWandRightClick(world: World, itemStack: ItemStack, entityPlayer: EntityPlayer, blockPos: BlockPos, enumFacing: EnumFacing): Boolean = {
    if(entityPlayer.isSneaking){
      world.setBlockState(blockPos,world.getBlockState(blockPos).withProperty(BlockRepairer.FACING,BlockPistonBase.getFacingFromEntity(world,blockPos,entityPlayer)))
    }else{
      world.setBlockState(blockPos,world.getBlockState(blockPos).withProperty(BlockRepairer.FACING,BlockPistonBase.getFacingFromEntity(world,blockPos,entityPlayer).getOpposite))
    }
    this.markDirty()
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
//Essentia Stuff
  override def canOutputTo(enumFacing: EnumFacing): Boolean = true

  override def canInputFrom(enumFacing: EnumFacing): Boolean = true

  override def getMinimumSuction: Int = 0

  override def doesContainerAccept(aspect: Aspect): Boolean = false

  override def doesContainerContain(aspectList: AspectList): Boolean = false

  override def addToContainer(aspect: Aspect, i: Int): Int = 0

  override def containerContains(aspect: Aspect): Int = 0

  override def takeFromContainer(aspect: Aspect, i: Int): Boolean = false

  override def takeFromContainer(aspectList: AspectList): Boolean = false

  override def getAspects: AspectList = {
    val aspectList:AspectList=new AspectList(    )
    if (inventory!=null && inventory.isItemStackDamageable)
      {
        aspectList.add(Aspect.ENTROPY,inventory.getItemDamage)
      }
    return aspectList
  }

  override def setAspects(aspectList: AspectList): Unit = {}

  override def doesContainerContainAmount(aspect: Aspect, i: Int): Boolean = false
// Inventory stuff
  override def decrStackSize(index: Int, count: Int): ItemStack = {
  if (this.inventory!=null) {
    var itemStack:ItemStack=null
    if (this.inventory.stackSize <= count) {
      itemStack=this.inventory
      this.inventory=null
      this.markDirty()
      return itemStack
    }
    else {
      itemStack=this.inventory.splitStack(count)
      if (this.inventory.stackSize <= 0)
        this.inventory=null
      this.markDirty()
      return itemStack

    }
  }
  else
    return null
}


  override def markDirty(): Unit = {
    super.markDirty()
  worldObj.markBlockForUpdate(pos)
  }

  override def closeInventory(player: EntityPlayer): Unit = {}

  override def getSizeInventory: Int = 1

  override def getInventoryStackLimit: Int = 1

  override def clear(): Unit = {inventory=null}

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = true

  override def openInventory(player: EntityPlayer): Unit = {}

  override def getFieldCount: Int = 0

  override def getField(id: Int): Int = 0

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = {
    inventory=stack
    this.markDirty()
  }

  override def isUseableByPlayer(player: EntityPlayer): Boolean = true

  override def getStackInSlot(index: Int): ItemStack = inventory

  override def setField(id: Int, value: Int): Unit = {}

  override def getDisplayName: IChatComponent = new ChatComponentText(getCommandSenderName)

 def getCommandSenderName: String = LibNames.REPAIRER

  override def hasCustomName: Boolean = false

  override def getSlotsForFace(side: EnumFacing): Array[Int] = Array[Int](0)

  override def canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean = true

  override def canInsertItem(index: Int, itemStackIn: ItemStack, direction: EnumFacing): Boolean = true

  override def removeStackFromSlot(index: Int): ItemStack =
  {
    if(index!=0)
      null
    else {
      val stack = inventory;
      inventory=null;
      stack
    }
  }

  override def getName: String = getCommandSenderName
}
