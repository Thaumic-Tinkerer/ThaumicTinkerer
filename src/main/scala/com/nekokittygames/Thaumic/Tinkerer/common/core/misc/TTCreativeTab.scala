package com.nekokittygames.Thaumic.Tinkerer.common.core.misc

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockBoundJar
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.Item


/**
 * Created by Katrina on 17/05/2015.
 */
object TTCreativeTab extends CreativeTabs("ThaumicTinkerer"){
  override def getTabIconItem: Item = Item.getItemFromBlock(BlockBoundJar)
}
