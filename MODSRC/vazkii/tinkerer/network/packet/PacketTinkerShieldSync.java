/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 *
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [15 Aug 2013, 23:45:57 (GMT)]
 */
package vazkii.tinkerer.network.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import vazkii.tinkerer.client.util.handler.TinkerShieldClientHandler;
import vazkii.tinkerer.lib.LibNetwork;
import vazkii.tinkerer.network.ModPacket;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketTinkerShieldSync extends ModPacket {

	int shields;
	
	public PacketTinkerShieldSync() { }

	public PacketTinkerShieldSync(int shield) {
		this.shields = shield;
	}

	@Override
	public ByteArrayOutputStream asOutputStream() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(stream);
		writeSubchannel(data);
		data.writeInt(shields);
		return stream;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean readPayload(Packet250CustomPayload packet, INetworkManager manager, Player player, String subchannel) throws IOException {
		if(subchannel.equals(getSubchannel())) {
			ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
			DataInputStream inputStream = new DataInputStream(stream);
			skipSubchannel(inputStream);
			int shields = inputStream.readInt();

			TinkerShieldClientHandler.clientPlayerShield = shields;

			return true;
		}
		return false;
	}

	@Override
	public String getSubchannel() {
		return LibNetwork.SUBCHANNEL_TINKER_SHIELD_SYNC;
	}
}
