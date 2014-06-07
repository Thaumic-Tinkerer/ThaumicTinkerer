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
 * File Created @ [Dec 27, 2013, 1:03:05 AM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.armor;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import thaumcraft.codechicken.lib.vec.Vector3;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.ItemBrightNitor;
import vazkii.tinkerer.common.item.ModItems;

public class ItemGemLegs extends ItemIchorclothArmorAdv {

	public ItemGemLegs(int par2) {
		super(par2);

	}

	@Override
	boolean ticks() {
		return true;
	}

	@Override
	void tickPlayer(EntityPlayer player) {
		ItemStack armor = player.getCurrentArmor(1);
		if (armor.getItemDamage() == 1 || !ThaumicTinkerer.proxy.armorStatus(player))
			return;

		ItemBrightNitor.meta = 1;
		ModItems.brightNitor.onUpdate(null, player.worldObj, player, 0, false);
		ItemBrightNitor.meta = 0;

		int x = (int) Math.floor(player.posX);
		int y = (int) player.posY + 1;
		int z = (int) Math.floor(player.posZ);

		float yaw = MathHelper.wrapAngleTo180_float(player.rotationYaw + 90F) * (float) Math.PI / 180F;
		Vector3 lookVector = new Vector3(Math.cos(yaw), Math.sin(yaw), 0).normalize();
		Vector3 newVector = new Vector3(lookVector.x, lookVector.y, 0);

		for (int i = 0; i < 5; i++) {
			newVector = newVector.add(lookVector);

			int x1 = x + (int) newVector.x;
			int z1 = z + (int) newVector.y;
			ItemBrightNitor.setBlock(x1, y, z1, player.worldObj);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onDamageTaken(LivingHurtEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() == this && event.source.isFireDamage() && ThaumicTinkerer.proxy.armorStatus(player)) {
				event.setCanceled(true);
				player.heal(event.ammount);
			}
		}
	}
}
