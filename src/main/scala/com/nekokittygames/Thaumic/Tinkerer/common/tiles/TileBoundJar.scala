package com.nekokittygames.Thaumic.Tinkerer.common.tiles

import java.util.UUID

import net.minecraft.nbt.NBTTagCompound
import thaumcraft.common.tiles.devices.{TileJarFillable, TileJar}

/**
 * Created by Katrina on 23/05/2015.
 */
class TileBoundJar  extends TileJarFillable{


  var network:UUID=UUID.randomUUID()


  override def validate(): Unit =
    {
      super.validate()
    }

  override def writeCustomNBT(nbttagcompound: NBTTagCompound): Unit =
    {
      super.writeCustomNBT(nbttagcompound)
      nbttagcompound.setLong("HighID",network.getMostSignificantBits)
      nbttagcompound.setLong("LowID",network.getLeastSignificantBits)
    }

  override def readCustomNBT(nbttagcompound: NBTTagCompound): Unit =
    {
      super.readCustomNBT(nbttagcompound)
      network=new UUID(nbttagcompound.getLong("HighID"),nbttagcompound.getLong("LowID"))
    }
}
