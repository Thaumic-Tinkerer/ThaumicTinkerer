package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import com.nekokittygames.Thaumic.Tinkerer.common.blocks.quartz.BlockDarkQuartz
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import net.minecraft.block.{ITileEntityProvider, Block}
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by Katrina on 17/05/2015.
 */
object ModBlocks {
  val Blocks=scala.collection.mutable.Map[Block,String]()


  def registerBlocks()=
  {
    def registerBlock(block:Block,name:String)=
    {
      GameRegistry.registerBlock(block,name)
      block match
      {
        case s:BlockModContainer=>GameRegistry.registerTileEntity(s.getTileClass,name)
      }
      Blocks(block)=name
    }
    registerBlock(BlockDarkQuartz,LibNames.DARK_QUARTZ)
  }




  def registerBlocksInventory(): Unit =
  {
    Blocks.foreach { block => ThaumicTinkerer.proxy.registerInventoryBlock(block._1,block._2)}
  }

}
