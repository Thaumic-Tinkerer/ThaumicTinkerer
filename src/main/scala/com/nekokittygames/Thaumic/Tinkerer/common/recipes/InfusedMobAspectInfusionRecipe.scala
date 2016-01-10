package com.nekokittygames.Thaumic.Tinkerer.common.recipes

import java.util

import com.nekokittygames.Thaumic.Tinkerer.common.items.ItemMobAspect
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import thaumcraft.api.ThaumcraftApiHelper
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.api.crafting.InfusionRecipe
import thaumcraft.api.research.ResearchHelper

/**
 * Created by katsw on 09/10/2015.
 */
class InfusedMobAspectInfusionRecipe(research:Array[String], output:Any, inst:Int, aspects2:AspectList, input:Any, recipe:Array[Object]) extends InfusionRecipe(research,output,inst,aspects2,input,recipe){


  override def matches(input: util.ArrayList[ItemStack], central: ItemStack, world: World, player: EntityPlayer): Boolean = {
    val mainAspect: Aspect = ItemMobAspect.getAspect(central)
    if (this.getRecipeInput() == null) {
      return false;
    } else if (this.research != null && this.research.asInstanceOf[Array[String]](0).length > 0 && !ResearchHelper.isResearchComplete(player.getDisplayNameString, this.research)) {
      return false;
    } else {
      var i2: ItemStack = central.copy();
      if (this.getRecipeInput().isInstanceOf[ItemStack] && this.getRecipeInput().asInstanceOf[ItemStack].getItemDamage() == 32767) {
        i2.setItemDamage(32767);
      }

      if (!ItemMobAspect.doesMobAspectMatchForInfusion(i2, mainAspect)) {
        return false;
      } else {

        var broke = false
        for (i <- input.toArray) {
          if (!broke) {
            broke= !(ItemMobAspect.doesMobAspectMatchForInfusion(i.asInstanceOf[ItemStack],mainAspect))
          }
        }
        if(broke)
          return false
      }
      return true
    }
  }

  override def getRecipeOutput(player: EntityPlayer, input: ItemStack, comps: util.ArrayList[ItemStack]): AnyRef =
    {
      val obj:ItemStack=super.getRecipeOutput(player, input, comps).asInstanceOf[ItemStack]
      ItemMobAspect.setAspect(obj,ItemMobAspect.getAspect(input))
      ItemMobAspect.setCondensed(obj,false)
      ItemMobAspect.setInfused(obj,true)

      obj
    }
}
