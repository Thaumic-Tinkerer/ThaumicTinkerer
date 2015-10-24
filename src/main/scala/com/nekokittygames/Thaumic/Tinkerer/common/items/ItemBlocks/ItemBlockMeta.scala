package com.nekokittygames.Thaumic.Tinkerer.common.items.ItemBlocks

import com.nekokittygames.Thaumic.Tinkerer.api.IMetaBlockName
import net.minecraft.block.Block
import net.minecraft.item.{ItemStack, ItemBlock}

/**
 * Created by Katrina on 18/05/2015.
 */
class ItemBlockMeta(block:Block) extends ItemBlock(block){
if(!block.isInstanceOf[IMetaBlockName])
  throw new IllegalArgumentException(String.format("The given block %s is not an instanc of IMetaBlockName",block.getUnlocalizedName))
  this.setMaxDamage(0)
  this.setHasSubtypes(true)

  override def getMetadata(damage: Int): Int = damage

  override def getUnlocalizedName(stack: ItemStack): String = super.getUnlocalizedName(stack)+"."+block.asInstanceOf[IMetaBlockName].getSpecialName(stack)
}
