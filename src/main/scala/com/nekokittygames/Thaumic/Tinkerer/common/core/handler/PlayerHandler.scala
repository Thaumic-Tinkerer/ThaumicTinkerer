package com.nekokittygames.Thaumic.Tinkerer.common.core.handler

import codechicken.lib.packet.PacketCustom
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import thaumcraft.api.aspects.AspectList

import scala.collection.JavaConversions._
/**
 * Created by katsw on 23/05/2015.
 */
object PlayerHandler {


  @SubscribeEvent
  def playerLogin(event:PlayerLoggedInEvent) =
  {
      for(entry <-BoundJarNetworkManager.getData.networks.toList)
        {
         PacketCustom.sendToPlayer(BoundJarNetworkManager.getPacket(new Pair[String,AspectList](entry._1,entry._2)),event.player)
      }
  }
}
