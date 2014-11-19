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
 * File Created @ [16 Sep 2013, 21:49:24 (GMT)]
 */
package thaumic.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import thaumic.tinkerer.common.block.tile.TileEnchanter;

public class PacketEnchanterStartWorking extends PacketTile<TileEnchanter> implements IMessageHandler<PacketEnchanterStartWorking, IMessage> {

    private static final long serialVersionUID = -9086252088394185376L;

    public PacketEnchanterStartWorking() {
        super();
    }

    public PacketEnchanterStartWorking(TileEnchanter tile) {
        super(tile);
    }

    public void handle() {

    }

    @Override
    public IMessage onMessage(PacketEnchanterStartWorking message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (!ctx.side.isServer())
            throw new IllegalStateException("received PacketEnchanterStartWorking " + message + "on client side!");
        if (!message.tile.working && !message.tile.enchantments.isEmpty() && !message.tile.levels.isEmpty()) {
            message.tile.working = true;
            message.tile.getWorldObj().markBlockForUpdate(message.tile.xCoord, message.tile.yCoord, message.tile.zCoord);
        }
        return null;
    }
}
