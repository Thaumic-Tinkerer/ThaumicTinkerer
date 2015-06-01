package com.nekokittygames.Thaumic.Tinkerer.common.tiles

import java.util.UUID

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.StringID
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.item.EnumDyeColor
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
  var aspectList:AspectList=new AspectList()


  override def update(): Unit =
    {
      aspectList=BoundJarNetworkManager.getAspect(network).copy()
      aspect=aspectList.getAspects()(0)
      amount=aspectList.getAmount(aspect)
      super.update()
    }

  override def validate(): Unit =
    {
      super.validate()
    }


  override def markDirty(): Unit = {
    super.markDirty()
    aspectList.remove(aspect)
    aspectList.add(aspect,amount)
    val oldAspects=BoundJarNetworkManager.getAspect(network)
    oldAspects.remove(oldAspects.getAspects()(0))
    oldAspects.add(aspect,amount)
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
          this.amount = this.amount+ added;
          amount = amount- added;
        }


        this.markDirty();
        this.worldObj.markBlockForUpdate(this.pos);

        return amount;
      }
    }



  override def takeFromContainer(tt: Aspect, am: Int): Boolean =
    {
      if(this.amount >= am && tt == this.aspect) {
        this.amount = this.amount - am;
        if(this.amount <= 0) {
          this.aspect = null;
          this.amount = 0;
        }
        this.markDirty();
        this.worldObj.markBlockForUpdate(this.pos);
        return true;
      } else {
        return false;
      }
    }


  override def setAspects(aspects: AspectList): Unit =
    {
      super.setAspects(aspects)
      this.markDirty()
      this.worldObj.markBlockForUpdate(this.pos);
    }
}
