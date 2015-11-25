package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileSummon
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity

/**
  * Created by katsw on 25/11/2015.
  */
object BlockSummon extends ModBlock(Material.rock) with  ModBlockContainer {

  override def getTileClass: Class[_ <: TileEntity] = classOf[TileSummon]


}
