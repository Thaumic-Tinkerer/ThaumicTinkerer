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
 * File Created @ [19 May 2013, 15:15:51 (GMT)]
 */
package vazkii.tinkerer.util.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import vazkii.tinkerer.item.ItemGoliathLegs;
import vazkii.tinkerer.lib.LibFeatures;

public final class PlayerDamageHandler {

	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onPlayerDamage(LivingHurtEvent event) {
		if(!(event.entityLiving instanceof EntityPlayer))
			return;

		EntityPlayer player = (EntityPlayer) event.entityLiving;

		if(player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem() instanceof ItemGoliathLegs && event.ammount > 0) {
			event.ammount -= 1;

			int health = player.getHealth();
			if(health > LibFeatures.GOLIATH_LEGS_MAX_SAFETY && event.ammount >= health && event.source.isUnblockable()) {
				event.ammount = health - 1;

				if(!player.worldObj.isRemote)
					player.addChatMessage(EnumChatFormatting.GOLD + "" + EnumChatFormatting.ITALIC + "Your Goliath's Leggings protected you from death.");
			}
		}
	}

}
