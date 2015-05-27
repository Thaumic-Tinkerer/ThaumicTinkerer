package com.nekokittygames.Thaumic.Tinkerer.common.items

import codechicken.lib.util.LangProxy
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTCreativeTab
import net.minecraft.item.Item

/**
 * Created by Katrina on 22/05/2015.
 */
class ModItem extends Item{



  final var lang: LangProxy = new LangProxy("ttitems")
  if(registerInCreative)
    setCreativeTab(TTCreativeTab)
  def registerInCreative:Boolean =true
}
