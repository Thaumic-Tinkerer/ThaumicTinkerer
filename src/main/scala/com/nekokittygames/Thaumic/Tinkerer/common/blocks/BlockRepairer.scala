package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileRepairer
import net.minecraft.block.BlockPistonBase
import net.minecraft.block.material.Material
import net.minecraft.block.properties.{PropertyBool, PropertyDirection}
import net.minecraft.block.state.{BlockState, IBlockState}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{BlockPos, EnumFacing, EnumWorldBlockLayer}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
 * Created by fiona on 21/05/2015.
 */
object BlockRepairer extends {
  val FACING:PropertyDirection=PropertyDirection.create("facing")
  val ACTIVE:PropertyBool=PropertyBool.create("active")
} with ModBlock(Material.iron) with ModBlockContainer {
  setHardness(5F)
  setResistance(10F)
  setUnlocalizedName(LibNames.REPAIRER)
  setDefaultState(getBlockState.getBaseState.withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE,false))

  @SideOnly(Side.CLIENT) override def getBlockLayer: EnumWorldBlockLayer = {
    return EnumWorldBlockLayer.TRANSLUCENT
  }
  override def getStateFromMeta(meta: Int): IBlockState = this.getDefaultState.withProperty(FACING,BlockPistonBase.getFacing(meta))

  override def getMetaFromState(state: IBlockState): Int = {
    val b0: Byte = 0
    var i: Int = b0 | state.getValue(FACING).asInstanceOf[EnumFacing].getIndex

    i
  }

  override def createBlockState(): BlockState = new BlockState(this,FACING, ACTIVE)

  override def getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState = {
    super.getActualState(state, worldIn, pos).withProperty(ACTIVE,true)
  }

  override def onBlockPlaced(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState = {
    super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING,BlockPistonBase.getFacingFromEntity(worldIn,pos,placer))

  }

  override def getTileClass: Class[_ <: TileEntity] = classOf[TileRepairer]
}
