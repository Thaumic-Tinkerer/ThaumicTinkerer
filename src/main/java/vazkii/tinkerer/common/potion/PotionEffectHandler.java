package vazkii.tinkerer.common.potion;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.BlockForcefield;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by pixlepix on 4/19/14.
 */
public class PotionEffectHandler {

	public static HashMap<Entity, Long> airPotionHit = new HashMap<Entity, Long>();
	public static HashMap<Entity, Long> firePotionHit = new HashMap<Entity, Long>();

	@SubscribeEvent
	public void onLivingHurt(LivingAttackEvent e) {
		if (e.source.getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e.source.getSourceOfDamage();
			if (p.isPotionActive(ModPotions.potionAir) && !p.worldObj.isRemote) {
				airPotionHit.put(e.entity, e.entity.worldObj.getTotalWorldTime());
			}
			if (p.isPotionActive(ModPotions.potionFire) && !p.worldObj.isRemote) {
				firePotionHit.put(e.entity, e.entity.worldObj.getTotalWorldTime());
			}
			if (p.isPotionActive(ModPotions.potionEarth) && !p.worldObj.isRemote) {
				boolean xAxis = Math.abs(e.entity.posZ - p.posZ) < Math.abs(e.entity.posX - p.posX);
				int centerX = (int) ((e.entity.posX + p.posX) / 2);

				int centerY = (int) (p.posY + 2);
				int centerZ = (int) ((e.entity.posZ + p.posZ) / 2);

				for (int i = -2; i < 3; i++) {
					for (int j = -2; j < 3; j++) {
						if (xAxis) {
							if (p.worldObj.isAirBlock(centerX, centerY + i, centerZ + j)) {
								p.worldObj.setBlock(centerX, centerY + i, centerZ + j, ThaumicTinkerer.registry.getFirstBlockFromClass(BlockForcefield.class));
								ThaumicTinkerer.tcProxy.blockSparkle(p.worldObj, centerX, centerY + i, centerZ + j, 100, 100);
							}
						} else {
							if (p.worldObj.isAirBlock(centerX + j, centerY + i, centerZ)) {
								p.worldObj.setBlock(centerX + j, centerY + i, centerZ, ThaumicTinkerer.registry.getFirstBlockFromClass(BlockForcefield.class));

								ThaumicTinkerer.tcProxy.blockSparkle(p.worldObj, centerX + j, centerY + i, centerZ, 100, 100);
							}
						}
					}

				}

			}
		}

	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
		if (e.player.isPotionActive(ModPotions.potionWater)) {
			for (int x = (int) (e.player.posX - 2); x < e.player.posX + 2; x++) {
				for (int y = (int) (e.player.posY - 2); y < e.player.posY + 2; y++) {
					for (int z = (int) (e.player.posZ - 2); z < e.player.posZ + 2; z++) {
						if (e.player.worldObj.getBlock(x, y, z) == Blocks.lava || e.player.worldObj.getBlock(x, y, z) == Blocks.flowing_lava) {
							e.player.worldObj.setBlock(x, y, z, Blocks.obsidian);
							ThaumicTinkerer.tcProxy.burst(e.player.worldObj, x + .5, y + .5, z + .5, 1.2F);

						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent e) {

		Iterator iter = airPotionHit.keySet().iterator();
		while (iter.hasNext()) {
			Entity target = (Entity) iter.next();
			if (target.isEntityAlive()) {
				if (target.worldObj.getTotalWorldTime() % 5 == 0) {
					Random rand = new Random();
					target.setVelocity(rand.nextFloat() - .5, rand.nextFloat(), rand.nextFloat() - .5);
					ThaumicTinkerer.tcProxy.burst(target.worldObj, target.posX, target.posY, target.posZ, .5F);
				}
			}
			if (target.worldObj.getTotalWorldTime() > airPotionHit.get(target) + 62) {
				iter.remove();
			}
		}

		//Fire Potion
		iter = firePotionHit.keySet().iterator();
		while (iter.hasNext()) {
			Entity target = (Entity) iter.next();
			if (target.isEntityAlive()) {
				if (target.worldObj.getTotalWorldTime() % 5 == 0) {
					Random rand = new Random();
					target.setFire(6);

					for (int i = 0; i < 30; i++) {
						double theta = rand.nextFloat() * 2 * Math.PI;

						double phi = rand.nextFloat() * 2 * Math.PI;
						double r = 2.5;

						double x = r * Math.sin(theta) * Math.cos(phi);

						double y = r * Math.sin(theta) * Math.sin(phi);

						double z = r * Math.cos(theta);

						ThaumicTinkerer.tcProxy.wispFX2(target.worldObj, target.posX + x, target.posY + y + 1, target.posZ + z, .1F, 4, true, 1F);
					}

				}
			}
			if (target.worldObj.getTotalWorldTime() > firePotionHit.get(target) + 6000) {
				iter.remove();
			}
		}
	}

}
