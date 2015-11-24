package com.nekokittygames.Thaumic.Tinkerer.common;

import com.nekokittygames.Thaumic.Tinkerer.api.ThaumicTinkererAPI
import com.nekokittygames.Thaumic.Tinkerer.common.blocks.ModBlocks
import com.nekokittygames.Thaumic.Tinkerer.common.core.handler.PlayerHandler
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.{MobAspects, TTConfig}
import com.nekokittygames.Thaumic.Tinkerer.common.core.proxy.CommonProxy
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import com.nekokittygames.Thaumic.Tinkerer.common.items.ModItems
import com.nekokittygames.Thaumic.Tinkerer.common.items.baubles.ItemFoodTalisman
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibMisc
import com.nekokittygames.Thaumic.Tinkerer.common.research.{ModRecipes, ModResearch}
import net.minecraft.entity.Entity
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import net.minecraftforge.common.util.Constants
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage
import net.minecraftforge.fml.common.event.{FMLInterModComms, FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common._
import org.apache.logging.log4j.Logger
import thaumcraft.api.aspects.Aspect
import thaumcraft.common
import thaumcraft.common.Thaumcraft
import scala.collection.JavaConversions._
import scala.collection.mutable

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
    Loader.instance().getLoaderState

  }


  @EventHandler
  def init(eventArgs:FMLInitializationEvent): Unit =
  {
    proxy.init(eventArgs)
  }


  @EventHandler
  def imcCallback(event:FMLInterModComms.IMCEvent)= {

    for (message: IMCMessage <- event.getMessages) {
      if (message.key.equalsIgnoreCase(ThaumicTinkererAPI.FOOD_TALISMAN_IMC)) {
        ItemFoodTalisman.foodBlacklist += message.getStringValue
      }
      if (message.key.equalsIgnoreCase(ThaumicTinkererAPI.MOB_ASPECT_IMC)) {
        val cmp = message.getNBTValue
        try {
          val clazz: Class[_ <: Entity] = Class.forName(cmp.getString(ThaumicTinkererAPI.ENTITY_CLASS_NAME)).asInstanceOf[Class[_ <: Entity]]

          val aspects: scala.collection.mutable.MutableList[Aspect] = new mutable.MutableList[Aspect]()
          val aspectcmp: NBTTagList = message.getNBTValue.getTagList(ThaumicTinkererAPI.ASPECT_NAME, Constants.NBT.TAG_COMPOUND)
          for (i <- 0 until aspectcmp.tagCount()) {
            aspects.+=(Aspect.getAspect(aspectcmp.getCompoundTagAt(i).getString(ThaumicTinkererAPI.ASPECT_NAME)))
          }
          MobAspects.aspects.put(clazz, aspects.toArray)

        }
      }
    }
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
    proxy.registerTileEntityRenders()
  }

}
