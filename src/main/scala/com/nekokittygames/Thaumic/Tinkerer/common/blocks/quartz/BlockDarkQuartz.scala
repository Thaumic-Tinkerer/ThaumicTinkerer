package com.nekokittygames.Thaumic.Tinkerer.common.blocks.quartz

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockMod
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import net.minecraft.block.material.Material

/**
 * Created by Katrina on 17/05/2015.
 */
object BlockDarkQuartz extends BlockMod(Material.rock){
  setHardness(0.8f)
  setResistance(10F)
  setUnlocalizedName(LibNames.DARK_QUARTZ)

}
