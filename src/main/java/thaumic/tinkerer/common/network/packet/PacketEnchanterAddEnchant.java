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
 * File Created @ [16 Sep 2013, 15:45:54 (GMT)]
 */
package thaumic.tinkerer.common.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import thaumic.tinkerer.common.block.tile.TileEnchanter;
import thaumic.tinkerer.common.enchantment.core.EnchantmentManager;

public class PacketEnchanterAddEnchant extends PacketTile<TileEnchanter> implements IMessageHandler<PacketEnchanterAddEnchant, IMessage> {

    private static final long serialVersionUID = -2182522429849764376L;
    int enchant;
    int level;
    public PacketEnchanterAddEnchant() {
        super();
    }

    public PacketEnchanterAddEnchant(TileEnchanter tile, int enchant, int level) {
        super(tile);
        this.enchant = enchant;
        this.level = level;
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        super.toBytes(byteBuf);
        byteBuf.writeInt(enchant);
        byteBuf.writeInt(level);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        super.fromBytes(byteBuf);
        enchant = byteBuf.readInt();
        level = byteBuf.readInt();
    }

    @Override
    public IMessage onMessage(PacketEnchanterAddEnchant message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (!ctx.side.isServer())
            throw new IllegalStateException("received PacketEnchanterAddEnchant " + message + "on client side!");
        if (message.tile.working)
            return null;

        if (message.level == -1) {
            int index = message.tile.enchantments.indexOf(message.enchant);
            message.tile.removeLevel(index);
            message.tile.removeEnchant(index);
        } else {
            if (!message.tile.enchantments.contains(message.enchant)) {
                if (message.player != null && EnchantmentManager.canApply(message.tile.getStackInSlot(0), Enchantment.enchantmentsList[message.enchant], message.tile.enchantments) && EnchantmentManager.canEnchantmentBeUsed(((EntityPlayer) message.player).getGameProfile().getName(), Enchantment.enchantmentsList[message.enchant])) {
                    message.tile.appendEnchant(message.enchant);
                    message.tile.appendLevel(1);
                }
            } else {
                int maxLevel = Enchantment.enchantmentsList[message.enchant].getMaxLevel();
                message.tile.setLevel(message.tile.enchantments.indexOf(message.enchant), Math.max(1, Math.min(maxLevel, message.level)));
            }
        }
        message.tile.updateAspectList();

        message.tile.getWorldObj().markBlockForUpdate(message.tile.xCoord, message.tile.yCoord, message.tile.zCoord);
        return null;
    }
}
