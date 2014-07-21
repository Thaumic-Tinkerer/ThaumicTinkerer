package thaumic.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.TileRPlacer;

/**
 * Created by nekosune on 30/06/14.
 */
public class PacketPlacerButton extends PacketTile<TileRPlacer> implements IMessageHandler<PacketPlacerButton, IMessage> {

    public int blocks;

    public PacketPlacerButton() {
        super();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        super.toBytes(byteBuf);
        byteBuf.writeInt(blocks);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        super.fromBytes(byteBuf);
        blocks=byteBuf.readInt();
    }

    public PacketPlacerButton(TileRPlacer tile) {
        super(tile);
        blocks=tile.blocks;
    }
    @Override
    public IMessage onMessage(PacketPlacerButton message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (!ctx.side.isServer())
            throw new IllegalStateException("received PacketPlacerbutton " + message + "on client side!");
        message.tile.blocks= message.blocks;
        return null;
    }
}
