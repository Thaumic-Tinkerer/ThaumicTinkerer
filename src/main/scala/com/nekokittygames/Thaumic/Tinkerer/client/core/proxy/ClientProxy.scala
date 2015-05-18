package com.nekokittygames.Thaumic.Tinkerer.client.core.proxy

import com.nekokittygames.Thaumic.Tinkerer.common.core.proxy.CommonProxy
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.item.Item

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
}
