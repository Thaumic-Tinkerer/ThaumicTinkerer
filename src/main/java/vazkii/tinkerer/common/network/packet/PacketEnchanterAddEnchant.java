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
package vazkii.tinkerer.common.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import vazkii.tinkerer.common.block.tile.TileEnchanter;
import vazkii.tinkerer.common.enchantment.core.EnchantmentManager;

public class PacketEnchanterAddEnchant extends PacketTile<TileEnchanter> {

    public PacketEnchanterAddEnchant(){
        super();
    }

	private static final long serialVersionUID = -2182522429849764376L;
	int enchant;
	int level;
	public PacketEnchanterAddEnchant(TileEnchanter tile, int enchant, int level) {
		super(tile);
		this.enchant = enchant;
		this.level = level;
	}

    @Override
    public void encodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        super.encodeInto(channelHandlerContext, byteBuf);
        byteBuf.writeInt(enchant);
        byteBuf.writeInt(level);
    }

    @Override
    public void decodeInto(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        super.decodeInto(channelHandlerContext, byteBuf);
        enchant=byteBuf.readInt();
        level=byteBuf.readInt();
    }

    @Override
	public void handle() {
		if(tile.working)
			return;

		if(level == -1) {
			int index = tile.enchantments.indexOf(enchant);
			tile.removeLevel(index);
			tile.removeEnchant(index);
		} else {
			if(!tile.enchantments.contains(enchant)){
				if(player != null && EnchantmentManager.canApply(tile.getStackInSlot(0), Enchantment.enchantmentsList[enchant], tile.enchantments)&& EnchantmentManager.canEnchantmentBeUsed(player.getGameProfile().getName(), Enchantment.enchantmentsList[enchant])) {
					tile.appendEnchant(enchant);
					tile.appendLevel(1);
				}
			} else {
				int maxLevel = Enchantment.enchantmentsList[enchant].getMaxLevel();
				tile.setLevel(tile.enchantments.indexOf(enchant), Math.max(1, Math.min(maxLevel, level)));
			}
		}
		tile.updateAspectList();

        tile.getWorldObj().markBlockForUpdate(tile.xCoord,tile.yCoord,tile.zCoord);
	}
}
