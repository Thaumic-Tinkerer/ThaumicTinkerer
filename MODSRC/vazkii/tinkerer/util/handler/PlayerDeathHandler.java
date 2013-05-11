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
 * File Created @ [11 May 2013, 22:37:43 (GMT)]
 */
package vazkii.tinkerer.util.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import vazkii.tinkerer.entity.EntityDeathRune;
import vazkii.tinkerer.item.ModItems;

public final class PlayerDeathHandler {

	@ForgeSubscribe
	public void onEntityDeath(LivingDeathEvent event) {
		if(event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if(player.inventory.consumeInventoryItem(ModItems.deathRune.itemID)) {
				EntityDeathRune rune = new EntityDeathRune(player);
				rune.setPosition(player.posX, player.posY, player.posZ);
				player.worldObj.spawnEntityInWorld(rune);
			}
		}
	}
	
}
