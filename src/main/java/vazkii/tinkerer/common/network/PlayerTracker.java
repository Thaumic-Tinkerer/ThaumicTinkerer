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

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.foci.ItemFocusHeal;
import vazkii.tinkerer.common.item.foci.ItemFocusSmelt;
import vazkii.tinkerer.common.item.kami.armor.ItemGemBoots;
import vazkii.tinkerer.common.item.kami.armor.ItemGemChest;
import cpw.mods.fml.common.IPlayerTracker;
import vazkii.tinkerer.common.item.kami.armor.ItemIchorclothArmorAdv;
import vazkii.tinkerer.common.network.packet.kami.PacketToggleArmor;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		ItemFocusHeal.playerHealData.put(player.username, 0);
        PacketDispatcher.sendPacketToPlayer(PacketManager.buildPacket(new PacketToggleArmor(ThaumicTinkerer.proxy.isOn(player))),(Player)player);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		ItemFocusSmelt.playerData.remove(player.username);
		ItemFocusHeal.playerHealData.remove(player.username);

		ItemGemChest.playersWithFlight.remove(player.username + ":false");
		ItemGemChest.playersWithFlight.remove(player.username + ":true");

		ItemGemBoots.playersWith1Step.remove(player.username);

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) { }

	@Override
	public void onPlayerRespawn(EntityPlayer player) { }

}
