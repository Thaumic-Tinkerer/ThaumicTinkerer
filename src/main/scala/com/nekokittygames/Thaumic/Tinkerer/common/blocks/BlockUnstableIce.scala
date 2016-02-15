package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import java.util.Random

import net.minecraft.block.BlockIce
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.util.BlockPos
import net.minecraft.world.World

/**
 * Created by katsw on 30/10/2015.
 */
object BlockUnstableIce extends BlockIce {

setUnlocalizedName("unstableIce")
  setCreativeTab(null)

  override def getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item = Item.getItemFromBlock(Blocks.ice)

  override def updateTick(worldIn: World, pos: BlockPos, state: IBlockState, rand: Random): Unit =
    {
      if(worldIn.provider.doesWaterVaporize())
        worldIn.setBlockToAir(pos)
      worldIn.setBlockState(pos,Blocks.water.getDefaultState)
    }
}
