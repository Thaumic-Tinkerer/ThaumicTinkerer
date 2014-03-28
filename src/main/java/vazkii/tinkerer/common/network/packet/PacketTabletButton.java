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

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;

public class PacketTabletButton extends PacketTile<TileAnimationTablet> {

	private static final long serialVersionUID = -6507755382924554527L;
	boolean leftClick, redstone;

    public PacketTabletButton(){
        super();
    }

	public PacketTabletButton(TileAnimationTablet tile) {
		super(tile);
		leftClick = tile.leftClick;
		redstone = tile.redstone;
	}

	@Override
	public void handle() {
		tile.leftClick = leftClick;
		tile.redstone = redstone;
	}

    @Override
    public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        super.encodeInto(channelHandlerContext, byteBuf);
        byteBuf.writeBoolean(leftClick);
        byteBuf.writeBoolean(redstone);
    }

    @Override
    public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        super.decodeInto(channelHandlerContext, byteBuf);
        leftClick=byteBuf.readBoolean();
        redstone=byteBuf.readBoolean();
    }

}
