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
 * File Created @ [12 Jul 2013, 22:24:12 (GMT)]
 */
package vazkii.tinkerer.network.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import vazkii.tinkerer.inventory.container.ContainerMobMagnet;
import vazkii.tinkerer.lib.LibNetwork;
import vazkii.tinkerer.network.ModPacket;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.tile.TileEntityMobMagnet;
import cpw.mods.fml.common.network.Player;

public class PacketMobMagnetButton extends ModPacket {

	TileEntityMobMagnet mobMagnet;

	public PacketMobMagnetButton() { }

	public PacketMobMagnetButton(TileEntityMobMagnet mobMagnet) {
		this.mobMagnet = mobMagnet;
	}

	@Override
	public ByteArrayOutputStream asOutputStream() throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(stream);
		writeSubchannel(data);
		data.writeInt(mobMagnet.xCoord);
		data.writeInt(mobMagnet.yCoord);
		data.writeInt(mobMagnet.zCoord);
		data.writeBoolean(mobMagnet.adult);
		return stream;
	}

	@Override
	public boolean readPayload(Packet250CustomPayload packet, INetworkManager manager, Player player, String subchannel) throws IOException {
		if(subchannel.equals(getSubchannel()) && player != null && player instanceof EntityPlayer) {
			EntityPlayer entityPlayer = (EntityPlayer)player;
			if(entityPlayer.openContainer != null && entityPlayer.openContainer instanceof ContainerMobMagnet) {
				ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
				DataInputStream inputStream = new DataInputStream(stream);
				skipSubchannel(inputStream);
				int x = inputStream.readInt();
				int y = inputStream.readInt();
				int z = inputStream.readInt();
				ContainerMobMagnet container = (ContainerMobMagnet)entityPlayer.openContainer;
				if(container.mobMagnet.xCoord == x && container.mobMagnet.yCoord == y && container.mobMagnet.zCoord == z) {
					TileEntityMobMagnet mobMagnet = (TileEntityMobMagnet) container.mobMagnet.worldObj.getBlockTileEntity(x, y, z);
					boolean adult = inputStream.readBoolean();
					mobMagnet.adult = adult;
					PacketMobMagnetSync packet1 = new PacketMobMagnetSync(mobMagnet);
					PacketManager.sendPacketToClient(player, packet1);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String getSubchannel() {
		return LibNetwork.SUBCHANNEL_MOB_MAGNET_BUTTON;
	}

}
