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
 * File Created @ [27 Apr 2013, 19:17:37 (GMT)]
 */
package vazkii.tinkerer.util.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class EntityInteractionHandler {

	private static EntityPlayer lastInteractingPlayer;

	public static EntityPlayer getLastInteractingPlayer() {
		return lastInteractingPlayer;
	}

	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent event) {
		lastInteractingPlayer = event.entityPlayer;
	}
	
	@ForgeSubscribe(priority = EventPriority.HIGHEST)
	public void onAttack(AttackEntityEvent event) {
		lastInteractingPlayer = event.entityPlayer;
	}

}
