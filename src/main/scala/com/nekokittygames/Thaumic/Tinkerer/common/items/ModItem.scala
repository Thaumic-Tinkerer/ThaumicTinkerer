package com.nekokittygames.Thaumic.Tinkerer.common.items

import codechicken.lib.util.LangProxy
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTCreativeTab
import net.minecraft.item.Item
import net.minecraftforge.fml.common.event.{FMLPreInitializationEvent, FMLInitializationEvent}

/**
 * Created by Katrina on 22/05/2015.
 */
trait ModItem extends Item{


  def initItem(fMLPreInitializationEvent: FMLPreInitializationEvent)=
  {

  }

  final var lang: LangProxy = new LangProxy("ttitems")
  if(registerInCreative)
    setCreativeTab(TTCreativeTab)
  def registerInCreative:Boolean =true
}
