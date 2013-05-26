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
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
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

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.freezing, heldItem) > 0) {
				event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.effectFrozen.id, 40));
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft.ice", 0.6F, 1F);
			}

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.soulbringer, heldItem) > 0) {
				// TODO
			}

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.vampirism, heldItem) > 0) {
				attacker.heal((int) Math.ceil(event.ammount / 4D));
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft.zap", 0.6F, 1F);
			}
		}
	}

	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onItemBroken(PlayerDestroyItemEvent event) {
		if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.ashes, event.original) > 0) {
			event.original.setItemDamage(0);
			NBTTagList list = event.original.getEnchantmentTagList();
			int remLoc = -1;
			for(int i = 0; i < list.tagCount(); i++) {
				short id = ((NBTTagCompound) list.tagAt(i)).getShort("id");
				if(id == LibEnchantmentIDs.ashes) {
					remLoc = i;
					break;
				}
			}

			if(remLoc >= 0)
				list.removeTag(remLoc);

			for(int i = 0; list.tagCount() > 0 && i < 2; i++) {
				int loc = event.entity.worldObj.rand.nextInt(list.tagCount());
				list.removeTag(loc);
			}

			if(list.tagCount() == 0)
				event.original.getTagCompound().removeTag("ench");

			event.original.stackSize++;
			event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem] = event.original.copy();

			if(!event.entityPlayer.worldObj.isRemote)
				event.entityPlayer.addChatMessage(EnumChatFormatting.GOLD + "" + EnumChatFormatting.ITALIC + "Your " + event.original.getDisplayName() + " was reborn from the Ashes.");
			event.entityPlayer.worldObj.playSoundAtEntity(event.entityPlayer, "thaumcraft.fireloop", 0.6F, 1F);
		}
	}

	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onEntityUpdate(LivingUpdateEvent event) {
		if(isEntityFrozen(event.entityLiving)) {
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
		if(isEntityFrozen(event.entityLiving) && event.source.isFireDamage())
			event.entityLiving.removePotionEffect(LibPotions.idFrozen);
	}

	public static boolean isEntityFrozen(EntityLiving entity) {
		return entity.isPotionActive(LibPotions.idFrozen);
	}
}
