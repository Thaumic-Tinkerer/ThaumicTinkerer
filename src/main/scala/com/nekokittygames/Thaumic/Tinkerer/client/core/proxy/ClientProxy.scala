package com.nekokittygames.Thaumic.Tinkerer.client.core.proxy

import codechicken.lib.packet.PacketCustom
import com.nekokittygames.Thaumic.Tinkerer.client.renders.tiles.{TileRepairerRenderer, TileFunnelRenderer}
import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import com.nekokittygames.Thaumic.Tinkerer.common.blocks.quartz.BlockDarkQuartzPatterned
import com.nekokittygames.Thaumic.Tinkerer.common.core.proxy.CommonProxy
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import com.nekokittygames.Thaumic.Tinkerer.common.items.ItemBlocks.ItemBlockMeta
import com.nekokittygames.Thaumic.Tinkerer.common.items.ModItem
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.{TileRepairer, TileFunnel}
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.model.{ModelBakery, ModelResourceLocation}
import net.minecraft.item.Item
import net.minecraftforge.fml.client.registry.ClientRegistry

/**
 * Created by Katrina on 17/05/2015.
 */
class ClientProxy extends CommonProxy{



  override def registerInventoryBlock(block: Block,name: String): Unit=
  {
    registerInventoryBlock(block,name,0)
  }

  override def registerInventoryBlock(block: Block, name: String, meta: Int): Unit =
  {
    Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(Item.getItemFromBlock(block),meta,new ModelResourceLocation("thaumictinkerer:"+name,"inventory"))
  }

  override def registerTileEntityRenders(): Unit =
  {
    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileFunnel], new TileFunnelRenderer);
    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileRepairer], new TileRepairerRenderer)
  }


  override def registerTest(): Unit =
    {
      Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(new ItemBlockMeta(BlockDarkQuartzPatterned),3,new ModelResourceLocation("thaumictinkerer:"+LibNames.DARK_QUARTZ_PILLAR,"inventory"))
    }

  override def registerInventoryItem(item: Item, name: String, meta: Int): Unit =
  {
    Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(item, meta, new ModelResourceLocation("thaumictinkerer:" + name, "inventory"))
    //Minecraft.getMinecraft.getRenderItem.getItemModelMesher.

  }



  override def registerInventoryBlockWithResourceDomain(block: Block, location: String): Unit =
  {
    registerInventoryBlock(block,location,0)
  }

  override def registerInventoryBlockWithResourceDomain(block: Block, location: String, meta: Int): Unit =
  {
    Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(Item.getItemFromBlock(block),meta,new ModelResourceLocation(location,"inventory"))
  }

  override def registerItemBakery(item:ModItem,names:Array[String])=
  {
    ModelBakery.addVariantName(item,names:_*)
  }

  override def registerInventoryItem(item: Item, name: String): Unit = {
    registerInventoryItem(item,name,0)
  }

  override def registerPacketHandlers(): Unit = {

    PacketCustom.assignHandler(ThaumicTinkerer,BoundJarNetworkManager.BoundJarHandler)
  }
}
