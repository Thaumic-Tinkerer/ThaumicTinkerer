package com.nekokittygames.Thaumic.Tinkerer.common.core.misc

import net.minecraft.entity.Entity
import net.minecraft.entity.monster._
import net.minecraft.entity.passive._
import net.minecraft.tileentity.TileEntity
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.common.entities.monster.{EntityWisp, EntityFireBat, EntityBrainyZombie}

import scala.collection.mutable
import scala.reflect.internal.util.Collections

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
  aspects.put(classOf[EntityEnderman],Array[Aspect](Aspect.ELDRITCH,Aspect.ELDRITCH,Aspect.MAN))
  aspects.put(classOf[EntityGhast],Array[Aspect](Aspect.FIRE,Aspect.FLIGHT,Aspect.FLIGHT))
  aspects.put(classOf[EntityHorse],Array[Aspect](Aspect.BEAST,Aspect.BEAST,Aspect.MOTION))
  aspects.put(classOf[EntityIronGolem],Array[Aspect](Aspect.METAL,Aspect.METAL,Aspect.MAN))
  aspects.put(classOf[EntityMagmaCube],Array[Aspect](Aspect.FIRE,Aspect.DARKNESS,Aspect.DARKNESS))
  aspects.put(classOf[EntityMooshroom],Array[Aspect](Aspect.BEAST,Aspect.EARTH,Aspect.LIFE))
  aspects.put(classOf[EntityOcelot],Array[Aspect](Aspect.BEAST,Aspect.EARTH,Aspect.ELDRITCH))
  aspects.put(classOf[EntityPig],Array[Aspect](Aspect.BEAST,Aspect.EARTH,Aspect.MOTION))
  aspects.put(classOf[EntityPigZombie],Array[Aspect](Aspect.UNDEAD,Aspect.FIRE,Aspect.MAN))
  aspects.put(classOf[EntitySheep],Array[Aspect](Aspect.EARTH,Aspect.TOOL,Aspect.BEAST))
  aspects.put(classOf[EntitySilverfish],Array[Aspect](Aspect.METAL,Aspect.METAL,Aspect.EARTH))
  aspects.put(classOf[EntitySkeleton],Array[Aspect](Aspect.UNDEAD,Aspect.MAN,Aspect.UNDEAD))
  aspects.put(classOf[EntitySlime],Array[Aspect](Aspect.DARKNESS,Aspect.DARKNESS,Aspect.BEAST))
  aspects.put(classOf[EntitySpider],Array[Aspect](Aspect.BEAST,Aspect.UNDEAD,Aspect.UNDEAD))
  aspects.put(classOf[EntitySquid],Array[Aspect](Aspect.WATER,Aspect.WATER,Aspect.WATER))
  aspects.put(classOf[EntityVillager],Array[Aspect](Aspect.MAN,Aspect.MAN,Aspect.MAN))
  aspects.put(classOf[EntityWolf],Array[Aspect](Aspect.BEAST,Aspect.BEAST,Aspect.BEAST))
  aspects.put(classOf[EntityZombie],Array[Aspect](Aspect.UNDEAD,Aspect.UNDEAD,Aspect.UNDEAD))

  // TODO: Add more creatures


  // Thaumcraft Entities
  aspects.put(classOf[EntityBrainyZombie],Array[Aspect](Aspect.ELDRITCH,Aspect.UNDEAD,Aspect.MAN))
  aspects.put(classOf[EntityFireBat],Array[Aspect](Aspect.FLIGHT,Aspect.FIRE,Aspect.ELDRITCH))
  aspects.put(classOf[EntityWisp],Array[Aspect](Aspect.AIR,Aspect.ELDRITCH,Aspect.ELDRITCH))


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

  def getClazz(inAspect:Array[Aspect]):Class[_]=
  {
    for((key:Class[_],value:Array[Aspect])<-aspects)
      {
        if(value.forall(x=> inAspect.contains(x)))
          {
            return key
          }
      }
    return null
  }
}
