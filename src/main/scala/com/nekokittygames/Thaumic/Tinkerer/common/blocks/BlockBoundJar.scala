package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTCreativeTab
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileBoundJar
import net.minecraft.tileentity.TileEntity
import thaumcraft.common.blocks.devices.BlockJar

/**
 * Created by fiona on 23/05/2015.
 */
object BlockBoundJar extends BlockJar with ModBlockContainer{


  setUnlocalizedName(LibNames.BOUNDJAR)

  override def getTileClass: Class[_ <: TileEntity] = classOf[TileBoundJar]
}
