package com.nekokittygames.Thaumic.Tinkerer.common.data

import java.util
import java.util.Map.Entry
import java.util.{HashMap, UUID}

import codechicken.lib.packet.PacketCustom
import codechicken.lib.packet.PacketCustom.IClientPacketHandler
import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import net.minecraft.client.Minecraft
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.Packet
import net.minecraft.network.play.INetHandlerPlayClient
import net.minecraftforge.common.DimensionManager
import thaumcraft.api.aspects.AspectList

/**
 * Created by Katrina on 24/05/2015.
 */
object BoundJarNetworkManager {


  object BoundJarHandler extends IClientPacketHandler {
    override def handlePacket(packetCustom: PacketCustom, minecraft: Minecraft, iNetHandlerPlayClient: INetHandlerPlayClient): Unit = {
      val id: UUID = new UUID(packetCustom.readLong(), packetCustom.readLong())
      if(!data.networks.containsKey(id))
        {
          data.networks.put(id,new AspectList())
        }

      data.networks.get(id).readFromNBT(packetCustom.readNBTTagCompound())
    }
  }
  private var data:BoundJarNetworkData=null

  def getPacket(boundJarWorldData:Tuple2[UUID,AspectList]):Packet=
  {
    val packetCustom:PacketCustom=new PacketCustom(ThaumicTinkerer,2)
    packetCustom.writeLong(boundJarWorldData._1.getMostSignificantBits)
    packetCustom.writeLong(boundJarWorldData._1.getLeastSignificantBits)
    val nbt:NBTTagCompound=new NBTTagCompound()
    boundJarWorldData._2.writeToNBT(nbt)
    packetCustom.writeNBTTagCompound(nbt)
    packetCustom.toPacket
  }

  def loadData(): Unit =
  {
    val world=ThaumicTinkerer.proxy.getOverworld
    if(world!=null) {
      data = world.getMapStorage.loadData(classOf[BoundJarNetworkData], BoundJarNetworkData.IDENTIFIER).asInstanceOf[BoundJarNetworkData]
      if(data==null) {
        data = new BoundJarNetworkData()
        data.markDirty()
        world.getMapStorage.setData(BoundJarNetworkData.IDENTIFIER,data)
      }
    }
  }

  def markDirty(): Unit =
  {
    val world=ThaumicTinkerer.proxy.getOverworld
    data.markDirty()
    if(world!=null)
      world.getMapStorage.saveAllData()

  }

  def markDirty(uuid:UUID): Unit =
  {

    markDirty()
    PacketCustom.sendToClients(getPacket(new Pair[UUID,AspectList](uuid,getData.networks.get(uuid))))
  }

  def getAspect(uuid:UUID):AspectList=
  {
    if(!getData.networks.containsKey(uuid))
      getData.networks.put(uuid,new AspectList())

    getData.networks.get(uuid)
  }
  def getData:BoundJarNetworkData =
    {
      if(data==null)
        {
          loadData()
          data
        }
      else
        {
          data
        }
    }

}
