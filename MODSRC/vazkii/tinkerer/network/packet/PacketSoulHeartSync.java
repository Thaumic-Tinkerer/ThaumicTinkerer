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
 * File Created @ [2 Jul 2013, 17:22:14 (GMT)]
 */
package vazkii.tinkerer.network.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import vazkii.tinkerer.lib.LibNetwork;
import vazkii.tinkerer.network.ModPacket;
import vazkii.tinkerer.util.handler.SoulHeartHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketSoulHeartSync  extends ModPacket {

	int sync;
	
	public PacketSoulHeartSync() { }
	
	public PacketSoulHeartSync(int sync) {
		this.sync = sync;
	}
	
	@Override
	public ByteArrayOutputStream asOutputStream() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(stream);
		writeSubchannel(data);
		data.writeInt(sync);
		return stream;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean readPayload(Packet250CustomPayload packet, INetworkManager manager, Player player, String subchannel) throws IOException {
		if(subchannel.equals(getSubchannel())) {
			ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
			DataInputStream inputStream = new DataInputStream(stream);
			skipSubchannel(inputStream);
			int hp = inputStream.readInt();
			
			SoulHeartHandler.clientPlayerHP = hp;
			
			return true;
		}
		return false;
	}

	@Override
	public String getSubchannel() {
		return LibNetwork.SUBCHANNEL_SOUL_HEART_SYNC;
	}
}
