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
 * File Created @ [9 Sep 2013, 17:02:18 (GMT)]
 */
package vazkii.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;

public class PacketTabletButton extends PacketTile<TileAnimationTablet> implements IMessageHandler<PacketTabletButton, IMessage> {

	private static final long serialVersionUID = -6507755382924554527L;
	boolean leftClick, redstone;

	public PacketTabletButton() {
		super();
	}

	public PacketTabletButton(TileAnimationTablet tile) {
		super(tile);
		leftClick = tile.leftClick;
		redstone = tile.redstone;
	}

	@Override
	public void toBytes(ByteBuf byteBuf) {
		super.toBytes(byteBuf);
		byteBuf.writeBoolean(leftClick);
		byteBuf.writeBoolean(redstone);
	}

	@Override
	public void fromBytes(ByteBuf byteBuf) {
		super.fromBytes(byteBuf);
		leftClick = byteBuf.readBoolean();
		redstone = byteBuf.readBoolean();
	}

	@Override
	public IMessage onMessage(PacketTabletButton message, MessageContext ctx) {
		super.onMessage(message, ctx);
		if (!ctx.side.isServer())
			throw new IllegalStateException("received PacketTabletbutton " + message + "on client side!");
		message.tile.leftClick = message.leftClick;
		message.tile.redstone = message.redstone;
		return null;
	}
}
