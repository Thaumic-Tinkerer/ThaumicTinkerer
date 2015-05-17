package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity

/**
 * Created by Katrina on 17/05/2015.
 */
class BlockModContainer(mat:Material) extends BlockMod(mat){


  def getTileClass:Class[_ <: TileEntity]=null
}
