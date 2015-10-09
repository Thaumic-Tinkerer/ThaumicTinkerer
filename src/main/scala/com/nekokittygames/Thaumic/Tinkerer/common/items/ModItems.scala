package com.nekokittygames.Thaumic.Tinkerer.common.items

import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import com.nekokittygames.Thaumic.Tinkerer.common.items.baubles.{ItemFoodTalisman, ItemEnderDisruption, ItemStabilizerBelt}
import com.nekokittygames.Thaumic.Tinkerer.common.items.quartz.ItemDarkQuartz
import com.nekokittygames.Thaumic.Tinkerer.common.libs.{LibMisc, LibItemNames}
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by Katrina on 22/05/2015.
 */
object ModItems {


  def registerItems(fMLPreInitializationEvent: FMLPreInitializationEvent) = {
    def registerItem(item: ModItem, name: String) = {
      GameRegistry.registerItem(item, name)
      item.initItem(fMLPreInitializationEvent)
    }

    registerItem(ItemDarkQuartz, LibItemNames.DARK_QUARTZ)
    registerItem(ItemJarSeal,LibItemNames.JARSEAL)
    registerItem(ItemStabilizerBelt,LibItemNames.STABILIZERBELT)
    registerItem(ItemEnderDisruption,LibItemNames.ENDERDISRUPTION)
    registerItem(ItemFoodTalisman,LibItemNames.FOOD_TALISMAN)
    registerItem(ItemMobAspect,LibItemNames.MOB_ASPECT)
  }


  def registerInventoryItems() = {
    ThaumicTinkerer.proxy.registerInventoryItem(ItemDarkQuartz, LibItemNames.DARK_QUARTZ)

    for(i <- 0 to 16)
      {
        ThaumicTinkerer.proxy.registerInventoryItem(ItemJarSeal, LibItemNames.JARSEAL,i)
      }
    ThaumicTinkerer.proxy.registerInventoryItem(ItemStabilizerBelt,LibItemNames.STABILIZERBELT)
    ThaumicTinkerer.proxy.registerInventoryItem(ItemEnderDisruption,LibItemNames.ENDERDISRUPTION)
    ThaumicTinkerer.proxy.registerInventoryItem(ItemFoodTalisman,LibItemNames.FOOD_TALISMAN)
    ThaumicTinkerer.proxy.registerInventoryItem(ItemMobAspect,LibItemNames.MOB_ASPECT)
    ThaumicTinkerer.proxy.registerItemBakery(ItemMobAspect,Array[String](LibMisc.MOD_ID+":"+LibItemNames.MOB_ASPECT,LibMisc.MOD_ID+":"+LibItemNames.MOB_ASPECT+"Condensed"))
  }
}
