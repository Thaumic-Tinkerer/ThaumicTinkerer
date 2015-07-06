package com.nekokittygames.Thaumic.Tinkerer.common.items.baubles

import baubles.api.{BaubleType, BaublesApi}
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibItemNames
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.living.EnderTeleportEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

import scala.collection.JavaConversions._
/**
 * Created by Katrina on 07/06/2015.
 */
object ItemEnderDisruption extends ItemBaubles(BaubleType.AMULET){
  setUnlocalizedName(LibItemNames.ENDERDISRUPTION)
  setMaxDamage(600)


  override def initItem(fMLPreInitializationEvent: FMLPreInitializationEvent): Unit =
    {
      super.initItem(fMLPreInitializationEvent)
      MinecraftForge.EVENT_BUS.register(this)
    }

  final val RANGE:Int=16;
  override def onWornTick(itemstack: ItemStack, player: EntityLivingBase): Unit = {}

  override def onUnequipped(itemstack: ItemStack, player: EntityLivingBase): Unit = {}

  override def onEquipped(itemstack: ItemStack, player: EntityLivingBase): Unit = {}




  @SubscribeEvent
  def onEnderTeleport(event: EnderTeleportEvent)=
  {


    val players=event.entityLiving.worldObj.getEntitiesWithinAABB(classOf[EntityPlayer],event.entityLiving.getEntityBoundingBox.expand(RANGE,RANGE,RANGE)).toList

    for(player <- players)
      {
        val play=player.asInstanceOf[EntityPlayer]
        val inventory=BaublesApi.getBaubles(play)
        for( i <- 0 to inventory.getSizeInventory)
          {
            val itemStack=inventory.getStackInSlot(i)
            if(itemStack!=null && itemStack.getItem==ItemEnderDisruption && itemStack.getItemDamage<itemStack.getMaxDamage) {
              event.setCanceled(true)
              itemStack.setItemDamage(Math.min(itemStack.getMaxDamage,itemStack.getItemDamage+1))
            }
          }
      }
  }
}
