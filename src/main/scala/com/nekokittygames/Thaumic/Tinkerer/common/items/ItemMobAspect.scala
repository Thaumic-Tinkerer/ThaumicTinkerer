package com.nekokittygames.Thaumic.Tinkerer.common.items

import java.util

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.ItemNBT
import com.nekokittygames.Thaumic.Tinkerer.common.libs.{LibMisc, LibNames, LibItemNames}
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import net.minecraftforge.oredict.OreDictionary
import thaumcraft.api.ThaumcraftApiHelper
import thaumcraft.api.aspects.Aspect

/**
 * Created by katsw on 09/10/2015.
 */
object ItemMobAspect extends ModItem{

  final var ASPECT_TAG="aspect"
  final var INFUSED_TAG="infused"
  final var CONDENSED_TAG="condensed"

  setUnlocalizedName(LibItemNames.MOB_ASPECT)
  setHasSubtypes(true)


  override def addInformation(stack: ItemStack, playerIn: EntityPlayer, tooltip: util.List[String], advanced: Boolean): Unit =
    {
      super.addInformation(stack, playerIn, tooltip.asInstanceOf[java.util.List[String]], advanced)
      val aspect:Aspect=getAspect(stack)
      if(aspect!=null)
        tooltip.asInstanceOf[java.util.List[String]].add(lang.format("mobAspects.aspect.info",aspect.getName))
      if(isCondensed(stack))
        tooltip.asInstanceOf[java.util.List[String]].add(lang.translate("mobaspects.condensed"))
      if(isInfused(stack))
        tooltip.asInstanceOf[java.util.List[String]].add(lang.translate("mobaspects.infused"))
    }


  def getAspect(itemStack: ItemStack): Aspect =
  {
    Aspect.getAspect(ItemNBT.getItemStackTag(itemStack).getString(ASPECT_TAG))
  }
  def setAspect(itemStack: ItemStack,aspect:Aspect) =
  {
      ItemNBT.getItemStackTag(itemStack).setString(ASPECT_TAG,aspect.getTag)
  }

  def isInfused(itemStack: ItemStack):Boolean=
  {
      ItemNBT.getItemStackTag(itemStack).getBoolean(INFUSED_TAG)
  }

  def setInfused(itemStack: ItemStack,infused: Boolean): Unit =
  {
    ItemNBT.getItemStackTag(itemStack).setBoolean(INFUSED_TAG,infused)
  }

  def isCondensed(itemStack: ItemStack):Boolean=
  {
    ItemNBT.getItemStackTag(itemStack).getBoolean(CONDENSED_TAG)
  }

  def setCondensed(itemStack: ItemStack,condensed: Boolean): Unit =
  {
    ItemNBT.getItemStackTag(itemStack).setBoolean(CONDENSED_TAG,condensed)
  }

  override def getSubItems(itemIn: Item, tab: CreativeTabs, subItems: util.List[ItemStack]): Unit =
    {
      super.getSubItems(itemIn, tab, subItems)
      Aspect.getPrimalAspects.toArray.foreach
      {
        aspect=>
          val stack:ItemStack=new ItemStack(ItemMobAspect)
          setAspect(stack,aspect.asInstanceOf[Aspect])
          subItems.asInstanceOf[java.util.List[ItemStack]].add(stack)
      }
      Aspect.getCompoundAspects.toArray.foreach
      {
        aspect=>
          val stack:ItemStack=new ItemStack(ItemMobAspect)
          setAspect(stack,aspect.asInstanceOf[Aspect])
          subItems.asInstanceOf[java.util.List[ItemStack]].add(stack)
      }
    }

  override def getColorFromItemStack(stack: ItemStack, renderPass: Int): Int =
    {
      val aspect:Aspect=getAspect(stack)
      if(aspect!=null)
        aspect.getColor
      else
        0
    }

  override def getUnlocalizedName(stack: ItemStack): String =
    {
      if(isInfused(stack))
        return super.getUnlocalizedName(stack)+".infused"
      if(isCondensed(stack))
        return super.getUnlocalizedName(stack)+".condensed"
      super.getUnlocalizedName(stack)
    }

  override def hasEffect(stack: ItemStack): Boolean =
    {
      if(isInfused(stack))
        true
      else
        super.hasEffect(stack)

    }


  def doesMobAspectMatchForInfusion(stack0:ItemStack,aspect:Aspect):Boolean= {
    if (stack0.isInstanceOf[ItemStack]) {
      if(stack0.getItem!=ItemMobAspect)
        false
      else
        {
          if(ItemMobAspect.getAspect(stack0).getTag.equalsIgnoreCase(aspect.getTag) && ItemMobAspect.isCondensed(stack0))
            true
          else
            false
        }
    }
    else
      false

  }

  @SideOnly(Side.CLIENT)
  override def getModel(stack: ItemStack, player: EntityPlayer, useRemaining: Int): ModelResourceLocation =
    {
      if(isCondensed(stack) || isInfused(stack))
        {
         new ModelResourceLocation(LibMisc.MOD_ID+":"+LibItemNames.MOB_ASPECT+"Condensed","inventory")
        }
      else
        new ModelResourceLocation(LibMisc.MOD_ID+":"+LibItemNames.MOB_ASPECT,"inventory")
    }
}
