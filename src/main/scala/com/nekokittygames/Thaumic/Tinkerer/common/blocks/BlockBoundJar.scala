package com.nekokittygames.Thaumic.Tinkerer.common.blocks

import java.util.UUID

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTCreativeTab
import com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager
import com.nekokittygames.Thaumic.Tinkerer.common.items.ItemJarSeal
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibNames
import com.nekokittygames.Thaumic.Tinkerer.common.tiles.TileBoundJar
import net.minecraft.block.Block
import net.minecraft.block.properties.PropertyEnum
import net.minecraft.block.state.{BlockState, IBlockState}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{EnumDyeColor, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{ChatComponentText, EnumFacing, BlockPos}
import net.minecraft.world.{IBlockAccess, World}
import thaumcraft.api.aspects.{AspectList, Aspect, IEssentiaContainerItem}
import thaumcraft.api.blocks.BlocksTC
import thaumcraft.api.items.{ItemsTC, ItemGenericEssentiaContainer}
import thaumcraft.common.Thaumcraft
import thaumcraft.common.blocks.devices.{BlockJarItem, BlockJar}
import thaumcraft.common.config.ConfigItems
import thaumcraft.common.lib.CreativeTabThaumcraft
import thaumcraft.common.tiles.essentia.TileJarFillable

/**
 * Created by Katrina on 23/05/2015.
 */
object BlockBoundJar extends {
  val COLOUR:PropertyEnum[EnumDyeColor]=PropertyEnum.create("colour",classOf[EnumDyeColor])
} with BlockJar  with ModBlockContainer{

  setUnlocalizedName(LibNames.BOUNDJAR)
  setDefaultState(getBlockState.getBaseState.withProperty(COLOUR,EnumDyeColor.WHITE))
  this.setCreativeTab(null)
  override def getTileClass: Class[_ <: TileEntity] = classOf[TileBoundJar]

  override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, side: EnumFacing, fx: Float, fy: Float, fz: Float): Boolean =
  {
    if(!world.isRemote)
      {
        world.markBlockForUpdate(pos)
      }

    val tileEntity:TileEntity=world.getTileEntity(pos)
    if(tileEntity==null || !(tileEntity.isInstanceOf[TileBoundJar]))
      return false;

    val jar=tileEntity.asInstanceOf[TileBoundJar]
    val heldItem=player.getHeldItem
    if(heldItem==null || !(heldItem.getItem.isInstanceOf[ItemGenericEssentiaContainer]))
      return false

    val phial= heldItem.getItem.asInstanceOf[ItemGenericEssentiaContainer]
    var aspect:Aspect=null

    var amount:Int=0
    if(heldItem.getItemDamage==1) {
      val aspects = phial.getAspects(heldItem).getAspects
      if (aspects.length > 0)
        aspect = aspects(0)
      amount = phial.getAspects(heldItem).getAmount(aspect)
    }

    if (heldItem != null && jar.amount <= (jar.maxAmount - 8) && ((jar.aspect != null && jar.aspect != aspect && jar.amount == 0) || jar.aspect == null || (jar.aspect != null && jar.aspect == aspect && amount >= 8))){
      player.inventory.decrStackSize(player.inventory.currentItem,1)

      jar.aspect = aspect
      jar.addToContainer(aspect, amount)
      if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsTC.phial, 1, 0)))
        player.dropItem(ItemsTC.phial, 1)

      world.playSoundAtEntity(player,"liquid.swim",0.25F,1.0F)
    }
    else if (heldItem != null && jar.amount >= 8 && aspect == null)
      {
      player.inventory.decrStackSize(player.inventory.currentItem, 1)
      val newPhial = new ItemStack(ItemsTC.phial, 1, 1)
      val setAspect = new AspectList().add(jar.aspect, 8)
      newPhial.getItem.asInstanceOf[ItemGenericEssentiaContainer].setAspects(newPhial, setAspect)
      if (!player.inventory.addItemStackToInventory(newPhial))
        player.dropItem(newPhial.getItem, 1)

      jar.takeFromContainer(jar.aspect, 8)
      world.playSoundAtEntity(player, "thaumcraft:jar", 0.4F, 1.0F)
    }
    super.onBlockActivated(world,pos,state,player,side,fx,fy,fz)
  }


  override def getRenderColor(state: IBlockState): Int =
    {
      super.getRenderColor(state)
    }

  override def onBlockClicked(worldIn: World, pos: BlockPos, playerIn: EntityPlayer): Unit =
  {
    super.onBlockClicked(worldIn, pos, playerIn)
  }

  override def createBlockState(): BlockState = new BlockState(this,COLOUR,BlockJar.TYPE)

  override def getMetaFromState(state: IBlockState): Int = super.getMetaFromState(state)

  override def getStateFromMeta(meta: Int): IBlockState = super.getStateFromMeta(meta)

  override def getActualState(state: IBlockState, worldIn: IBlockAccess, pos: BlockPos): IBlockState =
    {
      val tileEntity:TileBoundJar=worldIn.getTileEntity(pos).asInstanceOf[TileBoundJar]
      if(tileEntity!=null)
        super.getActualState(state, worldIn, pos).withProperty(COLOUR,EnumDyeColor.byDyeDamage(tileEntity.jarColor))
      else
        super.getActualState(state, worldIn, pos)
    }

  override def dropBlockAsItemWithChance(worldIn: World, pos: BlockPos, state: IBlockState, chance: Float, fortune: Int): Unit =
  {
    val te:TileEntity = worldIn.getTileEntity(pos);
    if(te.isInstanceOf[TileBoundJar]) {
      this.spawnFilledJar(worldIn, pos, state, te.asInstanceOf[TileBoundJar]);
    } else {
      super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }
  }

  override  def harvestBlock(worldIn: World, player: EntityPlayer, pos: BlockPos, state: IBlockState, te: TileEntity)
  =
  {


    if(te.isInstanceOf[TileBoundJar]) {
      this.spawnFilledJar(worldIn, pos, state, te.asInstanceOf[TileBoundJar]);
    } else {
      super.harvestBlock(worldIn, player, pos, state, te)
    }
  }

  def spawnFilledJar(world: World, pos: BlockPos, state: IBlockState, te: TileBoundJar): Unit =
  {
    val drop = new ItemStack(BlocksTC.jar, 1, this.getMetaFromState(state));

    if(te.aspectFilter != null) {
      if(!drop.hasTagCompound()) {
        drop.setTagCompound(new NBTTagCompound());
      }

      drop.getTagCompound().setString("AspectFilter", te.aspectFilter.getTag());
    }

    val seal=new ItemStack(ItemJarSeal,1,te.jarColor)
    ItemJarSeal.setNetwork(seal,te.network)
    Block.spawnAsEntity(world, pos, drop)
    Block.spawnAsEntity(world,pos,seal)
  }


}
