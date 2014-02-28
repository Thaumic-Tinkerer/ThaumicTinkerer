package vazkii.tinkerer.common.core.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityFireBat;
import thaumcraft.common.entities.monster.EntityWisp;

public enum EnumMobAspect {

	SNOW_GOLEM(EntitySnowman.class, new Aspect[]{Aspect.WATER, Aspect.WATER, Aspect.MAN}),
	BAT(EntityBat.class, new Aspect[]{Aspect.AIR, Aspect.AIR, Aspect.FLIGHT}),
	BLAZE(EntityBlaze.class, new Aspect[]{Aspect.FIRE, Aspect.FLIGHT, Aspect.MAGIC}),
	BRAIN_ZOMBIE(EntityBrainyZombie.class, new Aspect[]{Aspect.MAGIC, Aspect.UNDEAD, Aspect.FLESH}),
	FIRE_BAT(EntityFireBat.class, new Aspect[]{Aspect.FLIGHT, Aspect.FIRE, Aspect.MAGIC}),
	CAVE_SPIDER(EntityCaveSpider.class, new Aspect[]{Aspect.BEAST, Aspect.POISON, Aspect.POISON}),
	CHICKEN(EntityChicken.class, new Aspect[]{Aspect.AIR, Aspect.FLIGHT, Aspect.BEAST}),
	COW(EntityCow.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.BEAST}),
	CREEPER(EntityCreeper.class, new Aspect[]{Aspect.MAGIC, Aspect.BEAST, Aspect.ELDRITCH}),
	ENDERMAN(EntityEnderman.class, new Aspect[]{Aspect.ELDRITCH, Aspect.ELDRITCH, Aspect.MAN}),
	GHAST(EntityGhast.class, new Aspect[]{Aspect.FIRE, Aspect.FLIGHT, Aspect.MAGIC}),
	HORSE(EntityHorse.class, new Aspect[]{Aspect.BEAST, Aspect.BEAST, Aspect.TRAVEL}),
	IRON_GOLEM(EntityIronGolem.class, new Aspect[]{Aspect.METAL, Aspect.METAL, Aspect.MAN}),
	MAGMA_CUBE(EntityMagmaCube.class, new Aspect[]{Aspect.FIRE, Aspect.SLIME, Aspect.SLIME}),
	MOOSHROOM(EntityMooshroom.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.SEED}),
	OCELOT(EntityOcelot.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.ELDRITCH}),
	PIG(EntityPig.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.TRAVEL}),
	PIG_ZOMBIE(EntityPigZombie.class, new Aspect[]{Aspect.UNDEAD, Aspect.FLESH, Aspect.FIRE}),
	PLAYER(EntityPlayer.class, new Aspect[]{Aspect.MAN, Aspect.MAN, Aspect.MAN}),
	SHEEP(EntitySheep.class, new Aspect[]{Aspect.EARTH, Aspect.EARTH, Aspect.BEAST}),
	SILVERFISH(EntitySilverfish.class, new Aspect[]{Aspect.METAL, Aspect.METAL, Aspect.EARTH}),
	SKELETON(EntitySkeleton.class, new Aspect[]{Aspect.UNDEAD, Aspect.MAN, Aspect.UNDEAD}),
	SLIME(EntitySlime.class, new Aspect[]{Aspect.SLIME, Aspect.SLIME, Aspect.BEAST}),
	SPIDER(EntitySpider.class, new Aspect[]{Aspect.BEAST, Aspect.UNDEAD, Aspect.UNDEAD}),
	SQUID(EntitySquid.class, new Aspect[]{Aspect.WATER, Aspect.WATER, Aspect.WATER}),
	WISP(EntityWisp.class, new Aspect[]{Aspect.AIR, Aspect.MAGIC, Aspect.MAGIC}),
	WITCH(EntityWitch.class, new Aspect[]{Aspect.MAGIC, Aspect.UNDEAD, Aspect.ELDRITCH}),
	WOLF(EntityWolf.class, new Aspect[]{Aspect.BEAST, Aspect.BEAST, Aspect.BEAST}),
	ZOMBIE(EntityZombie.class, new Aspect[]{Aspect.FLESH, Aspect.FLESH, Aspect.UNDEAD})
	;



	public Aspect[] aspects;
	public Class entity;

	EnumMobAspect(Class entity, Aspect[] aspects){
		this.aspects=aspects;
		this.entity=entity;
	}

	public static Aspect[] getAspectsForEntity(Entity e){
		return getAspectsForEntity(e.getClass());
	}

	public static Aspect[] getAspectsForEntity(Class clazz){
		for(EnumMobAspect e:EnumMobAspect.values()){
			System.out.println(1);
			if(clazz.equals(e.entity)){
				return e.aspects;
			}
		}
		return null;
	}

}
