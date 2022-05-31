/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.api;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityFireBat;

import java.util.Dictionary;
import java.util.Hashtable;

public class MobAspects {
    private static  final  Dictionary<Class<?>,MobAspect> aspects=new Hashtable<>();

    public static  Dictionary<Class<?>, MobAspect> getAspects() {
        return aspects;
    }

    static {
        aspects.put(EntitySnowman.class,new MobAspect(EntitySnowman.class,new AspectList().add(Aspect.WATER,2).add(Aspect.MAN,1)));
        aspects.put(EntityBat.class,new MobAspect(EntityBat.class,new AspectList().add(Aspect.AIR,2).add(Aspect.FLIGHT,1)));
        aspects.put(EntityBlaze.class,new MobAspect(EntityBlaze.class,new AspectList().add(Aspect.FIRE,3)));
        aspects.put(EntityBrainyZombie.class,new MobAspect(EntityBrainyZombie.class,new AspectList().add(Aspect.MAGIC,1).add(Aspect.UNDEAD,1).add(Aspect.DARKNESS,1)).setPrefix("thaumcraft"));
        aspects.put(EntityFireBat.class,new MobAspect(EntityFireBat.class,new AspectList().add(Aspect.FLIGHT,1).add(Aspect.FIRE,1).add(Aspect.MAGIC,1)).setPrefix("thaumcraft"));
        aspects.put(EntityCaveSpider.class,new MobAspect(EntityCaveSpider.class,new AspectList().add(Aspect.BEAST,1).add(Aspect.DEATH,2)));
        aspects.put(EntityChicken.class,new MobAspect(EntityChicken.class,new AspectList().add(Aspect.LIFE,1).add(Aspect.FLIGHT,1).add(Aspect.BEAST,1)));
        aspects.put(EntityCow.class,new MobAspect(EntityCow.class,new AspectList().add(Aspect.BEAST,2).add(Aspect.EARTH,1)));
        aspects.put(EntityCreeper.class,new MobAspect(EntityCreeper.class,new AspectList().add(Aspect.MAGIC,1).add(Aspect.BEAST,1).add(Aspect.ELDRITCH,1)));
        aspects.put(EntityEnderman.class,new MobAspect(EntityEnderman.class,new AspectList().add(Aspect.ELDRITCH,2).add(Aspect.MAN,1),0.3f,0.0f));
        aspects.put(EntityGhast.class,new MobAspect(EntityGhast.class,new AspectList().add(Aspect.FIRE,1).add(Aspect.FLIGHT,2),0.1f,0.2f));
        aspects.put(EntityHorse.class,new MobAspect(EntityHorse.class,new AspectList().add(Aspect.BEAST,2).add(Aspect.AIR,1)));

    }
}
