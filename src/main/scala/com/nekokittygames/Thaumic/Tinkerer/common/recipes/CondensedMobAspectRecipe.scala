package com.nekokittygames.Thaumic.Tinkerer.common.recipes

import com.nekokittygames.Thaumic.Tinkerer.common.items.ItemMobAspect
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.api.crafting.IArcaneRecipe

/**
 * Created by katsw on 09/10/2015.
 */
class CondensedMobAspectRecipe extends IRecipe{
  override def matches(inventoryCrafting: InventoryCrafting, world: World): Boolean =
  {
    val stack=inventoryCrafting.getStackInSlot(0)

    if(stack==null || stack.getItem!=ItemMobAspect || ItemMobAspect.isCondensed(stack)==true || ItemMobAspect.isInfused(stack)==true)
      false
    else {
      val aspect:Aspect=ItemMobAspect.getAspect(stack)
      for (i <- 1 until inventoryCrafting.getSizeInventory) {
        val check = inventoryCrafting.getStackInSlot(i)
        if(check==null || check.getItem!=ItemMobAspect || ItemMobAspect.isCondensed(check)==true || ItemMobAspect.isInfused(check)==true || !ItemMobAspect.getAspect(check).getTag.equalsIgnoreCase(aspect.getTag))
          return false
      }

      true
    }
  }

  override def getRemainingItems(inventoryCrafting: InventoryCrafting): Array[ItemStack] =
  {
    val ret:Array[ItemStack] = new Array[ItemStack](inventoryCrafting.getSizeInventory)
    for (i<-0 until ret.length)
    {
      ret(i) = ForgeHooks.getContainerItem(inventoryCrafting.getStackInSlot(i))
    }
    return ret;
  }

  override def getRecipeOutput: ItemStack = new ItemStack(ItemMobAspect)

  override def getRecipeSize: Int = 9

  override def getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack =
  {
    val stack=inventoryCrafting.getStackInSlot(0).copy()
    ItemMobAspect.setCondensed(stack,true)
    stack
  }



}
