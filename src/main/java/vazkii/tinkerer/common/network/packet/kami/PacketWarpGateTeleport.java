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
package vazkii.tinkerer.common.network.packet.kami;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.network.packet.PacketTile;

public class PacketWarpGateTeleport extends PacketTile<TileWarpGate> {

	private static final long serialVersionUID = 2247241734524685744L;
	int index;

	public PacketWarpGateTeleport(TileWarpGate tile, int index) {
		super(tile);
		this.index = index;
	}

	@Override
	public void handle() {
		if(player instanceof EntityPlayer)
			tile.teleportPlayer((EntityPlayer) player, index);
	}

    @Override
    public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        super.encodeInto(channelHandlerContext, byteBuf);
        byteBuf.writeInt(index);
    }

    @Override
    public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        super.decodeInto(channelHandlerContext, byteBuf);
        index=byteBuf.readInt();
    }

}
