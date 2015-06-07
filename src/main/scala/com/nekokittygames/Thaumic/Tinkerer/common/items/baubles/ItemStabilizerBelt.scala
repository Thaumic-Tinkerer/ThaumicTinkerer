package com.nekokittygames.Thaumic.Tinkerer.common.items.baubles

import java.util.UUID

import baubles.api.BaubleType
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibItemNames
import net.minecraft.entity.{SharedMonsterAttributes, EntityLivingBase}
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

/**
 * Created by Katrina on 05/06/2015.
 */
object ItemStabilizerBelt extends ItemBaubles(BaubleType.BELT) {

  final val uuid:UUID = UUID.fromString("0d40c1fa-b89c-4f74-8295-74d484fa8c24");
  final val knockbackBoost:AttributeModifier = new AttributeModifier(uuid,"KBRESIST", 1.0D, 0).setSaved(true);
  setUnlocalizedName(LibItemNames.STABILIZERBELT)



  override def onWornTick(itemstack: ItemStack, player: EntityLivingBase): Unit = {}

  override def onUnequipped(itemstack: ItemStack, player: EntityLivingBase): Unit =
  {
    val play=player.asInstanceOf[EntityPlayer]
    if(play.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getModifier(uuid)==null)
      play.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(knockbackBoost)
  }

  override def onEquipped(itemstack: ItemStack, player: EntityLivingBase): Unit =
  {
    val play=player.asInstanceOf[EntityPlayer]
    if(play.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getModifier(uuid)!=null)
      play.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).removeModifier(knockbackBoost)
  }
}
