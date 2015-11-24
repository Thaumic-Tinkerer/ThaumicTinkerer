package com.nekokittygames.Thaumic.Tinkerer.common.core.misc

import net.minecraft.entity.Entity
import net.minecraft.entity.monster.{EntityCreeper, EntityCaveSpider, EntityBlaze, EntitySnowman}
import net.minecraft.entity.passive.{EntityCow, EntityChicken, EntityBat}
import net.minecraft.tileentity.TileEntity
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.common.entities.monster.{EntityFireBat, EntityBrainyZombie}

import scala.collection.mutable

/**
  * Created by katsw on 24/11/2015.
  */
object MobAspects {

  val aspects=new mutable.HashMap[Class[_ <: Entity],Array[Aspect]]
  // Vanilla Entities
  aspects.put(classOf[EntitySnowman],Array[Aspect](Aspect.WATER,Aspect.WATER,Aspect.MAN))
  aspects.put(classOf[EntityBat],Array[Aspect](Aspect.AIR,Aspect.AIR,Aspect.FLIGHT))
  aspects.put(classOf[EntityBlaze],Array[Aspect](Aspect.FIRE,Aspect.FIRE,Aspect.FIRE))
  aspects.put(classOf[EntityCaveSpider],Array[Aspect](Aspect.BEAST,Aspect.UNDEAD,Aspect.DEATH))
  aspects.put(classOf[EntityCaveSpider],Array[Aspect](Aspect.BEAST,Aspect.UNDEAD,Aspect.DEATH))
  aspects.put(classOf[EntityChicken],Array[Aspect](Aspect.FLIGHT,Aspect.BEAST,Aspect.PLANT))
  aspects.put(classOf[EntityCow],Array[Aspect](Aspect.BEAST,Aspect.EARTH,Aspect.BEAST))
  aspects.put(classOf[EntityCreeper],Array[Aspect](Aspect.ELDRITCH,Aspect.ENERGY,Aspect.BEAST))
  // TODO: Add more creatures


  // Thaumcraft Entities
  aspects.put(classOf[EntityBrainyZombie],Array[Aspect](Aspect.ELDRITCH,Aspect.UNDEAD,Aspect.MAN))
  aspects.put(classOf[EntityFireBat],Array[Aspect](Aspect.FLIGHT,Aspect.FIRE,Aspect.ELDRITCH))



  def getAspects(clazz:Class[_]):Array[Aspect] =
  {
    if(aspects.contains(clazz.asInstanceOf[Class[_ <: Entity]]))
      {
        aspects.get(clazz.asInstanceOf[Class[_ <: Entity]]).get
      }
    else
      {
        null
      }
  }
}
