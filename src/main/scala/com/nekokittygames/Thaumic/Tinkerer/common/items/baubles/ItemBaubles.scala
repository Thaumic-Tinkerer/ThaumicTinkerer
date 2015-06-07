package com.nekokittygames.Thaumic.Tinkerer.common.items.baubles

import baubles.api.{BaubleType, IBauble}
import com.nekokittygames.Thaumic.Tinkerer.common.items.ModItem
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack

/**
 * Created by Katrina on 05/06/2015.
 */
abstract class ItemBaubles(baubleType: BaubleType) extends ModItem with IBauble {
this.setMaxStackSize(1)

  override def getBaubleType(itemstack: ItemStack): BaubleType = baubleType

  override def canEquip(itemstack: ItemStack, player: EntityLivingBase): Boolean = true

  override def canUnequip(itemstack: ItemStack, player: EntityLivingBase): Boolean = true
}
