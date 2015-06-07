package com.nekokittygames.Thaumic.Tinkerer.common;

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.ModBlocks
import com.nekokittygames.Thaumic.Tinkerer.common.core.handler.PlayerHandler
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTConfig
import com.nekokittygames.Thaumic.Tinkerer.common.core.proxy.CommonProxy
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import com.nekokittygames.Thaumic.Tinkerer.common.items.ModItems
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibMisc
import com.nekokittygames.Thaumic.Tinkerer.common.research.{ModRecipes, ModResearch}
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInterModComms, FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Loader, FMLCommonHandler, Mod, SidedProxy}
import org.apache.logging.log4j.Logger
import thaumcraft.common
import thaumcraft.common.Thaumcraft
;

/**
 * Created by Katrina on 17/05/2015.
 */
@Mod(modid = LibMisc.MOD_ID,name = LibMisc.MOD_NAME,version = LibMisc.VERSION,dependencies = LibMisc.DEPENDENCIES,guiFactory = LibMisc.GUI_FACTORY,modLanguage = "scala")
object ThaumicTinkerer {
  final var logger: Logger = null


  @SidedProxy(clientSide = LibMisc.CLIENT_PROXY, serverSide = LibMisc.COMMON_PROXY)
  var proxy: CommonProxy = null


  var ThaumcraftProxy: common.CommonProxy = null

  @EventHandler
  def preInit(eventArgs: FMLPreInitializationEvent) = {
    TTConfig.init(eventArgs.getSuggestedConfigurationFile)
    ThaumcraftProxy = Thaumcraft.proxy
    logger = eventArgs.getModLog
    ModBlocks.registerBlocks(eventArgs)
    ModItems.registerItems(eventArgs)
    proxy.registerTileEntityRenders()
  }


  @EventHandler
  def Init(eventArgs: FMLInitializationEvent) = {
    ModBlocks.registerBlocksInventory()
    ModItems.registerInventoryItems()
    proxy.registerPacketHandlers()
    FMLCommonHandler.instance().bus().register(PlayerHandler)

    if (Loader.isModLoaded("Waila")) {
      FMLInterModComms.sendMessage("Waila", "register", "com.nekokittygames.Thaumic.Tinkerer.common.integration.Waila.callbackRegister");
    }

  }

  @EventHandler
  def postInit(eventArgs: FMLPostInitializationEvent) =
  {
    ModRecipes.registerRecipes()
    ModResearch.registerResearch()
  }

}
