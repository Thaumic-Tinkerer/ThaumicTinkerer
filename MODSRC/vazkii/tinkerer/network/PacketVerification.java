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
 * File Created @ [28 Apr 2013, 20:25:08 (GMT)]
 */
package vazkii.tinkerer.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import vazkii.tinkerer.lib.LibNetwork;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketVerification extends ModPacket {

	@Override
	public ByteArrayOutputStream asOutputStream() throws IOException {
		return emptyStream();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean readPayload(Packet250CustomPayload packet, INetworkManager manager, Player player, String subchannel) {
		if(subchannel.equals(getSubchannel())) {
			PacketManager.currentServerVerified = true;
			return true;
		}
		return false;
	}

	@Override
	public String getSubchannel() {
		return LibNetwork.SUBCHANNEL_VERIFY;
	}

}
