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
 * File Created @ [9 Sep 2013, 16:58:55 (GMT)]
 */
package vazkii.tinkerer.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import vazkii.tinkerer.common.lib.LibMisc;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public final class PacketManager implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.data);
			ObjectInputStream objStream = new ObjectInputStream(byteStream);
			IPacket ipacket = (IPacket) objStream.readObject();

			ipacket.handle(manager, player);
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	public static Packet250CustomPayload buildPacket(IPacket ipacket) {
		try {
			Packet250CustomPayload payload = new Packet250CustomPayload();
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream objStream = new ObjectOutputStream(byteStream);

			objStream.writeObject(ipacket);

			payload.channel = LibMisc.NETWORK_CHANNEL;
			payload.data = byteStream.toByteArray();
			payload.length = payload.data.length;

			return payload;
		} catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}