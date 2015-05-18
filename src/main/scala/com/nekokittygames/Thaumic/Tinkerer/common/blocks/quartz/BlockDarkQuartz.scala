package com.nekokittygames.Thaumic.Tinkerer.common.blocks.quartz

import java.util

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockMod
import com.nekokittygames.Thaumic.Tinkerer.common.core.enums.EnumQuartzType
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.{IBlockState, BlockState}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{ItemStack, Item}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
 * Created by Katrina on 17/05/2015.
 */
object BlockDarkQuartz extends BlockMod(Material.rock) {
  setHardness(0.8f)
  setResistance(10F)
  setUnlocalizedName(LibNames.DARK_QUARTZ_BLOCK)
}
