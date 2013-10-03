/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [25 May 2013, 22:57:41 (GMT)]
 */
package vazkii.tinkerer.enchantment;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITaskEntry;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
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
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.lib.LibEnchantmentIDs;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibPotions;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketTinkerShieldSync;
import vazkii.tinkerer.potion.ModPotions;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ModEnchantmentHandler {

	private static final String COMPOUND = LibMisc.MOD_ID;
	private static final String TAG_SHIELD = "tinkerShields";

	@ForgeSubscribe
	public void onEntityDamaged(LivingHurtEvent event) {
		if(event.source.getEntity() instanceof EntityLiving) {
			EntityLiving attacker = (EntityLiving) event.source.getEntity();
			ItemStack heldItem = attacker.getHeldItem();

			if(heldItem == null)
				return;

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.freezing, heldItem) > 0) {
				event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.effectFrozen.id, 60));
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft.ice", 0.6F, 1F);
			}

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.soulbringer, heldItem) > 0 && event.entityLiving.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD && !isEntityPossessed(event.entityLiving) && Math.random() < 0.05F) {
				event.entityLiving.addPotionEffect(new PotionEffect(ModPotions.effectPossessed.id, 12000));
				if(attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;
					if(!player.worldObj.isRemote)
						player.addChatMessage(EnumChatFormatting.GOLD + "" + EnumChatFormatting.ITALIC + "The " + event.entityLiving.getEntityName() + " was possessed to join your fight.");
				}
				event.entityLiving.heal(event.entityLiving.getMaxHealth());
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft.wand", 0.6F, 1F);
			}

			if(EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.vampirism, heldItem) > 0) {
				attacker.heal((int) Math.ceil(event.ammount / 4D));
				event.entityLiving.worldObj.playSoundAtEntity(event.entityLiving, "thaumcraft.zap", 0.6F, 1F);
			}
		}

		// Stone Skin Enchantment
		if(event.entityLiving instanceof EntityPlayer) {

			if(event.source.canHarmInCreative()) {
				return;
			}
			else {
				ItemStack[] armor = event.entityLiving.getLastActiveItems();
				for(ItemStack stack : armor) {
					int lvl = EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.stoneskin, stack);
					if(lvl > 0) {
						float base = (6 + lvl * lvl) / 3.0F * 0.75F;

						if(AuraManager.decreaseClosestAura(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posY, lvl)) {
							event.ammount -= base;
						}
					}
				}
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

			ThaumicTinkerer.proxy.sanityCheckedFrozenParticles(event.entityLiving);
		} else if(event.entityLiving.landMovementFactor == 0F && event.entityLiving.jumpMovementFactor == 0F){
			event.entityLiving.landMovementFactor = 0.1F;
			event.entityLiving.jumpMovementFactor = 0.02F;
			// This gets calibrated after, but for some entities (e.g. slimes)
			// it doesn't get calibrated automatically, so they stay
			// in the same place.
		}

		if(isEntityPossessed(event.entityLiving)) {
			List<EntityAITaskEntry> entries = new ArrayList(event.entityLiving.tasks.taskEntries);
			entries.addAll(new ArrayList(event.entityLiving.targetTasks.taskEntries));

			for(EntityAITaskEntry entry : entries) {
				if(entry.action instanceof EntityAIAttackOnCollide)
					messWithAttackAI((EntityAIAttackOnCollide) entry.action);
				else if(entry.action instanceof EntityAINearestAttackableTarget)
					messWithFollowAI((EntityAINearestAttackableTarget) entry.action);
				else if(entry.action instanceof EntityAIWatchClosest)
					messWithWatchAI((EntityAIWatchClosest) entry.action);
			}
			if(event.entityLiving.getAttackTarget() instanceof EntityPlayer)
				event.entityLiving.setAttackTarget(null);

			ThaumicTinkerer.proxy.sanityCheckedPossessedParticles(event.entityLiving);
		}

		if(event.entityLiving instanceof EntityPlayer) {
			// Motion Enchantments
			int ascentBoost = EnchantmentHelper.getMaxEnchantmentLevel(LibEnchantmentIDs.ascentboost, event.entityLiving.getLastActiveItems());
			int slowfall = EnchantmentHelper.getMaxEnchantmentLevel(LibEnchantmentIDs.slowfall, event.entityLiving.getLastActiveItems());

			if(ascentBoost > 0 || slowfall > 0) {
				if(!event.entityLiving.isSneaking()) {
					if(event.entityLiving.motionY < 0) {
						event.entityLiving.motionY /= 1 + slowfall * LibMisc.MOVEMENT_MODIFIER;
						event.entityLiving.fallDistance = 0;
					}
				}

				if(event.entityLiving.isAirBorne && event.entityLiving.motionY > 0 && ascentBoost > 0) {
					event.entityLiving.moveEntity(event.entityLiving.motionX, event.entityLiving.motionY * (ascentBoost * LibMisc.MOVEMENT_MODIFIER), event.entityLiving.motionZ);
				}
			}

			// Stone Skin Enchantment
			int stoneskinLevel = 0;
			ItemStack[] armor = event.entityLiving.getLastActiveItems();
			for(ItemStack stack : armor) {
				stoneskinLevel += EnchantmentHelper.getEnchantmentLevel(LibEnchantmentIDs.stoneskin, stack);
			}

			NBTTagCompound cmp = getCompoundToSet((EntityPlayer) event.entityLiving);
			cmp.setInteger(TAG_SHIELD, stoneskinLevel);
			PacketManager.sendPacketToClient((Player) event.entityLiving, new PacketTinkerShieldSync(stoneskinLevel));
		}
	}

	private void messWithAttackAI(EntityAIAttackOnCollide aiEntry) {
		// EntityAIAttackOnCollide.classTargets
		ReflectionHelper.setPrivateValue(EntityAIAttackOnCollide.class, aiEntry, EntityMob.class, 7);
	}

	private void messWithFollowAI(EntityAINearestAttackableTarget aiEntry) {
		// EntityAINearestAttackableTarget.classTarget
		ReflectionHelper.setPrivateValue(EntityAINearestAttackableTarget.class, aiEntry, EntityMob.class, 1);
	}

	public void messWithWatchAI(EntityAIWatchClosest aiEntry) {
		// EntityAIWatchClosest.watchedClass
		ReflectionHelper.setPrivateValue(EntityAIWatchClosest.class, aiEntry, EntityMob.class, 5);
	}

	@ForgeSubscribe
	public void onEntityDamage(LivingHurtEvent event) {
		if(isEntityFrozen(event.entityLiving) && event.source.isFireDamage())
			event.entityLiving.removePotionEffect(LibPotions.idFrozen);
	}

	public static boolean isEntityFrozen(EntityLiving entity) {
		return entity.isPotionActive(LibPotions.idFrozen);
	}

	public static boolean isEntityPossessed(EntityLiving entity) {
		return entity.isPotionActive(LibPotions.idPossessed);
	}

	private static NBTTagCompound getCompoundToSet(EntityPlayer player) {
		NBTTagCompound cmp = player.getEntityData();
		if(!cmp.hasKey(COMPOUND))
			cmp.setCompoundTag(COMPOUND, new NBTTagCompound());

		return cmp.getCompoundTag(COMPOUND);
	}
}
