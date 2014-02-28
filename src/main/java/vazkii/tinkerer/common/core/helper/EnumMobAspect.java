package vazkii.tinkerer.common.core.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySnowman;
import thaumcraft.api.aspects.Aspect;

public enum EnumMobAspect {

	SNOW_GOLEM(EntitySnowman.class, new Aspect[]{Aspect.WATER, Aspect.WATER, Aspect.MAN});


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
