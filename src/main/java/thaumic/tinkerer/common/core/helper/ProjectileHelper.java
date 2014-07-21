package thaumic.tinkerer.common.core.helper;

import com.google.common.base.Function;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import thaumcraft.common.entities.projectile.EntityFrostShard;

import java.util.IdentityHashMap;
import java.util.Map;

public final class ProjectileHelper {
	private static Map<Class<? extends Entity>, Function<Entity, Entity>> ownerGetters =
			new IdentityHashMap<Class<? extends Entity>, Function<Entity, Entity>>();

	public static Entity getOwner(Entity projectile) {
		Function<Entity, Entity> ownerGetterForClass = ownerGetters.get(projectile.getClass());
		if (ownerGetterForClass != null) {
			return ownerGetterForClass.apply(projectile);
		}

		// Check if we have owner getter for any of the superclasses
		for (Map.Entry<Class<? extends Entity>, Function<Entity, Entity>> ownerGetter : ownerGetters.entrySet()) {
			if (ownerGetter.getKey().isAssignableFrom(projectile.getClass())) {
				return ownerGetter.getValue().apply(projectile);
			}
		}

		return null;
	}

	public static void registerOwnerGetter(Class<? extends Entity> projectileClass, Function<Entity, Entity> ownerGetter) {
		ownerGetters.put(projectileClass, ownerGetter);
	}

	/* Owner getters for vanilla Minecraft and Thaumcraft projectiles */
	public static class VanillaArrowOwnerGetter implements Function<Entity, Entity> {
		@Override
		public Entity apply(Entity e) {
			return ((EntityArrow) e).shootingEntity;
		}
	}

	public static class VanillaThrowableOwnerGetter implements Function<Entity, Entity> {
		@Override
		public Entity apply(Entity e) {
			return ((EntityThrowable) e).getThrower();
		}
	}

	public static class ThaumcraftFrostShardOwnerGetter implements Function<Entity, Entity> {
		@Override
		public Entity apply(Entity e) {
			Entity owner = ((EntityFrostShard) e).shootingEntity;
			return owner != null ? owner : e.worldObj.getEntityByID(((EntityFrostShard) e).shootingEntityId);
		}
	}

	static {
		registerOwnerGetter(EntityArrow.class, new VanillaArrowOwnerGetter());
		registerOwnerGetter(EntityThrowable.class, new VanillaThrowableOwnerGetter());
		registerOwnerGetter(EntityFrostShard.class, new ThaumcraftFrostShardOwnerGetter());
	}
}