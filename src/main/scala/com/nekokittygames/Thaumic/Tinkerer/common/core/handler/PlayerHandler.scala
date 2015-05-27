package com.nekokittygames.Thaumic.Tinkerer.common.core.handler

import java.util.Map.Entry
import java.util.UUID

import codechicken.lib.packet.PacketCustom
import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import thaumcraft.api.aspects.AspectList

import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import scala.collection.JavaConversions._
/**
 * Created by katsw on 23/05/2015.
 */
object PlayerHandler {


  @SubscribeEvent
  def playerLogin(event:PlayerLoggedInEvent) =
  {
    ThaumicTinkerer.logger.info("Player logged in")
      for(entry <-BoundJarNetworkManager.getData.networks.toList)
        {

          ThaumicTinkerer.logger.info(entry)
         PacketCustom.sendToPlayer(BoundJarNetworkManager.getPacket(new Pair[String,AspectList](entry._1,entry._2)),event.player)
      }
  }
}
