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
 * File Created @ [28 Apr 2013, 20:05:24 (GMT)]
 */
package vazkii.tinkerer.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import vazkii.tinkerer.lib.LibNetwork;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketManager implements IPacketHandler {

	public static boolean currentServerVerified = false;

	public static Set<ModPacket> packetHandlers = new LinkedHashSet();


	@Override
	public void onPacketData(INetworkManager handler, Packet250CustomPayload packet, Player player) {
		ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
		DataInputStream inputStream = new DataInputStream(stream);
		try {
			String subchannel = inputStream.readUTF();
			for(ModPacket packetHandler : packetHandlers) {
				if(packetHandler.readPayload(packet, handler, player, subchannel)) // If the packet handler read the packet, stops execution
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendPacketToClient(Player player, ModPacket packet) {
		Packet250CustomPayload payload = generatePacket(packet);
		PacketDispatcher.sendPacketToPlayer(payload, player);
	}

	public static void sendPacketToAllClients(ModPacket packet) {
		Packet250CustomPayload payload = generatePacket(packet);
		PacketDispatcher.sendPacketToAllPlayers(payload);
	}

	// Sends a packet to all players in range of the area pssed in
	public static void sendPacketToClientsInRange(ModPacket packet, World world, double x, double y, double z, float range) {
		Packet250CustomPayload payload = generatePacket(packet);
		List<EntityPlayerMP> players = world.getEntitiesWithinAABB(EntityPlayerMP.class, AxisAlignedBB.getBoundingBox(x-range, y-range, z-range, x+range, y+range, z+range));
		for(EntityPlayerMP player : players)
			PacketDispatcher.sendPacketToPlayer(payload, (Player)player);
	}

	public static void sendPacketToServer(ModPacket packet) {
		if(!currentServerVerified)
			return;

		try {
			ByteArrayOutputStream outStream = packet.asOutputStream();
			Packet250CustomPayload payload = signPacket(outStream, packet);
			PacketDispatcher.sendPacketToServer(payload);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/** Generates a packet **/
	public static Packet250CustomPayload generatePacket(ModPacket packet) {
		try {
			ByteArrayOutputStream outStream = packet.asOutputStream();
			Packet250CustomPayload payload = signPacket(outStream, packet);
			return payload;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** Signs a packet, setting the channel, data and size **/
	public static Packet250CustomPayload signPacket(ByteArrayOutputStream outStream, ModPacket packet) {
		Packet250CustomPayload payload = new Packet250CustomPayload();
		payload.data = outStream.toByteArray();
		payload.length = payload.data.length;
		payload.channel = LibNetwork.PACKET_CHANNEL;
		return payload;
	}

	/** Writes an ItemStack onto a DataOutputStream but
	 * it write the Stack Tag Compound. **/
	public static void writeItemStackIntoStream(ItemStack stack, DataOutputStream stream) throws IOException {
		if(stack != null) {
			stream.writeShort(stack.itemID);
			stream.writeByte(stack.stackSize);
			stream.writeShort(stack.getItemDamage());
		} else {
			stream.writeShort(-1); // If the itemstack is null, write it regardless
		}
	}

	/** Gets an ItemStack from a DataInputStream. **/
	public static ItemStack getItemStackFromStream(DataInputStream stream) throws IOException {
		short id = stream.readShort();
		if(id == -1) // Handle null item stacks
			return null;

		byte size = stream.readByte();
		short dmg = stream.readShort();
		return new ItemStack(id, size, dmg);
	}

}
