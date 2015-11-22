package com.nekokittygames.Thaumic.Tinkerer.common.core.proxy

import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkData
import com.nekokittygames.Thaumic.Tinkerer.common.integration.oc.DriverThaumometerUpgrade
import com.nekokittygames.Thaumic.Tinkerer.common.items.ModItem
import li.cil.oc.api.Driver
import net.minecraft.block.Block
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.world.World
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.fml.common.{Loader, Optional}
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPreInitializationEvent}

/**
 * Created by Katrina on 17/05/2015.
 */
class CommonProxy {
  def registerTest() = {}

  def registerInventoryItem(item: Item, name: String) = {

  }

  def registerInventoryItem(item: Item, name: String,meta:Int) = {

  }

  def registerInventoryBlock(block: Block,name: String): Unit=
  {

  }
  def registerInventoryBlockWithResourceDomain(block: Block,location: String): Unit=
  {

  }
  def registerInventoryBlockWithResourceDomain(block: Block,location: String,meta:Int): Unit=
  {

  }
  def registerInventoryBlock(block: Block,name: String,meta:Int): Unit=
  {

  }

  @Optional.Method(modid = "OpenComputers")
  def registerOCItems(): Unit =
  {
    Driver.add(new DriverThaumometerUpgrade)
  }

  def getOverworld:World=
  {
    DimensionManager.getWorld(0)
  }

  def init(event:FMLInitializationEvent): Unit =
  {
    if(Loader.isModLoaded("OpenComputers"))
      {
        registerOCItems();
      }
  }

  def registerTileEntityRenders(): Unit =
  {

  }


  def registerPacketHandlers()=
  {

  }

  def registerItemBakery(item:ModItem,names:Array[String])={}
}
