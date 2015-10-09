package com.nekokittygames.Thaumic.Tinkerer.common.core.proxy

import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkData
import com.nekokittygames.Thaumic.Tinkerer.common.items.ModItem
import net.minecraft.block.Block
import net.minecraft.world.World
import net.minecraftforge.common.DimensionManager

/**
 * Created by Katrina on 17/05/2015.
 */
class CommonProxy {
  def registerInventoryItem(item: ModItem, name: String) = {

  }

  def registerInventoryItem(item: ModItem, name: String,meta:Int) = {

  }

  def registerInventoryBlock(block: Block,name: String): Unit=
  {

  }
  def registerInventoryBlock(block: Block,name: String,meta:Int): Unit=
  {

  }



  def getOverworld:World=
  {
    DimensionManager.getWorld(0)
  }


  def registerTileEntityRenders(): Unit =
  {

  }


  def registerPacketHandlers()=
  {

  }

  def registerItemBakery(item:ModItem,names:Array[String])={}
}
