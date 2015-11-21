package com.nekokittygames.Thaumic.Tinkerer.common.items

import com.google.common.collect.{HashMultimap, Multimap}
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.ItemNBT
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibItemNames
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.{EntityLivingBase, SharedMonsterAttributes}
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemStack, Item, ItemSword}
import net.minecraft.util.DamageSource
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.event.entity.living.{LivingAttackEvent, LivingDropsEvent}
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thaumcraft.api.aspects.{Aspect, AspectHelper}
import thaumcraft.api.items.IRepairable
import thaumcraft.api.research.{ScanningManager, ScanEntity}
import thaumcraft.api.{ThaumcraftMaterials, ThaumcraftApi}

/**
 * Created by katsw on 26/10/2015.
 */
object ItemBloodSword extends ItemSword(EnumHelper.addToolMaterial("TT_BLOOD",0,950,0,0,ThaumcraftMaterials.TOOLMAT_THAUMIUM.getEnchantability)) with ModItem with IRepairable{

  final var DAMAGE:Int = 10;
  final var ACTIVATED="activated"
  var handleNext=0
  setUnlocalizedName(LibItemNames.BLOOD_SWORD)
  override def initItem(fMLPreInitializationEvent: FMLPreInitializationEvent): Unit =
  {
    MinecraftForge.EVENT_BUS.register(this)
  }


  override def onItemRightClick(itemStackIn: ItemStack, worldIn: World, playerIn: EntityPlayer): ItemStack =
    {
      if(playerIn.isSneaking)
        {
          ItemNBT.getItemStackTag(itemStackIn).setBoolean(ACTIVATED,! ItemNBT.getItemStackTag(itemStackIn).getBoolean(ACTIVATED))
        }
      return itemStackIn
    }

  override def getItemAttributeModifiers: Multimap[_,_] =
  {
    var multimap = super.getItemAttributeModifiers()
    multimap.asInstanceOf[com.google.common.collect.HashMultimap[String,AttributeModifier]].put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(Item.itemModifierUUID, "Weapon modifier", DAMAGE, 0));
    multimap.asInstanceOf[com.google.common.collect.HashMultimap[String,AttributeModifier]].put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(Item.itemModifierUUID, "Weapon modifier", 0.25, 1));
    return multimap;
  }

  @SubscribeEvent
  def onDrops(event:LivingDropsEvent) =
  {
    if(event.source.damageType.equalsIgnoreCase("player"))
      {
        val player=event.source.getEntity.asInstanceOf[EntityPlayer]
        val stack=player.getCurrentEquippedItem
        if(stack!=null && stack.getItem == this && ItemNBT.getItemStackTag(stack).hasKey(ACTIVATED) && ItemNBT.getItemStackTag(stack).getBoolean(ACTIVATED))
          {
            var aspects=AspectHelper.getEntityAspects(event.entity)
            for(aspect:Aspect <- aspects.getAspects) {
              var item=new ItemStack(ItemMobAspect,aspects.getAmount(aspect))
              ItemMobAspect.setAspect(item,aspect)
              event.drops.add(new EntityItem(player.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ,item))
            }
          }
      }
  }

  @SubscribeEvent
  def onDamage(event:LivingAttackEvent):Void=
  {
    if(event.entity.worldObj.isRemote)
      return null
    var handle=handleNext==0
    if(!handle)
      handleNext=handleNext-1
    if(event.entityLiving.isInstanceOf[EntityPlayer] && handle) {
      val player = event.entityLiving.asInstanceOf[EntityPlayer]
      val itemInUse = player.inventory.getCurrentItem
      if (itemInUse != null && itemInUse.getItem == null) {
        event.setCanceled(true)
        handleNext = 3
        player.attackEntityFrom(DamageSource.magic, 3)
      }
    }

        if(handle)
          {
            val source=event.source.getSourceOfDamage
            if(source!=null && source.isInstanceOf[EntityLivingBase])
              {
                val attacker=source.asInstanceOf[EntityLivingBase]
                val item=attacker.getHeldItem
                if(item!=null && item.getItem==this)
                  attacker.attackEntityFrom(DamageSource.magic,2)
              }
          }
    null
  }
}
