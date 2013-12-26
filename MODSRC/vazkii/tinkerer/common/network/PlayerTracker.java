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
 * File Created @ [25 Oct 2013, 21:25:50 (GMT)]
 */
package vazkii.tinkerer.common.network;

import net.minecraft.entity.player.EntityPlayer;
import vazkii.tinkerer.common.item.foci.ItemFocusHeal;
import vazkii.tinkerer.common.item.foci.ItemFocusSmelt;
import vazkii.tinkerer.common.item.kami.ItemIchorclothArmorAdv;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		ItemFocusHeal.playerHealData.put(player.username, 0);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		ItemFocusSmelt.playerData.remove(player.username);
		ItemFocusHeal.playerHealData.remove(player.username);
		
		ItemIchorclothArmorAdv.playersWithFlight.remove(player.username + ":false");
		ItemIchorclothArmorAdv.playersWithFlight.remove(player.username + ":true");

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) { }

	@Override
	public void onPlayerRespawn(EntityPlayer player) { }

}
