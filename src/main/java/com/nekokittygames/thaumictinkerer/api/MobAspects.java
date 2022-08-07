/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.api;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
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
import java.util.HashMap;

public class MobAspects {
    private static  final HashMap<Class<?>,MobAspect> aspects=new HashMap<>();

    @Contract(pure = true)
    public static  HashMap<Class<?>, MobAspect> getAspects() {
        return aspects;
    }

    static {
        putAspect(EntitySnowman.class,"SnowMan",new AspectList().add(Aspect.WATER,2).add(Aspect.MAN,1));
        putAspect(EntityBat.class,"Bat",new AspectList().add(Aspect.AIR,2).add(Aspect.FLIGHT,1));
        putAspect(EntityBlaze.class,"Blaze",new AspectList().add(Aspect.FIRE,3));
        putAspect(EntityBrainyZombie.class,"BrainyZombie",new AspectList().add(Aspect.MAGIC,1).add(Aspect.UNDEAD,1).add(Aspect.DARKNESS,1),"thaumcraft");
        putAspect(EntityFireBat.class,"Firebat",new AspectList().add(Aspect.FLIGHT,1).add(Aspect.FIRE,1).add(Aspect.MAGIC,1),"thaumcraft");
        putAspect(EntityCaveSpider.class,"CaveSpider",new AspectList().add(Aspect.BEAST,1).add(Aspect.DEATH,2));
        putAspect(EntityChicken.class,"Chicken",new AspectList().add(Aspect.LIFE,1).add(Aspect.FLIGHT,1).add(Aspect.BEAST,1));
        putAspect(EntityCow.class,"Cow",new AspectList().add(Aspect.BEAST,2).add(Aspect.EARTH,1));
        putAspect(EntityCreeper.class,"Creeper",new AspectList().add(Aspect.MAGIC,1).add(Aspect.BEAST,1).add(Aspect.ELDRITCH,1));
        putAspect(EntityEnderman.class,"Enderman",new AspectList().add(Aspect.ELDRITCH,2).add(Aspect.MAN,1),0.3f,0.0f);
        putAspect(EntityGhast.class,"Ghast",new AspectList().add(Aspect.FIRE,1).add(Aspect.FLIGHT,2),0.1f,0.2f);
        putAspect(EntityHorse.class,"Horse",new AspectList().add(Aspect.BEAST,2).add(Aspect.AIR,1));
        putAspect(EntityIronGolem.class,"VillagerGolem",new AspectList().add(Aspect.METAL,2).add(Aspect.MAN,1),0.3f,0.0f);
        putSlimeAspect(EntityMagmaCube.class,"LavaSlime",new AspectList().add(Aspect.FIRE,1).add(Aspect.WATER,2));
        putAspect(EntityMooshroom.class, "MushroomCow",new AspectList().add(Aspect.BEAST,1).add(Aspect.EARTH,1).add(Aspect.PLANT,1));
        putAspect(EntityOcelot.class,"Ozelot",new AspectList().add(Aspect.BEAST,1).add(Aspect.EARTH,1).add(Aspect.ELDRITCH,1));
        putAspect(EntityPig.class,"Pig",new AspectList().add(Aspect.BEAST,1).add(Aspect.EARTH,1).add(Aspect.MOTION,1));
        putAspect(EntityPigZombie.class, "PigZombie",new AspectList().add(Aspect.UNDEAD,1).add(Aspect.BEAST,1).add(Aspect.FIRE,1));
        putAspect(EntitySheep.class,"Sheep",new AspectList().add(Aspect.EARTH,2).add(Aspect.BEAST,1));
        putAspect(EntitySilverfish.class, "Silverfish",new AspectList().add(Aspect.METAL,2).add(Aspect.EARTH,1));
        putAspect(EntitySkeleton.class, "Skeleton",new AspectList().add(Aspect.UNDEAD,1).add(Aspect.MAN,1).add(Aspect.TOOL,1));
        putSlimeAspect(EntitySlime.class, "Slime",new AspectList().add(Aspect.WATER,2).add(Aspect.BEAST,1));
        putAspect(EntitySpider.class, "Spider",new AspectList().add(Aspect.BEAST,1).add(Aspect.UNDEAD,2));
        putAspect(EntitySquid.class, "Squid",new AspectList().add(Aspect.WATER,3));
        putAspect(EntityWisp.class, "Wisp",new AspectList().add(Aspect.AIR,1).add(Aspect.MAGIC,2),"thaumcraft");
        putAspect(EntityWitch.class, "Witch",new AspectList().add(Aspect.MAGIC,1).add(Aspect.UNDEAD,1).add(Aspect.ELDRITCH,1));
        putAspect(EntityWolf.class, "Wolf",new AspectList().add(Aspect.BEAST,3));
        putAspect(EntityZombie.class, "Zombie",new AspectList().add(Aspect.MAN,1).add(Aspect.UNDEAD,2));
        //put
    }


    public static void checkAspects() {
        for(MobAspect mobAspect: aspects.values()) {
            AspectList curAspects=mobAspect.getAspects();
            for(MobAspect mobAspect2: aspects.values()) {
                if(mobAspect.equals(mobAspect2))
                    continue;
                AspectList checkAspects = mobAspect2.getAspects();
                boolean same=true;
                for(Aspect aspect:curAspects.getAspects()) {
                    if(checkAspects.getAmount(aspect)!=curAspects.getAmount(aspect))
                        same=false;
                }
                if(same)
                    ThaumicTinkerer.logger.error("Warning, Two entities have the same mob aspects"+mobAspect.toString()+", "+mobAspect2.toString());

            }
        }
        ThaumicTinkerer.logger.info("Checked Mob Aspects");
    }

    public static MobAspect getByAspects(AspectList aspectList) {
        for(MobAspect mobAspect2: aspects.values()) {
            AspectList checkAspects = mobAspect2.getAspects();
            boolean same = true;
            for (Aspect aspect : aspectList.getAspects()) {
                if (checkAspects.getAmount(aspect) != aspectList.getAmount(aspect))
                    same = false;
            }
            if (same)
                return mobAspect2;
        }
        return null;
    }
    static void putAspect(Class<?> clazz, String entityName,AspectList aspectsList) {
        aspects.put(clazz,new MobAspect(clazz,entityName,aspectsList));
    }
    static void putAspect(Class<?> clazz, String entityName,AspectList aspectsList,String prefix) {
        aspects.put(clazz,new MobAspect(clazz,entityName,aspectsList).setPrefix(prefix));
    }
    static void putAspect(Class<?> clazz, String entityName,AspectList aspectsList,float scale, float offset) {
        aspects.put(clazz,new MobAspect(clazz,entityName,aspectsList).setScale(scale).setOffset(offset));
    }
    private static final Method setSlimeSizeMethod;

    static
    {
        setSlimeSizeMethod = ObfuscationReflectionHelper.findMethod(EntitySlime.class, "func_70799_a", void.class, int.class, boolean.class);
    }
    static void putSlimeAspect(Class<?> clazz, String entityName,AspectList aspectsList) {
        aspects.put(clazz,new MobAspect(clazz,entityName,aspectsList) {
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
