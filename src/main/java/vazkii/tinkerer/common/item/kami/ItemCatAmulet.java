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
 * File Created @ [Dec 27, 2013, 7:41:50 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.item.ItemMod;

import java.util.ArrayList;
import java.util.List;

public class ItemCatAmulet extends ItemMod implements IBauble {

	public ItemCatAmulet() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity e, int par4, boolean par5) {

	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

	private boolean messWithRunAwayAI(EntityAIAvoidEntity aiEntry) {
		if (aiEntry.targetEntityClass == EntityOcelot.class) {
			aiEntry.targetEntityClass = EntityPlayer.class;
			return true;
		}
		return false;
	}

	private void messWithGetTargetAI(EntityAINearestAttackableTarget aiEntry) {
		if (aiEntry.targetClass == EntityPlayer.class)
			aiEntry.targetClass = EntityEnderCrystal.class; // Something random that won't be around
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		int range = 10;
		int rangeY = 4;
		List<EntityLiving> entities = player.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - rangeY, player.posZ - range, player.posX + range, player.posY + rangeY, player.posZ + range));

		for (EntityLiving entity : entities) {
			List<EntityAITasks.EntityAITaskEntry> entries = new ArrayList(entity.tasks.taskEntries);
			entries.addAll(new ArrayList(entity.targetTasks.taskEntries));

			boolean avoidsOcelots = false;
			for (EntityAITasks.EntityAITaskEntry entry : entries) {
				if (entry.action instanceof EntityAIAvoidEntity)
					avoidsOcelots = messWithRunAwayAI((EntityAIAvoidEntity) entry.action) || avoidsOcelots;

				if (entry.action instanceof EntityAINearestAttackableTarget)
					messWithGetTargetAI((EntityAINearestAttackableTarget) entry.action);
			}

			if (entity instanceof EntityCreeper) {
				((EntityCreeper) entity).timeSinceIgnited = 2;
				entity.setAttackTarget(null);
			}
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
