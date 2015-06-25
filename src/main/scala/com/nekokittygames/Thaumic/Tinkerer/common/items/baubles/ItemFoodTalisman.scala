package com.nekokittygames.Thaumic.Tinkerer.common.items.baubles

import java.util

import baubles.api.BaubleType
import com.mojang.authlib.GameProfile
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.ItemNBT
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibItemNames
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemFood, ItemStack}
import net.minecraft.world.WorldServer
import net.minecraftforge.common.util.FakePlayerFactory
import thaumcraft.api.items.ItemsTC

import scala.collection.mutable

/**
 * Created by Katrina on 24/06/2015.
 */
object ItemFoodTalisman extends ItemBaubles(BaubleType.AMULET) {
  setMaxStackSize(1)

  var foodBlacklist: mutable.MutableList[String] = new mutable.MutableList[String]
  var foodCache: mutable.Map[String, Boolean] = mutable.Map[String, Boolean]()
  foodBlacklist+=ItemsTC.brain.getUnlocalizedName
  setUnlocalizedName(LibItemNames.FOOD_TALISMAN)




  override def onWornTick(itemstack: ItemStack, player: EntityLivingBase): Unit =
  {

  }


  override def addInformation(stack: ItemStack, playerIn: EntityPlayer, tooltip: util.List[_], advanced: Boolean): Unit = {
    super.addInformation(stack, playerIn, tooltip, advanced)
    val saturation = getSaturation(stack)
    val food = getFood(stack)
  tooltip.asInstanceOf[java.util.List[String]].add(lang.format("foodTalisman.tooltip",food.toString,saturation.toString))
  }

  def getSaturation(itemStack:ItemStack):Float=
  {
    if(ItemNBT.getItemStackTag(itemStack).hasKey("saturation"))
      ItemNBT.getItemStackTag(itemStack).getFloat("saturation")
    else
      0
  }

  def getFood(itemStack:ItemStack):Float=
  {
    if(ItemNBT.getItemStackTag(itemStack).hasKey("food"))
      ItemNBT.getItemStackTag(itemStack).getFloat("food")
    else
      0
  }


  def setSaturation(itemStack:ItemStack,saturation:Float)=
  {
    ItemNBT.getItemStackTag(itemStack).setFloat("saturation",saturation)
  }

  def setFood(itemStack:ItemStack,food:Float)=
  {
    ItemNBT.getItemStackTag(itemStack).setFloat("food",food)
  }

  def isEdible(itemStack: ItemStack, player: EntityPlayer): Boolean = {
    val foodName: String = itemStack.getUnlocalizedName
    if (foodCache.contains(foodName.toLowerCase))
      return foodCache.get(foodName.toLowerCase).get
    for (black: String <- foodBlacklist) {
      if (black.equalsIgnoreCase(foodName)) {
        foodCache.put(foodName.toLowerCase, false)
        return false
      }
    }
    if (!itemStack.getItem.isInstanceOf[ItemFood]) {
      foodCache.put(foodName.toLowerCase, false)
      return false
    }

    val food: ItemFood = itemStack.getItem.asInstanceOf[ItemFood]
    try {
      for (i <- 0 until 25) {
        val fakePlayer: EntityPlayer = FakePlayerFactory.get(player.worldObj.asInstanceOf[WorldServer], new GameProfile(null, "foodTabletPLayer"))

        fakePlayer.setPosition(0.0f, 999.0f, 0.0f)
        food.onItemUseFinish(itemStack.copy(), fakePlayer.worldObj, fakePlayer)
        if (fakePlayer.getActivePotionEffects.size() > 0) {
          foodCache.put(foodName.toLowerCase, false)
          return false
        }

      }
    }
    catch
    {
      case e:Exception => {
        foodCache.put(foodName.toLowerCase, false)
        return false
      }
    }
    foodCache.put(foodName.toLowerCase, true)
    true
  }

  override def onEquipped(itemstack: ItemStack, player: EntityLivingBase): Unit = {}

  override def onUnequipped(itemstack: ItemStack, player: EntityLivingBase): Unit = {}
}