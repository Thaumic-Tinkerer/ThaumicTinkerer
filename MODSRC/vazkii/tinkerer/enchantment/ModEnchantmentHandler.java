/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [25 May 2013, 22:57:41 (GMT)]
 */
package vazkii.tinkerer.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import vazkii.tinkerer.lib.LibEnchantmentIDs;
import vazkii.tinkerer.lib.LibPotions;
import vazkii.tinkerer.potion.ModPotions;

public class ModEnchantmentHandler {

	@ForgeSubscribe
	public void onEntityDamaged(LivingHurtEvent event) {
		if(event.source.getEntity() instanceof EntityLiving) {
			EntityLiving attacker = (EntityLiving) event.source.getEntity();
			ItemStack heldItem = attacker.getHeldItem();

			if(heldItem == null)
				return;

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.freezing, heldItem) > 0)
				event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.effectFrozen.id, 40));

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.wither, heldItem) > 0)
				event.entityLiving.addPotionEffect(new PotionEffect(Potion.wither.id, 40));

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.sync, heldItem) > 0) {
				// TODO
			}
		}
	}

	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onItemBroken(PlayerDestroyItemEvent event) {
		// TODO
	}

	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onEntityUpdate(LivingUpdateEvent event) {
		if(isEntityFrozen(event.entityLiving)) {
			if(event.entityLiving instanceof EntityPlayer && ((EntityPlayer)event.entityLiving).capabilities.isCreativeMode)
				return; // Ignore creative mode

			event.entityLiving.landMovementFactor = 0F;
			event.entityLiving.jumpMovementFactor = 0F;
			event.entityLiving.setJumping(false);
			event.entityLiving.motionY = 0;
		} else if(event.entityLiving.landMovementFactor == 0F && event.entityLiving.jumpMovementFactor == 0F){
			event.entityLiving.landMovementFactor = 0.1F;
			event.entityLiving.jumpMovementFactor = 0.02F;
			// This gets calibrated after, but for some entities (e.g. slimes)
			// it doesn't get calibrated automatically, so they stay
			// in the same place.
		}
	}

	@ForgeSubscribe
	public void onEntityDamage(LivingHurtEvent event) {
		if(isEntityFrozen(event.entityLiving))
			event.entityLiving.removePotionEffect(LibPotions.idFrozen);
	}

	public static boolean isEntityFrozen(EntityLiving entity) {
		return entity.isPotionActive(LibPotions.idFrozen);
	}
}
