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

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
import vazkii.tinkerer.common.lib.LibObfuscation;

import java.util.ArrayList;
import java.util.List;

public class ItemCatAmulet extends ItemMod {

	public ItemCatAmulet() {
		super();
		setMaxStackSize(1);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity e, int par4, boolean par5) {
		int range = 10;
		int rangeY = 4;
		List<EntityLiving> entities = par2World.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(e.posX - range, e.posY - rangeY, e.posZ - range, e.posX + range, e.posY + rangeY, e.posZ + range));

		for(EntityLiving entity : entities) {
            List<EntityAITasks.EntityAITaskEntry> entries = new ArrayList(entity.tasks.taskEntries);
            entries.addAll(new ArrayList(entity.targetTasks.taskEntries));

            boolean avoidsOcelots = false;
            for(EntityAITasks.EntityAITaskEntry entry : entries) {
            	if(entry.action instanceof EntityAIAvoidEntity)
            		avoidsOcelots = messWithRunAwayAI((EntityAIAvoidEntity) entry.action) || avoidsOcelots;

            	if(entry.action instanceof EntityAINearestAttackableTarget)
            		messWithGetTargetAI((EntityAINearestAttackableTarget) entry.action);
            }

            if(entity instanceof EntityCreeper) {
            	ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper) entity, 2, LibObfuscation.TIME_SINCE_IGNITED);
            	entity.setAttackTarget(null);
            }
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

	private boolean messWithRunAwayAI(EntityAIAvoidEntity aiEntry) {
		if(ReflectionHelper.getPrivateValue(EntityAIAvoidEntity.class, aiEntry, LibObfuscation.TARGET_ENTITY_CLASS) == EntityOcelot.class) {
			ReflectionHelper.setPrivateValue(EntityAIAvoidEntity.class, aiEntry, EntityPlayer.class, LibObfuscation.TARGET_ENTITY_CLASS);
			return true;
		}
		return false;
	}

	private void messWithGetTargetAI(EntityAINearestAttackableTarget aiEntry) {
		if(ReflectionHelper.getPrivateValue(EntityAINearestAttackableTarget.class, aiEntry, LibObfuscation.TARGET_CLASS) == EntityPlayer.class)
			ReflectionHelper.setPrivateValue(EntityAINearestAttackableTarget.class, aiEntry, EntityEnderCrystal.class, LibObfuscation.TARGET_CLASS); // Something random that won't be around
	}
}
