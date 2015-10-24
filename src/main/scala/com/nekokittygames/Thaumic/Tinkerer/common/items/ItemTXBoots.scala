package com.nekokittygames.Thaumic.Tinkerer.common.items

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.ItemNBT
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import thaumcraft.api.ThaumcraftMaterials
import thaumcraft.common.Thaumcraft
import thaumcraft.common.config.Config
import thaumcraft.common.items.armor.{Hover, ItemBootsTraveller}

/**
 * Created by katsw on 24/10/2015.
 */
class ItemTXBoots extends ItemBootsTraveller(ThaumcraftMaterials.ARMORMAT_SPECIAL, 4, 3) with ModItem{

  final var RUNTICKS="runTicks"

  override def onArmorTick(world: World, player: EntityPlayer, itemStack: ItemStack): Unit =
  {

    if(!player.capabilities.isFlying && player.moveForward > 0.0F) {
      val haste = EnchantmentHelper.getEnchantmentLevel(Config.enchHaste.effectId, itemStack);
      if(player.worldObj.isRemote && !player.isSneaking()) {
        if(!Thaumcraft.instance.playerEvents.prevStep.containsKey(Integer.valueOf(player.getEntityId()))) {
          Thaumcraft.instance.playerEvents.prevStep.put(Integer.valueOf(player.getEntityId()), player.stepHeight);
        }

        player.stepHeight = 1.0F;
      }
      if(!ItemNBT.getItemStackTag(itemStack).hasKey(RUNTICKS))
      {
        ItemNBT.getItemStackTag(itemStack).setInteger(RUNTICKS,0)
      }

      if(player.onGround) {
        var ticks = ItemNBT.getItemStackTag(itemStack).getInteger(RUNTICKS);
        var bonus:Float = 0.110F;
        bonus = bonus+((ticks/5)*0.003F);
        if(player.isInWater()) {
          bonus = bonus/4.0F;
        }

        player.moveFlying(0.0F, 1.0F, bonus);
      } else if(Hover.getHover(player.getEntityId())) {
        player.jumpMovementFactor = 0.03F;
      } else {
        player.jumpMovementFactor = 0.05F;
      }
    }

    if(player.fallDistance > 0.0F) {
      player.fallDistance -= 0.25F;
    }
  }
}
