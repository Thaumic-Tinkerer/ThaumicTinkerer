package com.nekokittygames.Thaumic.Tinkerer.common.core.handler

import codechicken.lib.packet.PacketCustom
import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarManager
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarManager.BoundJarHandler
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
      BoundJarManager.map.values.foreach{

          f => ThaumicTinkerer.logger.info(f)
         PacketCustom.sendToPlayer(BoundJarManager.getPacket(f),event.player)
      }
  }
}
