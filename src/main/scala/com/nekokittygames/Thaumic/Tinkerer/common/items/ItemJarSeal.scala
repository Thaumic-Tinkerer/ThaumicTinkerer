package com.nekokittygames.Thaumic.Tinkerer.common.items

import java.util.UUID

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockBoundJar
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.ItemNBT
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibItemNames
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileBoundJar
import net.minecraft.block.state.BlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.{EnumFacing, BlockPos}
import net.minecraft.world.World
import thaumcraft.api.blocks.BlocksTC

/**
 * Created by katsw on 24/05/2015.
 */
object ItemJarSeal extends ModItem {


  setUnlocalizedName(LibItemNames.JARSEAL)


  override def onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockPos, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean =
    {
      if(worldIn.getBlockState(pos).getBlock==BlocksTC.jar)
        {
          worldIn.setBlockState(pos,BlockBoundJar.getDefaultState)

          val id=getNetwork(stack)

          if(id!=null) {
            worldIn.getTileEntity(pos).asInstanceOf[TileBoundJar].network = id
            worldIn.getTileEntity(pos).asInstanceOf[TileBoundJar].markDirty()
            worldIn.markBlockForUpdate(pos)
          }
        }
      else if(worldIn.getBlockState(pos).getBlock==BlockBoundJar)
        {
          val tileEntity=worldIn.getTileEntity(pos).asInstanceOf[TileBoundJar]
          setNetwork(stack,tileEntity.network)
        }
      super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
    }

  def getNetwork(itemStack:ItemStack):UUID=
  {
    if(ItemNBT.getItemStackTag(itemStack).hasKey("HighID"))
      new UUID(ItemNBT.getItemStackTag(itemStack).getLong("HighID"),ItemNBT.getItemStackTag(itemStack).getLong("LowID"))
    else
      null
  }


  def setNetwork(itemStack: ItemStack,uuid:UUID)=
  {
    ItemNBT.getItemStackTag(itemStack).setLong("HighID",uuid.getMostSignificantBits)
    ItemNBT.getItemStackTag(itemStack).setLong("LowID",uuid.getLeastSignificantBits)
  }
}



