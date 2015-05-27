package com.nekokittygames.Thaumic.Tinkerer.common.blocks.quartz

import java.util.UUID

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.ModBlock
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import thaumcraft.api.aspects.Aspect

/**
 * Created by Katrina on 17/05/2015.
 */
object BlockDarkQuartz extends ModBlock(Material.rock) {
  setHardness(0.8f)
  setResistance(10F)
  setUnlocalizedName(LibNames.DARK_QUARTZ_BLOCK)

  override def onBlockClicked(worldIn: World, pos: BlockPos, playerIn: EntityPlayer): Unit =
  {
    if(!worldIn.isRemote) {
      val uuid: String="Poppy"

      BoundJarNetworkManager.getAspect(uuid).add(Aspect.CRYSTAL,64)
      BoundJarNetworkManager.markDirty(uuid)
    }
    super.onBlockClicked(worldIn, pos, playerIn)
  }
}
