package com.nekokittygames.Thaumic.Tinkerer.common.integration.oc

import com.nekokittygames.Thaumic.Tinkerer.common.items.oc.ItemThaumometerUpgrade
import li.cil.oc.api.driver.{EnvironmentHost, Item, EnvironmentAware}
import li.cil.oc.api.driver.item.{Slot, HostAware}
import li.cil.oc.api.network.{ManagedEnvironment, Environment}
import net.minecraft.entity.Entity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

/**
  * Created by katsw on 22/11/2015.
  */
class DriverThaumometerUpgrade extends Item with HostAware with EnvironmentAware {
  override def tier(stack: ItemStack): Int = 0

  override def slot(stack: ItemStack): String = Slot.Upgrade

  override def worksWith(stack: ItemStack): Boolean = stack.getItem==ItemThaumometerUpgrade

  override def createEnvironment(stack: ItemStack, host: EnvironmentHost): ManagedEnvironment = host match {
    case entity: Entity =>
      {
        new UpgradeThaumometer(entity)
      }
    case tEntity:TileEntity =>
      {
        new UpgradeThaumometer((tEntity))
      }
    case _ => null
  }

  override def dataTag(stack: ItemStack): NBTTagCompound = new NBTTagCompound

  override def worksWith(stack: ItemStack, host: Class[_ <: EnvironmentHost]): Boolean = stack.getItem==ItemThaumometerUpgrade

  override def providedEnvironment(stack: ItemStack): Class[_ <: Environment] = classOf[UpgradeThaumometer]
}
