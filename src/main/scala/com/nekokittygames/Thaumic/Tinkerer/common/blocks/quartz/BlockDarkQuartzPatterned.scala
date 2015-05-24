package com.nekokittygames.Thaumic.Tinkerer.common.blocks.quartz

import java.util
import java.util.UUID

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.ModBlock
import com.nekokittygames.Thaumic.Tinkerer.common.core.enums.EnumQuartzType
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import com.nekokittygames.Thaumic.api.IMetaBlockName
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.{BlockState, IBlockState}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.EnumFacing.Axis
import net.minecraft.util.{BlockPos, ChatComponentText, EnumFacing, MovingObjectPosition}
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import thaumcraft.api.aspects.Aspect

/**
 * Created by Katrina on 18/05/2015.
 */
object BlockDarkQuartzPatterned extends {
  val VARIANT: PropertyEnum=PropertyEnum.create("variant",classOf[EnumQuartzType])
  val AXIS: PropertyEnum=PropertyEnum.create("axis",classOf[Axis])
} with ModBlock(Material.rock) with IMetaBlockName {
  setHardness(0.8f)
  setResistance(10F)
  setUnlocalizedName(LibNames.DARK_QUARTZ_PATTERNED)

  this.setDefaultState(this.getBlockState.getBaseState.withProperty(VARIANT,EnumQuartzType.CHISEL).withProperty(AXIS,Axis.Y))


  override def onBlockPlaced(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState =
  {
    super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(AXIS,facing.getAxis)
  }

  override def getStateFromMeta(meta: Int): IBlockState =
  {
    val axis = meta % 3;
    val typ = (meta - axis) / 3;
    return this.getDefaultState().withProperty(VARIANT, EnumQuartzType.values()(typ)).withProperty(AXIS, Axis.values()(axis));
  }

  @SideOnly(Side.CLIENT)
  override def getSubBlocks(itemIn: Item, tab: CreativeTabs, list: util.List[_]): Unit =
  {
    super.getSubBlocks(itemIn, tab, list)
    list.asInstanceOf[java.util.List[ItemStack]].add(new ItemStack(this,1,getMetaFromState(this.getDefaultState.withProperty(VARIANT,EnumQuartzType.CHISEL).withProperty(AXIS,Axis.X))))
    list.asInstanceOf[java.util.List[ItemStack]].add(new ItemStack(this,1,getMetaFromState(this.getDefaultState.withProperty(VARIANT,EnumQuartzType.PILLAR).withProperty(AXIS,Axis.X))))
  }

  override def getMetaFromState(state: IBlockState): Int = {
    val baseMeta = (state.getValue(VARIANT)).asInstanceOf[EnumQuartzType].ordinal();
    return baseMeta * 3 + (state.getValue(AXIS)).asInstanceOf[Axis].ordinal();
  }

  override def createBlockState(): BlockState = new BlockState(this,VARIANT,AXIS)


  override def getSpecialName(stack: ItemStack): String =
  {
    val axis = stack.getItemDamage % 3;
    val typ = (stack.getItemDamage - axis) / 3;
    if(typ==1) "pillar" else "chisel"
  }

  override def getPickBlock(target: MovingObjectPosition, world: World, pos: BlockPos): ItemStack =new ItemStack(Item.getItemFromBlock(this),1,this.getMetaFromState(world.getBlockState(pos)))


  override def onBlockClicked(worldIn: World, pos: BlockPos, playerIn: EntityPlayer): Unit =
  {
    if(worldIn.isRemote) {
      val uuid: UUID = new UUID(112L, 112L)

      val blockData=BoundJarNetworkManager.data.networks.get(uuid)
      playerIn.addChatMessage(new ChatComponentText(blockData.getAspects()(0).getName))
      playerIn.addChatMessage(new ChatComponentText(blockData.getAmount(blockData.getAspects()(0)).toString))
    }
    super.onBlockClicked(worldIn, pos, playerIn)
  }
}
