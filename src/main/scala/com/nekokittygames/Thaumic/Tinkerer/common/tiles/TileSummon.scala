package com.nekokittygames.Thaumic.Tinkerer.common.tiles


import net.minecraft.server.gui.IUpdatePlayerListBox
import net.minecraft.tileentity.TileEntity

/**
  * Created by katsw on 25/11/2015.
  */
class TileSummon extends TileEntity with IUpdatePlayerListBox {
  override def update(): Unit = {
    if (worldObj.getTotalWorldTime() % 300 == 0) {
      if(worldObj.isBlockIndirectlyGettingPowered(pos)!=0)
        return;

    }
  }
}
