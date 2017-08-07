package com.nekokittygames.Thaumic.Tinkerer.common.data

import java.util
import java.util.Map.Entry
import java.util.{HashMap, UUID}

import codechicken.lib.packet.PacketCustom
import codechicken.lib.packet.PacketCustom.IClientPacketHandler
import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import net.minecraft.client.Minecraft
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.{INetHandler, Packet}
import net.minecraft.network.play.INetHandlerPlayClient
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.Side
import thaumcraft.api.aspects.AspectList

/**
 * Created by Katrina on 24/05/2015.
 */
object BoundJarNetworkManager {


  object BoundJarHandler extends IClientPacketHandler {
    override def handlePacket(packetCustom: PacketCustom, minecraft: Minecraft, iNetHandlerPlayClient: INetHandlerPlayClient): Unit = {
      val id=packetCustom.readString()            //new UUID(packetCustom.readLong(), packetCustom.readLong())
      if(getData==null)
        {
          ThaumicTinkerer.logger.error("data Should never be null")
          return
        }
      if(getData.networks==null)
        {
          ThaumicTinkerer.logger.error("how the heck is networks null????")
          return
        }

      if(!getData.networks.containsKey(id))
        {
          getData.networks.put(id,new AspectList())
        }

      getData.networks.get(id).readFromNBT(packetCustom.readNBTTagCompound())
    }
  }
  private var data:BoundJarNetworkData=null

  def getPacket(boundJarWorldData:Tuple2[String,AspectList]):Packet[INetHandler]=
  {
    val packetCustom:PacketCustom=new PacketCustom(ThaumicTinkerer,2)
    packetCustom.writeString(boundJarWorldData._1)
    val nbt:NBTTagCompound=new NBTTagCompound()
    boundJarWorldData._2.writeToNBT(nbt)
    packetCustom.writeNBTTagCompound(nbt)
    packetCustom.toPacket
  }

  def loadData(): Unit = {
    // todo only do this on server side, client side should just throw all changes away and let server resent it later
    val world = ThaumicTinkerer.proxy.getOverworld
    if (world != null) {
      data = world.getMapStorage.loadData(classOf[BoundJarNetworkData], BoundJarNetworkData.IDENTIFIER).asInstanceOf[BoundJarNetworkData]
    }
    if (data == null) {
      data = new BoundJarNetworkData()
      data.markDirty()
      if (world != null) {
        world.getMapStorage.setData(BoundJarNetworkData.IDENTIFIER, data)
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

  def markDirty(uuid:String): Unit =
  {

    markDirty()
    if(FMLCommonHandler.instance().getSide==Side.SERVER)
      PacketCustom.sendToClients(getPacket(new Pair[String,AspectList](uuid,getData.networks.get(uuid))))
  }

  def getAspect(uuid:String):AspectList=
  {
    val data=getData
    if(getData==None)
      {
        return new AspectList();
      }
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
