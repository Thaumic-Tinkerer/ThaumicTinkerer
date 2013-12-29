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
 * File Created @ [Dec 29, 2013, 9:41:05 PM (GMT)]
 */
package vazkii.tinkerer.common.network.packet.kami;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;
import vazkii.tinkerer.client.core.handler.kami.SoulHeartClientHandler;
import vazkii.tinkerer.common.network.IPacket;

public class PacketSoulHearts implements IPacket {

	int hearts;
	
	public PacketSoulHearts(int hearts) {
		this.hearts = hearts;
	}
	
	@Override
	public void handle(INetworkManager manager, Player player) {
		SoulHeartClientHandler.clientPlayerHP = hearts;
	}

}
