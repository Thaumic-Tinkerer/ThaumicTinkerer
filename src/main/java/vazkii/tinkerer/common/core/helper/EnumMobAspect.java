package vazkii.tinkerer.common.core.helper;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.entities.monster.EntityBrainyZombie;
import thaumcraft.common.entities.monster.EntityFireBat;
import thaumcraft.common.entities.monster.EntityWisp;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.item.ItemMobAspect;
import vazkii.tinkerer.common.item.ItemMobDisplay;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.lib.LibResearch;

import java.util.Map;

public enum EnumMobAspect {

	SnowMan(EntitySnowman.class, new Aspect[]{Aspect.WATER, Aspect.WATER, Aspect.MAN}),
	Bat(EntityBat.class, new Aspect[]{Aspect.AIR, Aspect.AIR, Aspect.FLIGHT},1.9f,-0.3f),
	Blaze(EntityBlaze.class, new Aspect[]{Aspect.FIRE, Aspect.FLIGHT, Aspect.MAGIC}),
    BrainyZombie(EntityBrainyZombie.class, new Aspect[]{Aspect.MAGIC, Aspect.UNDEAD, Aspect.FLESH}),
	Firebat(EntityFireBat.class, new Aspect[]{Aspect.FLIGHT, Aspect.FIRE, Aspect.MAGIC},1.9f,-0.3f),
	CaveSpider(EntityCaveSpider.class, new Aspect[]{Aspect.BEAST, Aspect.POISON, Aspect.POISON}),
	Chicken(EntityChicken.class, new Aspect[]{Aspect.CROP, Aspect.FLIGHT, Aspect.BEAST}),
	Cow(EntityCow.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.BEAST}),
	Creeper(EntityCreeper.class, new Aspect[]{Aspect.MAGIC, Aspect.BEAST, Aspect.ELDRITCH}),
	Enderman(EntityEnderman.class, new Aspect[]{Aspect.ELDRITCH, Aspect.ELDRITCH, Aspect.MAN},0.3f,0.0f),
	Ghast(EntityGhast.class, new Aspect[]{Aspect.FIRE, Aspect.FLIGHT, Aspect.MAGIC},0.1f,0.2f),
	Horse(EntityHorse.class, new Aspect[]{Aspect.BEAST, Aspect.BEAST, Aspect.TRAVEL}),
    VillagerGolem(EntityIronGolem.class, new Aspect[]{Aspect.METAL, Aspect.METAL, Aspect.MAN},0.3f,0.0f),
	LavaSlime(EntityMagmaCube.class, new Aspect[]{Aspect.FIRE, Aspect.SLIME, Aspect.SLIME},0.6f,0.0f){
        @Override
        protected Entity createEntity() {
            return setSlimeSize(super.createEntity(), 1);
        }
    },
	MushroomCow(EntityMooshroom.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.CROP}),
	Ozelot(EntityOcelot.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.ELDRITCH}),
	Pig(EntityPig.class, new Aspect[]{Aspect.BEAST, Aspect.EARTH, Aspect.TRAVEL}),
	PigZombie(EntityPigZombie.class, new Aspect[]{Aspect.UNDEAD, Aspect.FLESH, Aspect.FIRE}),
	Sheep(EntitySheep.class, new Aspect[]{Aspect.EARTH, Aspect.EARTH, Aspect.BEAST}),
	Silverfish(EntitySilverfish.class, new Aspect[]{Aspect.METAL, Aspect.METAL, Aspect.EARTH}),
	Skeleton(EntitySkeleton.class, new Aspect[]{Aspect.UNDEAD, Aspect.MAN, Aspect.UNDEAD}),
	Slime(EntitySlime.class, new Aspect[]{Aspect.SLIME, Aspect.SLIME, Aspect.BEAST},0.6f,0.0f){
        @Override
        protected Entity createEntity() {
            return setSlimeSize(super.createEntity(), 1);
        }
    },
	Spider(EntitySpider.class, new Aspect[]{Aspect.BEAST, Aspect.UNDEAD, Aspect.UNDEAD}),
	Squid(EntitySquid.class, new Aspect[]{Aspect.WATER, Aspect.WATER, Aspect.WATER},0.3f,0.5f),
	Wisp(EntityWisp.class, new Aspect[]{Aspect.AIR, Aspect.MAGIC, Aspect.MAGIC}),
	Witch(EntityWitch.class, new Aspect[]{Aspect.MAGIC, Aspect.UNDEAD, Aspect.ELDRITCH},0.35f,0.0f),
	Wolf(EntityWolf.class, new Aspect[]{Aspect.BEAST, Aspect.BEAST, Aspect.BEAST}),
	Zombie(EntityZombie.class, new Aspect[]{Aspect.FLESH, Aspect.FLESH, Aspect.UNDEAD})

	;

    public static Entity getEntityFromCache(EnumMobAspect ent) {
        Entity entity = entityCache.get(ent);
        if (entity == null) {
            entity = ent.createEntity();
            entityCache.put(ent, entity);
        }
        return entity;
    }

    private static Entity setSlimeSize(Entity entity, int size) {

        if(entity instanceof EntitySlime){
            //TODO: Get nekosune to make an access transformer for this
            ((EntitySlime)entity).setSlimeSize(1);
        }

        return entity;
    }
    public static Map<EnumMobAspect, Entity> entityCache = Maps.newHashMap();
	public Aspect[] aspects;
	public Class entity;
    private float scale;
    private float offset;
	EnumMobAspect(Class entity, Aspect[] aspects,float scale,float offset){
		this.aspects=aspects;
		this.entity=entity;
        this.scale=scale;
        this.offset=offset;
	}
    public float getVerticalOffset()
    {
        return offset;
    }

    public float getScale()
    {
        return scale;
    }

    EnumMobAspect(Class entity, Aspect[] aspects){
        this.aspects=aspects;
        this.entity=entity;
        this.scale=1.1f;
        this.offset=0.0f;
    }
    public Class getEntityClass()
    {
        return entity;
    }

    public Entity getEntity() {
        return getEntityFromCache(this);
    }
    protected Entity createEntity() {
        return EntityList.createEntityByName(toString(), (Minecraft.getMinecraft() != null) ? Minecraft.getMinecraft().theWorld : null);
    }
	public static Aspect[] getAspectsForEntity(Entity e){
		return getAspectsForEntity(e.getClass());
	}
    public static EnumMobAspect getMobAspectForType(String name)
    {
        if(name.isEmpty())
            return null;
        Class clazz= (Class) EntityList.stringToClassMapping.get(name);
        for(EnumMobAspect e:EnumMobAspect.values()){
            if(clazz.equals(e.entity)){
                return e;
            }
        }
        return null;
    }
	public static Aspect[] getAspectsForEntity(Class clazz){
		for(EnumMobAspect e:EnumMobAspect.values()){
			if(clazz.equals(e.entity)){
				return e.aspects;
			}
		}
		return null;
	}
    public ResearchPage GetRecepiePage()
    {
        ItemStack output=new ItemStack(ModItems.mobDisplay);
        ((ItemMobDisplay)output.getItem()).setEntityType(output,toString());
        ItemStack[] inputs=new ItemStack[this.aspects.length];
        int i=0;
        for(Aspect a:this.aspects)
        {
            inputs[i++]=ItemMobAspect.getStackFromAspect(a);
        }
        InfusionRecipe recepie=new InfusionRecipe(LibResearch.KEY_SUMMON,output,0,new AspectList(),new ItemStack(ModBlocks.spawner),inputs);
        return new ResearchPage(recepie);
    }

}
