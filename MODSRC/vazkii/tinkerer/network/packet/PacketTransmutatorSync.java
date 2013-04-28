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
 * File Created @ [28 Apr 2013, 20:50:21 (GMT)]
 */
package vazkii.tinkerer.network.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import vazkii.tinkerer.lib.LibNetwork;
import vazkii.tinkerer.network.ModPacket;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import cpw.mods.fml.common.network.Player;

public class PacketTransmutatorSync extends ModPacket {

	TileEntityTransmutator transmutator;

	public PacketTransmutatorSync() { }

	public PacketTransmutatorSync(TileEntityTransmutator transmutator) {
		this.transmutator = transmutator;
	}

	@Override
	public ByteArrayOutputStream asOutputStream() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(stream);
		writeSubchannel(data);
		data.writeInt(transmutator.xCoord);
		data.writeInt(transmutator.yCoord);
		data.writeInt(transmutator.zCoord);
		PacketManager.writeItemStackIntoStream(transmutator.getStackInSlot(0), data);
		return stream;
	}

	@Override
	public boolean readPayload(Packet250CustomPayload packet, INetworkManager manager, Player player, String subchannel) throws IOException {
		if(subchannel.equals(getSubchannel()) && player != null && player instanceof EntityPlayer) {
			ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
			DataInputStream inputStream = new DataInputStream(stream);
			skipSubchannel(inputStream);
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			TileEntityTransmutator transmutator = (TileEntityTransmutator) ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
			ItemStack stack = PacketManager.getItemStackFromStream(inputStream);
			transmutator.setInventorySlotContents(0, stack);
			return true;
		}
		return false;
	}

	@Override
	public String getSubchannel() {
		return LibNetwork.SUBCHANNEL_TRANSMUTATOR_SYNC;
	}

}
