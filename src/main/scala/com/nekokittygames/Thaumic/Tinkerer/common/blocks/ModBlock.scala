package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTCreativeTab
import net.minecraft.block.Block
import net.minecraft.block.material.Material

/**
 * Created by Katrina on 17/05/2015.
 */
class ModBlock(mat: Material) extends Block(mat) {

  if(registerInCreative)
    setCreativeTab(TTCreativeTab)
  def registerInCreative:Boolean =true
}
