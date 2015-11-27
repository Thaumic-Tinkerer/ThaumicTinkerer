package com.nekokittygames.Thaumic.Tinkerer.common.tiles


import com.nekokittygames.Thaumic.Tinkerer.common.ThaumicTinkerer
import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.MobAspects
import com.nekokittygames.Thaumic.Tinkerer.common.items.ItemMobAspect
import net.minecraft.entity.{IEntityLivingData, EntityLiving, EntityList}
import net.minecraft.entity.monster.EntitySkeleton
import net.minecraft.server.gui.IUpdatePlayerListBox
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.BlockPos
import net.minecraft.world.WorldProviderHell
import thaumcraft.api.aspects.Aspect
import thaumcraft.common.Thaumcraft
import thaumcraft.common.tiles.crafting.TilePedestal

import scala.collection.mutable

/**
  * Created by katsw on 25/11/2015.
  */
class TileSummon extends TileEntity with IUpdatePlayerListBox {
  override def update(): Unit = {
    if (worldObj.getTotalWorldTime() % 300 == 0) {
      if(worldObj.isBlockIndirectlyGettingPowered(pos)!=0)
        return;

      for(radius <- 1 until 6)
        {
          var pedastels:mutable.MutableList[TilePedestal]=new mutable.MutableList[TilePedestal]
          for(x<-( pos.getX-radius).until (pos.getX+radius); z<-( pos.getZ-radius).until (pos.getZ+radius) )
            {
              val blockPos=new BlockPos(x,pos.getY,z)
              val isAir=worldObj.isAirBlock(blockPos)
              if(!isAir)
                {
                  val tileEntity=worldObj.getTileEntity(blockPos)
                  if(tileEntity.isInstanceOf[TilePedestal] && tileEntity.asInstanceOf[TilePedestal].getStackInSlot(0)!=null && tileEntity.asInstanceOf[TilePedestal].getStackInSlot(0).getItem == ItemMobAspect)
                    pedastels.+=(tileEntity.asInstanceOf[TilePedestal])
                }
            }
          for(i<-0 until pedastels.size; j<-0 until pedastels.size ; k<-0 until pedastels.size)
            {
              val pedastel1:TilePedestal=pedastels(i)
              val pedastel2:TilePedestal=pedastels(j)
              val pedastel3:TilePedestal=pedastels(k)

              if(pedastel1!=pedastel2 && pedastel2!=pedastel3 && pedastel3!=pedastel1)
                {
                    val aspectList=Array[Aspect](ItemMobAspect.getAspect(pedastel1.getStackInSlot(0)),ItemMobAspect.getAspect(pedastel2.getStackInSlot(0)),ItemMobAspect.getAspect(pedastel3.getStackInSlot(0)))
                    val entityClazz=MobAspects.getClazz(aspectList)
                  if(entityClazz==null)
                    return
                    val isInfused=ItemMobAspect.isInfused(pedastel1.getStackInSlot(0)) &&
                                  ItemMobAspect.isInfused(pedastel2.getStackInSlot(0)) &&
                                  ItemMobAspect.isInfused(pedastel3.getStackInSlot(0))

                  if(isInfused && worldObj.getTotalWorldTime%1200!=0)
                    return
                  if(!isInfused) {
                    pedastel1.setInventorySlotContents(0, null)
                    pedastel2.setInventorySlotContents(0, null)
                    pedastel3.setInventorySlotContents(0, null)
                  }
                  if(!worldObj.isRemote)
                    {
                      val entity=EntityList.createEntityByName(EntityList.classToStringMapping.get(entityClazz).asInstanceOf[String],worldObj)
                      entity.setLocationAndAngles(pos.getX+.5,pos.getY+1,pos.getZ+.5,0,0)
                      if(entity.isInstanceOf[EntitySkeleton] && worldObj.provider.isInstanceOf[WorldProviderHell])
                        entity.asInstanceOf[EntitySkeleton].setSkeletonType(1)
                      entity.asInstanceOf[EntityLiving].onInitialSpawn(worldObj.getDifficultyForLocation(new BlockPos(entity)), null.asInstanceOf[IEntityLivingData])
                      worldObj.spawnEntityInWorld(entity)
                      entity.asInstanceOf[EntityLiving].playLivingSound()

                    }
                  else
                    {

                      Thaumcraft.proxy.getFX.essentiaTrailFx(pedastel1.getPos,pos,20,aspectList(0).getColor,20)
                      Thaumcraft.proxy.getFX.essentiaTrailFx(pedastel2.getPos,pos,20,aspectList(1).getColor,20)
                      Thaumcraft.proxy.getFX.essentiaTrailFx(pedastel3.getPos,pos,20,aspectList(2).getColor,20)
                    }
                  return

                }
            }
        }
    }
  }
}
