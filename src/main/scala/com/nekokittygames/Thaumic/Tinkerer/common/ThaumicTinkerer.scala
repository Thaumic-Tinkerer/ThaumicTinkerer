package com.nekokittygames.Thaumic.Tinkerer.common;

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.ModBlocks
import com.nekokittygames.Thaumic.Tinkerer.common.core.proxy.CommonProxy
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibMisc
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}
;
import net.minecraftforge.fml.common.{SidedProxy, Mod}
import org.apache.logging.log4j.Logger
import thaumcraft.common
import thaumcraft.common.Thaumcraft
;

/**
 * Created by Katrina on 17/05/2015.
 */
@Mod(modid = LibMisc.MOD_ID,name = LibMisc.MOD_NAME,version = LibMisc.VERSION,dependencies = LibMisc.DEPENDENCIES,guiFactory = LibMisc.GUI_FACTORY,modLanguage = "scala")
object ThaumicTinkerer {
  final var logger:Logger=null


  @SidedProxy(clientSide = LibMisc.CLIENT_PROXY,serverSide = LibMisc.COMMON_PROXY)
  var proxy:CommonProxy=null


  var ThaumcraftProxy:common.CommonProxy=null

  @EventHandler
  def preInit(eventArgs:FMLPreInitializationEvent)=
  {
    ThaumcraftProxy=Thaumcraft.proxy
    logger=eventArgs.getModLog
    ModBlocks.registerBlocks()
    proxy.registerTileEntityRenders()
  }



  @EventHandler
  def Init(eventArgs:FMLInitializationEvent) =
  {
    ModBlocks.registerBlocksInventory()
  }
}
