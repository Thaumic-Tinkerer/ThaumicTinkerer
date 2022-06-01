/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.api;

import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.jetbrains.annotations.Contract;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityFireBat;
import thaumcraft.common.entities.monster.EntityWisp;

import java.lang.reflect.Method;
import java.util.Dictionary;
import java.util.Hashtable;

public class MobAspects {
    private static  final  Dictionary<Class<?>,MobAspect> aspects=new Hashtable<>();

    @Contract(pure = true)
    public static  Dictionary<Class<?>, MobAspect> getAspects() {
        return aspects;
    }

    static {
        putAspect(EntitySnowman.class,new AspectList().add(Aspect.WATER,2).add(Aspect.MAN,1));
        putAspect(EntityBat.class,new AspectList().add(Aspect.AIR,2).add(Aspect.FLIGHT,1));
        putAspect(EntityBlaze.class,new AspectList().add(Aspect.FIRE,3));
        putAspect(EntityBrainyZombie.class,new AspectList().add(Aspect.MAGIC,1).add(Aspect.UNDEAD,1).add(Aspect.DARKNESS,1),"thaumcraft");
        putAspect(EntityFireBat.class,new AspectList().add(Aspect.FLIGHT,1).add(Aspect.FIRE,1).add(Aspect.MAGIC,1),"thaumcraft");
        putAspect(EntityCaveSpider.class,new AspectList().add(Aspect.BEAST,1).add(Aspect.DEATH,2));
        putAspect(EntityChicken.class,new AspectList().add(Aspect.LIFE,1).add(Aspect.FLIGHT,1).add(Aspect.BEAST,1));
        putAspect(EntityCow.class,new AspectList().add(Aspect.BEAST,2).add(Aspect.EARTH,1));
        putAspect(EntityCreeper.class,new AspectList().add(Aspect.MAGIC,1).add(Aspect.BEAST,1).add(Aspect.ELDRITCH,1));
        putAspect(EntityEnderman.class,new AspectList().add(Aspect.ELDRITCH,2).add(Aspect.MAN,1),0.3f,0.0f);
        putAspect(EntityGhast.class,new AspectList().add(Aspect.FIRE,1).add(Aspect.FLIGHT,2),0.1f,0.2f);
        putAspect(EntityHorse.class,new AspectList().add(Aspect.BEAST,2).add(Aspect.AIR,1));
        putAspect(EntityIronGolem.class,new AspectList().add(Aspect.METAL,2).add(Aspect.MAN,1),0.3f,0.0f);
        putSlimeAspect(EntityMagmaCube.class,new AspectList().add(Aspect.FIRE,1).add(Aspect.WATER,2));
        putAspect(EntityMooshroom.class, new AspectList().add(Aspect.BEAST,1).add(Aspect.EARTH,1).add(Aspect.PLANT,1));
        putAspect(EntityOcelot.class,new AspectList().add(Aspect.BEAST,1).add(Aspect.EARTH,1).add(Aspect.ELDRITCH,1));
        putAspect(EntityPig.class,new AspectList().add(Aspect.BEAST,1).add(Aspect.EARTH,1).add(Aspect.MOTION,1));
        putAspect(EntityPigZombie.class, new AspectList().add(Aspect.UNDEAD,1).add(Aspect.BEAST,1).add(Aspect.FIRE,1));
        putAspect(EntitySheep.class,new AspectList().add(Aspect.EARTH,2).add(Aspect.BEAST,1));
        putAspect(EntitySilverfish.class, new AspectList().add(Aspect.METAL,2).add(Aspect.EARTH,1));
        putAspect(EntitySkeleton.class, new AspectList().add(Aspect.UNDEAD,2).add(Aspect.MAN,1));
        putSlimeAspect(EntitySlime.class, new AspectList().add(Aspect.WATER,2).add(Aspect.BEAST,1));
        putAspect(EntitySpider.class, new AspectList().add(Aspect.BEAST,1).add(Aspect.UNDEAD,2));
        putAspect(EntitySquid.class, new AspectList().add(Aspect.WATER,3));
        putAspect(EntityWisp.class, new AspectList().add(Aspect.AIR,1).add(Aspect.MAGIC,2),"thaumcraft");
        putAspect(EntityWitch.class, new AspectList().add(Aspect.MAGIC,1).add(Aspect.UNDEAD,1).add(Aspect.ELDRITCH,1));
        putAspect(EntityWolf.class, new AspectList().add(Aspect.BEAST,3));
        putAspect(EntityZombie.class, new AspectList().add(Aspect.MAN,1).add(Aspect.UNDEAD,2));
        //put
    }
    static void putAspect(Class<?> clazz,AspectList aspectsList) {
        aspects.put(clazz,new MobAspect(clazz,aspectsList));
    }
    static void putAspect(Class<?> clazz,AspectList aspectsList,String prefix) {
        aspects.put(clazz,new MobAspect(clazz,aspectsList).setPrefix(prefix));
    }
    static void putAspect(Class<?> clazz,AspectList aspectsList,float scale, float offset) {
        aspects.put(clazz,new MobAspect(clazz,aspectsList).setScale(scale).setOffset(offset));
    }
    private static final Method setSlimeSizeMethod;

    static
    {
        setSlimeSizeMethod = ObfuscationReflectionHelper.findMethod(EntitySlime.class, "func_70799_a", void.class, int.class, boolean.class);
    }
    static void putSlimeAspect(Class<?> clazz,AspectList aspectsList) {
        aspects.put(clazz,new MobAspect(clazz,aspectsList) {
            @Override
            public Entity createEntity(World worldObj) {
                Entity entity = super.createEntity(worldObj);
                if(entity instanceof EntitySlime) {
                    try
                    {
                        setSlimeSizeMethod.invoke(entity, 1, true);

                    }
                    catch (ReflectiveOperationException e)
                    {
                        throw new ReportedException(new CrashReport("Could not call method '" + setSlimeSizeMethod.getName() + "'", e));
                    }
                }
                return entity;
            }
        });
    }
}
