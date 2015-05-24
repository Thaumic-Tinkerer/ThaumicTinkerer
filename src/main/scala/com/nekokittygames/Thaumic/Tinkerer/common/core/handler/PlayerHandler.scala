package com.nekokittygames.Thaumic.Tinkerer.common.core.handler

import java.util.Map.Entry
import java.util.UUID

import codechicken.lib.packet.PacketCustom
import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import
import thaumcraft.api.aspects.AspectList

import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent

/**
 * Created by katsw on 23/05/2015.
 */
object PlayerHandler {


  @SubscribeEvent
  def playerLogin(event:PlayerLoggedInEvent) =
  {
    ThaumicTinkerer.logger.info("Player logged in")
      for(entry:Entry[UUID,AspectList] <-BoundJarNetworkManager.data.networks.entrySet())
        {

          ThaumicTinkerer.logger.info(entry)
         PacketCustom.sendToPlayer(BoundJarNetworkManager.getPacket(new Pair[UUID,AspectList](entry.getKey,entry.getValue)),event.player)
      }
  }
}
