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
 * File Created @ [Jan 10, 2014, 7:21:05 PM (GMT)]
 */
package thaumic.tinkerer.common.network.packet.kami;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import thaumic.tinkerer.common.block.tile.kami.TileWarpGate;
import thaumic.tinkerer.common.network.packet.PacketTile;

public class PacketWarpGateTeleport extends PacketTile<TileWarpGate> implements IMessageHandler<PacketWarpGateTeleport, IMessage> {

	private static final long serialVersionUID = 2247241734524685744L;
	int index;

	public PacketWarpGateTeleport() {
		super();
	}

	public PacketWarpGateTeleport(TileWarpGate tile, int index) {
		super(tile);
		this.index = index;
	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		super.toBytes(byteBuf);
		byteBuf.writeInt(index);
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		super.fromBytes(byteBuf);
		index = byteBuf.readInt();
	}

	@Override
	public IMessage onMessage(PacketWarpGateTeleport message, MessageContext ctx) {
		super.onMessage(message, ctx);
		if (!ctx.side.isServer())
			throw new IllegalStateException("received PacketTabletbutton " + message + "on client side!");
		if (message.player instanceof EntityPlayer)
			message.tile.teleportPlayer((EntityPlayer) message.player, message.index);
		return null;
	}
}
