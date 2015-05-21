package com.nekokittygames.Thaumic.Tinkerer.common.tiles

import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import net.minecraft.block.BlockHopper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.server.gui.IUpdatePlayerListBox
import net.minecraft.tileentity.{TileEntityHopper, TileEntity}
import net.minecraft.util.{BlockPos, ChatComponentText, EnumFacing, IChatComponent}
import thaumcraft.api.aspects.{IEssentiaContainerItem, AspectList, Aspect, IAspectContainer}
import thaumcraft.common.blocks.devices.BlockJarItem
import thaumcraft.common.tiles.devices.{TileJarFillableVoid, TileJarFillable}

/**
 * Created by Katrina on 18/05/2015.
 */
class TileFunnel extends TileEntity with ISidedInventory with IAspectContainer with IUpdatePlayerListBox{

  var inventory:ItemStack=null
  override def getSlotsForFace(side: EnumFacing): Array[Int] = Array(0)

  override def canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean = false

  override def canInsertItem(index: Int, itemStackIn: ItemStack, direction: EnumFacing): Boolean = itemStackIn.getItem.isInstanceOf[BlockJarItem]

  override def doesContainerAccept(aspect: Aspect): Boolean = false

  override def doesContainerContain(aspectList: AspectList): Boolean = false

  override def addToContainer(aspect: Aspect, i: Int): Int = 0

  override def containerContains(aspect: Aspect): Int = 0

  override def takeFromContainer(aspect: Aspect, i: Int): Boolean = false

  override def takeFromContainer(aspectList: AspectList): Boolean = false

  override def getAspects: AspectList =
  {
    if(inventory!=null && inventory.getItem.isInstanceOf[IEssentiaContainerItem]) inventory.getItem.asInstanceOf[IEssentiaContainerItem].getAspects(inventory) else null
  }

  override def setAspects(aspectList: AspectList): Unit = {}

  override def doesContainerContainAmount(aspect: Aspect, i: Int): Boolean = false

  override def decrStackSize(index: Int, count: Int): ItemStack = {
    if (this.inventory != null) {
      var itemstack: ItemStack = null
      if (this.inventory.stackSize <= count) {
        itemstack = this.inventory
        this.inventory = null
        this.markDirty
        return itemstack
      }
      else {
        itemstack = this.inventory.splitStack(count)
        if (this.inventory.stackSize == 0) {
          this.inventory = null
        }
        this.markDirty
        return itemstack
      }
    }
    else {
      return null
    }

  }

  override def closeInventory(player: EntityPlayer): Unit = {}

  override def getSizeInventory: Int = 1

  override def getInventoryStackLimit: Int = 1

  override def clear(): Unit = inventory=null

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = stack.getItem.isInstanceOf[BlockJarItem] && stack.getItemDamage==0

  override def getStackInSlotOnClosing(index: Int): ItemStack = inventory

  override def openInventory(player: EntityPlayer): Unit = {}

  override def getFieldCount: Int = 0

  override def getField(id: Int): Int = 0

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = inventory=stack

  override def isUseableByPlayer(player: EntityPlayer): Boolean = true

  override def getStackInSlot(index: Int): ItemStack = inventory

  override def setField(id: Int, value: Int): Unit = {}

  override def getDisplayName: IChatComponent = new ChatComponentText(getCommandSenderName)

  override def getCommandSenderName: String = LibNames.FUNNEL

  override def hasCustomName: Boolean = false

  override def markDirty(): Unit =
  {
    super.markDirty()
    worldObj.markBlockForUpdate(pos)
  }

  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity): Unit =
  {
    super.onDataPacket(net, pkt)
    readCustomNBT(pkt.getNbtCompound)
  }

  override def writeToNBT(compound: NBTTagCompound): Unit =
  {
    super.writeToNBT(compound)
    writeCustomNBT(compound)
  }

  override def getDescriptionPacket: Packet =
  {
    super.getDescriptionPacket
    val nbttagcompound: NBTTagCompound = new NBTTagCompound
    this.writeCustomNBT(nbttagcompound)

    new S35PacketUpdateTileEntity(this.pos,1,nbttagcompound)
  }

  override def readFromNBT(compound: NBTTagCompound): Unit =
  {
    super.readFromNBT(compound)
    readCustomNBT(compound)
  }


  def readCustomNBT(compound: NBTTagCompound)=
  {
    inventory=ItemStack.loadItemStackFromNBT(compound.getCompoundTag("inventory"))
  }
  def writeCustomNBT(compound: NBTTagCompound)=
  {
    val comp=new NBTTagCompound
    if(inventory!=null)
      inventory.writeToNBT(comp)
    compound.setTag("inventory",comp)
  }

  def getHopperFacing(pos: BlockPos, getBlockMetadata: Int):TileEntity =
  {
    val i=BlockHopper.getFacing(getBlockMetadata)
    return worldObj.getTileEntity(pos.offset(i))
  }

  override def update(): Unit =
  {
    if(inventory!=null && inventory.getItem.isInstanceOf[BlockJarItem])
      {
        if(!worldObj.isRemote)
          {
            val item:BlockJarItem=inventory.getItem.asInstanceOf[BlockJarItem]
            val aspectList=item.getAspects(inventory)
            if(aspectList!=null && aspectList.size()==1)
              {
                val aspect=aspectList.getAspects()(0)
                val tile=worldObj.getTileEntity(pos.down(1))
                if(tile!=null && tile.isInstanceOf[TileEntityHopper])
                  {
                    val hopperd=getHopperFacing(tile.getPos,tile.getBlockMetadata)
                    if(hopperd.isInstanceOf[TileJarFillable])
                    {
                      val jar1=hopperd.asInstanceOf[TileJarFillable]
                      val void=jar1.isInstanceOf[TileJarFillableVoid]
                      val jarAspects=jar1.getAspects
                      if(jarAspects!=null && jarAspects.size() ==0 && (jar1.aspectFilter==null || jar1.aspectFilter==aspect) || jarAspects.getAspects()(0)==aspect && (jarAspects.getAmount(jarAspects.getAspects()(0))< 64 || void))
                        {
                          jar1.addToContainer(aspect,1)
                          item.setAspects(inventory,aspectList.remove(aspect,1))
                        }
                    }
                  }
              }
          }
      }
  }
}
