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
 * File Created @ [9 Sep 2013, 17:00:10 (GMT)]
 */
package vazkii.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.core.helper.MiscHelper;

public abstract class PacketTile<T extends TileEntity> implements IMessage {

	public PacketTile() {

	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		byteBuf.writeInt(x);
		byteBuf.writeInt(y);
		byteBuf.writeInt(z);
		byteBuf.writeInt(dim);
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		x = byteBuf.readInt();
		y = byteBuf.readInt();
		z = byteBuf.readInt();
		dim = byteBuf.readInt();
	}

	private static final long serialVersionUID = -1447633008013055477L;

	protected int dim, x, y, z;

	protected transient T tile;
	protected transient EntityPlayer player;

	public PacketTile(T tile) {
		this.tile = tile;

		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		this.dim = tile.getWorldObj().provider.dimensionId;
	}

	public IMessage onMessage(PacketTile message, MessageContext ctx) {
		MinecraftServer server = MiscHelper.server();
		if (ctx.side.isClient())
			message.player = this.getClientPlayer();
		else {
			message.player = ctx.getServerHandler().playerEntity;
		}
		if (server != null) {
			World world = server.worldServerForDimension(message.dim);

			if (world == null) {
				MiscHelper.printCurrentStackTrace("No world found for dimension " + message.dim + "!");
				return null;
			}

			TileEntity tile = world.getTileEntity(message.x, message.y, message.z);
			if (tile != null) {
				message.tile = (T) tile;
			}
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	public static EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
}