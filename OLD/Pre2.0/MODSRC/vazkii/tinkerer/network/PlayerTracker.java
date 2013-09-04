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
 * File Created @ [28 Apr 2013, 20:30:53 (GMT)]
 */
package vazkii.tinkerer.network;

import net.minecraft.entity.player.EntityPlayer;
import vazkii.tinkerer.network.packet.PacketVerification;
import vazkii.tinkerer.util.handler.SoulHeartHandler;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.Player;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		PacketManager.sendPacketToClient((Player) player, new PacketVerification());
		SoulHeartHandler.updateClient(player);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		// NO-OP
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// NO-OP
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// NO-OP
	}

}
