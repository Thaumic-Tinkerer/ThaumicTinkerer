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
 * File Created @ [12 Sep 2013, 18:44:52 (GMT)]
 */
package thaumic.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import thaumic.tinkerer.common.block.tile.TileMobMagnet;

public class PacketMobMagnetButton extends PacketTile<TileMobMagnet> implements IMessageHandler<PacketMobMagnetButton, IMessage> {

    private static final long serialVersionUID = 7613980953987386713L;
    public boolean adult;

    public PacketMobMagnetButton() {
        super();
    }

    public PacketMobMagnetButton(TileMobMagnet tile) {
        super(tile);

        adult = tile.adult;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        super.toBytes(byteBuf);
        byteBuf.writeBoolean(adult);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        super.fromBytes(byteBuf);
        adult = byteBuf.readBoolean();
    }

    @Override
    public IMessage onMessage(PacketMobMagnetButton message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (!ctx.side.isServer())
            throw new IllegalStateException("received PacketTabletbutton " + message + "on client side!");
        message.tile.adult = message.adult;
        return null;
    }
}
