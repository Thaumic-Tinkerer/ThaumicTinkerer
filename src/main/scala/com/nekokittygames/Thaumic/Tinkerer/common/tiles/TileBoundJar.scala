package com.nekokittygames.Thaumic.Tinkerer.common.tiles

import java.util.UUID

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.StringID
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.common.tiles.devices.{TileJarFillable, TileJar}

/**
 * Created by Katrina on 23/05/2015.
 */
class TileBoundJar  extends TileJarFillable{


  var network:String=StringID.getName() // TODO: Choose random network name
  var jarColor:Int=0
  var aspectList:AspectList=null

  override def validate(): Unit =
    {
      super.validate()
    }


  override def markDirty(): Unit = {
    super.markDirty()
    val oldAspects=BoundJarNetworkManager.getAspect(network)
    oldAspects.remove(oldAspects.getAspects()(0))
    oldAspects.add(aspectList.getAspects()(0),aspectList.getAmount(aspectList.getAspects()(0)))
    aspect=aspectList.getAspects()(0)
    amount=aspectList.getAmount(aspectList.getAspects()(0))
    BoundJarNetworkManager.markDirty(network)
  }

  override def writeCustomNBT(nbttagcompound: NBTTagCompound): Unit =
    {
      super.writeCustomNBT(nbttagcompound)
      nbttagcompound.setString("network",network)
      nbttagcompound.setInteger("jarColor",jarColor)
    }

  override def readCustomNBT(nbttagcompound: NBTTagCompound): Unit =
    {
      super.readCustomNBT(nbttagcompound)
      network=nbttagcompound.getString("network")
      jarColor=nbttagcompound.getInteger("jarColor")
    }



  override def addToContainer(tt: Aspect,  am: Int): Int =
    {
      var amount=am
      if(amount == 0) {
        return amount;
      } else {
        if(this.amount < this.maxAmount && tt == this.aspect || this.amount == 0) {
          this.aspect = tt;
          var added = Math.min(am, this.maxAmount - this.amount);
          this.amount = this.amount- added;
          amount = amount- added;
        }


        this.markDirty();
        this.worldObj.markBlockForUpdate(this.pos);
        return amount;
      }
    }

  override def containerContains(tag: Aspect): Int = super.containerContains(tag)

  override def takeFromContainer(tt: Aspect, am: Int): Boolean = super.takeFromContainer(tt, am)

  override def takeFromContainer(ot: AspectList): Boolean = super.takeFromContainer(ot)

  override def getAspects: AspectList = super.getAspects

  override def getEssentiaType(loc: EnumFacing): Aspect = super.getEssentiaType(loc)

  override def setAspects(aspects: AspectList): Unit = super.setAspects(aspects)

  override def doesContainerContainAmount(tag: Aspect, amt: Int): Boolean = super.doesContainerContainAmount(tag, amt)
}
