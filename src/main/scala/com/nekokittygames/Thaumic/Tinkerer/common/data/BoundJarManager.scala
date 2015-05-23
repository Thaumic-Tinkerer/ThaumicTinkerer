package com.nekokittygames.Thaumic.Tinkerer.common.data

import java.util.UUID

import codechicken.lib.packet.PacketCustom
import codechicken.lib.packet.PacketCustom.IClientPacketHandler
import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import net.minecraft.client.Minecraft
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.Packet
import net.minecraft.network.play.INetHandlerPlayClient
import net.minecraft.world.World
import thaumcraft.api.aspects.AspectList

/**
 * Created by katsw on 23/05/2015.
 */
object BoundJarManager {
  val map = scala.collection.mutable.Map[UUID, BoundJarWorldData]()

  object BoundJarHandler extends IClientPacketHandler {
    override def handlePacket(packetCustom: PacketCustom, minecraft: Minecraft, iNetHandlerPlayClient: INetHandlerPlayClient): Unit = {
      val id: UUID = new UUID(packetCustom.readLong(), packetCustom.readLong())
      val aspectList: AspectList = new AspectList()
      aspectList.readFromNBT(packetCustom.readNBTTagCompound())
      val data:Option[BoundJarWorldData]=map.get(id)
      if(data.nonEmpty)
        {
          val nbt:NBTTagCompound=new NBTTagCompound()
          aspectList.writeToNBT(nbt)
          data.get.aspect.readFromNBT(nbt)
        }
      else
      {
        map(id)=new BoundJarWorldData()
        val nbt:NBTTagCompound=new NBTTagCompound()
        aspectList.writeToNBT(nbt)
        map(id).aspect.readFromNBT(nbt)
      }

    }
  }


  def markDirty(world:World,uuid:UUID)=
  {
    PacketCustom.sendToClients(getPacket(getBoundJarData(world,uuid)))
  }



  def getPacket(boundJarWorldData: BoundJarWorldData):Packet=
  {
    val packetCustom:PacketCustom=new PacketCustom(ThaumicTinkerer,2)
    packetCustom.writeLong(boundJarWorldData.uuid.getMostSignificantBits)
    packetCustom.writeLong(boundJarWorldData.uuid.getLeastSignificantBits)
    val nbt:NBTTagCompound=new NBTTagCompound()
    boundJarWorldData.aspect.writeToNBT(nbt)
    packetCustom.writeNBTTagCompound(nbt)
    packetCustom.toPacket
  }

  def getBoundJarData(world:World,uuid:UUID):BoundJarWorldData=
  {
    val data:Option[BoundJarWorldData]=map.get(uuid)
    if(data.nonEmpty)
      data.get
    else
      {
        BoundJarWorldData.getJarData(world,uuid)
      }
  }
}
