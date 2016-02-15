package com.nekokittygames.Thaumic.Tinkerer.common.items

import java.util
import java.util.UUID

import codechicken.lib.colour.{ColourRGBA, Colour}
import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockBoundJar
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.ItemNBT
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibItemNames
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileBoundJar
import net.minecraft.block.state.BlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{EnumDyeColor, Item, ItemStack}
import net.minecraft.util.{StatCollector, EnumFacing, BlockPos}
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.lwjgl.util.Color
import thaumcraft.api.blocks.BlocksTC

/**
 * Created by katsw on 24/05/2015.
 */
object ItemJarSeal extends ModItem {


  setUnlocalizedName(LibItemNames.JARSEAL)
  setHasSubtypes(true)


  override def onItemUse(stack: ItemStack, playerIn: EntityPlayer, worldIn: World, pos: BlockPos, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean =
    {
      if(worldIn.getBlockState(pos).getBlock==BlocksTC.jar)
        {
          worldIn.setBlockState(pos,BlockBoundJar.getDefaultState)

          val id=getNetwork(stack)
          val tileEntity:TileBoundJar=worldIn.getTileEntity(pos).asInstanceOf[TileBoundJar]
          tileEntity.jarColor=stack.getItemDamage

          if(id!=null) {

            tileEntity.network = id
            val aList=BoundJarNetworkManager.getAspect(id)
            if(aList.size()>0) {
              tileEntity.aspect = aList.getAspects()(0)
              tileEntity.amount = aList.getAmount(tileEntity.aspect)
            }
            tileEntity.markDirty()

          }
          worldIn.markBlockForUpdate(pos)
          playerIn.inventory.getCurrentItem.stackSize=playerIn.inventory.getCurrentItem.stackSize-1
          if(playerIn.inventory.getCurrentItem.stackSize<=0)
            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,null)
        }
      else if(worldIn.getBlockState(pos).getBlock==BlockBoundJar)
        {
          val tileEntity=worldIn.getTileEntity(pos).asInstanceOf[TileBoundJar]
          var newStack=new ItemStack(this,1,tileEntity.jarColor)
          setNetwork(newStack,tileEntity.network)
          playerIn.inventory.getCurrentItem.stackSize=playerIn.inventory.getCurrentItem.stackSize-1
          if(playerIn.inventory.getCurrentItem.stackSize<=0)
            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,null)
          if(!playerIn.inventory.addItemStackToInventory(newStack))
            playerIn.dropItem(newStack,false,false)

        }
      super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ)
    }


  override def addInformation(stack: ItemStack, playerIn: EntityPlayer, tooltip: util.List[String], advanced: Boolean): Unit =
    {
      super.addInformation(stack, playerIn, tooltip.asInstanceOf[java.util.List[String]], advanced)
      val network:String=getNetwork(stack)
      if(network!=null)
        tooltip.asInstanceOf[java.util.List[String]].add(lang.format("boundJar.networkInfo",network.toString))
      else
        tooltip.asInstanceOf[java.util.List[String]].add(lang.translate("boundJar.noNetworkInfo"))

    }


  @SideOnly(Side.CLIENT)
  override def getColorFromItemStack(stack: ItemStack, renderPass: Int): Int =
  {
    val damage=stack.getItemDamage
    val mapColor=EnumDyeColor.byDyeDamage(damage).getMapColor

    val colour:Colour=new ColourRGBA(mapColor.colorValue)
    if(renderPass==0)
      {

        return colour.rgba()
      }
    else
      {

        return if(getNetwork(stack)==null) colour.rgba() else colour.invert().rgba()
      }
  }


  @SideOnly(Side.CLIENT)
  override def getSubItems(itemIn: Item, tab: CreativeTabs, subItems: util.List[ItemStack]): Unit =
    {
      super.getSubItems(itemIn, tab, subItems.asInstanceOf[java.util.List[ItemStack]])
      for(i <- 1 to 16)
        {
          subItems.asInstanceOf[java.util.List[ItemStack]].add(new ItemStack(this,1,i))
        }
    }

  def getNetwork(itemStack:ItemStack):String=
  {
    if(itemStack.hasDisplayName)
      itemStack.getDisplayName
    else if(ItemNBT.getItemStackTag(itemStack).hasKey("network"))
      ItemNBT.getItemStackTag(itemStack).getString("network")
    else
      null
  }


  def setNetwork(itemStack: ItemStack,uuid:String)=
  {
    ItemNBT.getItemStackTag(itemStack).setString("network",uuid)
  }
}



