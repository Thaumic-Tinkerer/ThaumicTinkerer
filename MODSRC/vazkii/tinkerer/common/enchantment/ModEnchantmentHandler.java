/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [29 Sep 2013, 11:52:00 (GMT)]
 */
package vazkii.tinkerer.common.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import vazkii.tinkerer.common.lib.LibEnchantIDs;
import vazkii.tinkerer.common.lib.LibFeatures;

public class ModEnchantmentHandler {

	@ForgeSubscribe
	public void onEntityDamaged(LivingHurtEvent event) {
		if(event.source.getEntity() instanceof EntityLiving) {
			EntityLiving attacker = (EntityLiving) event.source.getEntity();
			ItemStack heldItem = attacker.getHeldItem();

			if(heldItem == null)
				return;

			int vampirism = EnchantmentHelper.getEnchantmentLevel(LibEnchantIDs.idVampirism, heldItem);
			if(vampirism > 0) {
				attacker.heal((int) Math.ceil(event.ammount / ((3 - vampirism) * 2D)));
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft:zap", 0.6F, 1F);
			}
		}
	}
	
	@ForgeSubscribe
	public void onEntityUpdate(LivingUpdateEvent event) {
		final double min = -0.0784000015258789;
		
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			int ascentBoost = EnchantmentHelper.getMaxEnchantmentLevel(LibEnchantIDs.idAscentBoost, player.inventory.armorInventory);
			int slowfall = EnchantmentHelper.getMaxEnchantmentLevel(LibEnchantIDs.idSlowFall, player.inventory.armorInventory);

			if(slowfall > 0) {
				if(event.entityLiving.isSneaking() && event.entityLiving.motionY < min) {
					event.entityLiving.motionY = Math.max(player.motionY, -((3F - slowfall) / 7.5F));
					event.entityLiving.fallDistance = Math.max(0, player.fallDistance - (slowfall / 3F));

					player.worldObj.spawnParticle("cloud", player.posX + 0.25, player.posY - 1, player.posZ + 0.25, -player.motionX, player.motionY, -player.motionZ);
				}

				if(!event.entityLiving.isSneaking() && event.entityLiving.isAirBorne && event.entityLiving.motionY > 0 && ascentBoost > 0)
					event.entityLiving.moveEntity(event.entityLiving.motionX, event.entityLiving.motionY * (ascentBoost * LibFeatures.MOVEMENT_MODIFIER), event.entityLiving.motionZ);
			}
		}
	}
	
}
