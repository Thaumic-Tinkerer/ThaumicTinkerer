package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockFunnel._
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileSummon
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity

/**
  * Created by katsw on 25/11/2015.
  */
object BlockSummon extends ModBlock(Material.rock) with  ModBlockContainer {

  setHardness(3.0F)
  setResistance(8.0f)
  setStepSound(Block.soundTypeStone)
  setBlockBounds(0f, 0f, 0f, 1f, 1f / 8f, 1f)
  setUnlocalizedName(LibNames.SUMMON_BLOCK)
  override def getTileClass: Class[_ <: TileEntity] = classOf[TileSummon]


}
