package com.nekokittygames.Thaumic.Tinkerer.common.items.baubles

import java.util

import baubles.api.BaubleType
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.ItemNBT
import com.nekokittygames.Thaumic.Tinkerer.common.items.ModItems
import com.nekokittygames.Thaumic.Tinkerer.common.libs.{LibItemNames, LibFeatures}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.potion.{Potion, PotionEffect}
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.ReflectionHelper
import thaumcraft.common.config.Config
import thaumcraft.common.lib.potions.PotionWarpWard
import scala.collection.JavaConversions._
/**
 * Created by katsw on 30/10/2015.
 */
object ItemCleaningTalisman extends ItemBaubles(BaubleType.AMULET) {

  final val TAG_ENABLED="enabled"
  final val WARP_POTIONS=Array[Int](Config.potionBlurredID,Config.potionDeathGazeID,Config.potionInfVisExhaustID,Config.potionSunScornedID,Config.potionUnHungerID)
  setMaxStackSize(1)
  setMaxDamage(LibFeatures.CLEANSING_TALISMAN_USES)
  setUnlocalizedName(LibItemNames.CLEANSING_TALISMAN)

  def isEnabled(stack:ItemStack):Boolean ={
        return ItemNBT.getItemStackTag(stack).getBoolean(TAG_ENABLED);
    }

  def  flipEnabled(stack:ItemStack)= {
    ItemNBT.getItemStackTag(stack).setBoolean( TAG_ENABLED, !isEnabled(stack));
    }


  override def onItemRightClick(itemStackIn: ItemStack, worldIn: World, playerIn: EntityPlayer): ItemStack =
    {
      if(playerIn.isSneaking)
        {
          flipEnabled(itemStackIn)
          worldIn.playSoundAtEntity(playerIn,"random.orb",0.3f,0.1f)
        }
      return itemStackIn
    }


  override def addInformation(stack: ItemStack, playerIn: EntityPlayer, tooltip: util.List[_], advanced: Boolean): Unit =
    {
      super.addInformation(stack, playerIn, tooltip, advanced)
      if(isEnabled(stack))
        {
          tooltip.asInstanceOf[java.util.List[String]].add(lang.translate("cleansingTalisman.enabled"))
        }
      else
        tooltip.asInstanceOf[java.util.List[String]].add(lang.translate("cleansingTalisman.disabled"))
    }

  override def hasEffect(stack: ItemStack): Boolean =
    {
      isEnabled(stack)
    }

  override def onWornTick(itemstack: ItemStack, player: EntityLivingBase): Unit =
  {
    var world=player.worldObj
    if(isEnabled(itemstack) && !world.isRemote)
      {
        if(player.ticksExisted%20==0)
          {
            if(player.isInstanceOf[EntityPlayer])
              {
                var damage=1

                val potions=player.getActivePotionEffects.asInstanceOf[java.util.Collection[PotionEffect]]
                if(player.isBurning)
                  {
                    player.extinguish()
                    itemstack.damageItem(damage,player)
                    world.playSoundAtEntity(player,"thaumcraft:wand",0.3f,0.1f)
                    return
                  }
                else
                  {

                    for(potion:PotionEffect <- potions)
                    {
                      var id=potion.getPotionID
                      var badEffect:Boolean=ReflectionHelper.getPrivateValue(classOf[Potion],Potion.potionTypes(id),"isBadEffect","field_76418_K","K")
                      if(Potion.potionTypes(id).isInstanceOf[PotionWarpWard])
                        badEffect=false
                      if(badEffect)
                        {
                          player.removePotionEffect(id)
                          if(WARP_POTIONS contains id)
                            {
                              damage=10
                            }
                            itemstack.damageItem(damage,player)
                          world.playSoundAtEntity(player,"thaumcraft:wand",0.3f,0.1f)
                          return
                        }

                    }
                  }
              }
          }
      }
  }

  override def onUnequipped(itemstack: ItemStack, player: EntityLivingBase): Unit = {}

  override def onEquipped(itemstack: ItemStack, player: EntityLivingBase): Unit = {}
}
