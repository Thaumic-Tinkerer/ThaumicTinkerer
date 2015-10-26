package com.nekokittygames.Thaumic.Tinkerer.common.items

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.ItemNBT
import com.nekokittygames.Thaumic.Tinkerer.common.items.ItemCometBoots._
import com.nekokittygames.Thaumic.Tinkerer.common.libs.{LibMisc, LibItemNames}
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.{EnumParticleTypes, Vec3}
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import thaumcraft.api.ThaumcraftMaterials
import thaumcraft.common.Thaumcraft
import thaumcraft.common.config.Config
import thaumcraft.common.items.armor.{Hover, ItemBootsTraveller}

/**
 * Created by katsw on 24/10/2015.
 */
object ItemMeteorBoots extends ItemTXBoots {

  final var IS_SMASHING="IsSmashing"
  final var AIR_TICKS="airTicks"
  final var SMASH_TICKS="smashTicks"

  setUnlocalizedName(LibItemNames.BOOTS_METEOR)
  override def getArmorTexture(stack: ItemStack, entity: Entity, slot: Int, `type`: String): String =
    return LibMisc.MOD_ID + ":textures/models/armor/bootsMeteor.png"


  override def onArmorTick(world: World, player: EntityPlayer, itemStack: ItemStack): Unit =
  {

    if (player.fallDistance > 0.0F) {
      player.fallDistance = 0.0F;
    }
    super.onArmorTick(world, player, itemStack)
  }

  override def initItem(fMLPreInitializationEvent: FMLPreInitializationEvent): Unit =
  {
    super.initItem(fMLPreInitializationEvent)
    MinecraftForge.EVENT_BUS.register(this)
  }

  @SubscribeEvent
  def playerJumps(event:LivingEvent.LivingJumpEvent)=
  {
    if(event.entity.isInstanceOf[EntityPlayer] && event.entity.asInstanceOf[EntityPlayer].inventory.armorItemInSlot(0)!=null && event.entity.asInstanceOf[EntityPlayer].inventory.armorItemInSlot(0).getItem==this)
      {
        if(event.entity.asInstanceOf[EntityPlayer].isSneaking)
          {
            var vector=event.entityLiving.getLook(0.5F)
            val total=Math.abs(vector.zCoord+vector.xCoord)
            val player=event.entity.asInstanceOf[EntityPlayer]
            var jump=0

            if (vector.yCoord < total) {
              vector=new Vec3(vector.xCoord, total,vector.zCoord)
            }
            event.entityLiving.motionY += ((jump+1)*vector.yCoord)/1.5F;
            event.entityLiving.motionZ += (jump+1)*vector.zCoord*4;
            event.entityLiving.motionX += (jump+1)*vector.xCoord*4;
          }
        else {
          event.entityLiving.motionY += 0.2750000059604645D;
        }
      }
  }

  @SubscribeEvent
  def livingTick(event:LivingEvent.LivingUpdateEvent) =
  {
    if(event.entity.isInstanceOf[EntityPlayer]) {
      val player = event.entity.asInstanceOf[EntityPlayer]


      if (player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem == this) {

        val vector=player.getLook(1.0f)
        val item=player.inventory.armorItemInSlot(0)
        if(!ItemNBT.getItemStackTag(item).hasKey(SMASH_TICKS))
          {
            ItemNBT.getItemStackTag(item).setBoolean(IS_SMASHING,false)
            ItemNBT.getItemStackTag(item).setInteger(SMASH_TICKS,0)
            ItemNBT.getItemStackTag(item).setInteger(AIR_TICKS,0)
          }
        var smashing=ItemNBT.getItemStackTag(item).getBoolean(IS_SMASHING)
        var ticks=ItemNBT.getItemStackTag(item).getInteger(SMASH_TICKS)
        var ticksAir=ItemNBT.getItemStackTag(item).getInteger(AIR_TICKS)

        if(player.isSneaking)
          smashing=true
        if(player.onGround || player.isOnLadder)
          {
            var size=0
            if(ticks>5)
              size=1
            if(ticks>10)
              size=2
            if(ticks>15)
              size=3
            smashing=false
            ticks=0
            if(size>0)
              {
                player.worldObj.createExplosion(player,player.posX,player.posY,player.posZ,size,true)
              }
          }
        if(player.capabilities.isFlying)
          {
            smashing=false
            ticks=0
            ticksAir=0
          }
        if(smashing)
          {
            player.worldObj.spawnParticle(EnumParticleTypes.FLAME, (player.posX + Math.random()-0.5F), (player.posY + Math.random()-0.5F), (player.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);
            player.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (player.posX + Math.random()-0.5F), (player.posY + Math.random()-0.5F), (player.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);
            player.worldObj.spawnParticle(EnumParticleTypes.FLAME, (player.posX + Math.random()-0.5F), (player.posY + Math.random()-0.5F), (player.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);
            player.motionY-= 0.1F;
            ticks=ticks+1;
          }
        else {
          var motion = Math.abs(player.motionX) + Math.abs(player.motionZ) + Math.abs(0.5*player.motionY)
          if(!player.isWet && motion>0.1F)
            {
              player.worldObj.spawnParticle(EnumParticleTypes.FLAME, (player.posX + Math.random()-0.5F), (player.getEntityBoundingBox.minY + 0.25F + ((Math.random()-0.5)*0.25F)), (player.posZ + Math.random()-0.5F), 0.0D, 0.025D, 0.0D);
              player.worldObj.spawnParticle(EnumParticleTypes.FLAME, (player.posX + Math.random()-0.5F), (player.getEntityBoundingBox.minY + 0.25F + ((Math.random()-0.5)*0.25F)), (player.posZ + Math.random()-0.5F), 0.0D, 0.025D, 0.0D);
            }
        }
        ItemNBT.getItemStackTag(item).setBoolean(IS_SMASHING,smashing)
        ItemNBT.getItemStackTag(item).setInteger(SMASH_TICKS,ticks)
        ItemNBT.getItemStackTag(item).setInteger(AIR_TICKS,ticksAir)

      }
    }
  }
}

