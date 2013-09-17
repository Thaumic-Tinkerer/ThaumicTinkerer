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

import net.minecraft.enchantment.Enchantment;
import cpw.mods.fml.common.network.PacketDispatcher;
import vazkii.tinkerer.common.block.tile.TileEnchanter;

public class PacketEnchanterAddEnchant extends PacketTile<TileEnchanter> {

	int enchant;
	int level;

	public PacketEnchanterAddEnchant(TileEnchanter tile, int enchant, int level) {
		super(tile);
		this.enchant = enchant;
		this.level = level;
	}

	@Override
	public void handle() {
		if(tile.working)
			return;
		
		if(level == -1) {
			int index = tile.enchantments.indexOf((Object) enchant);
			tile.removeLevel(index);
			tile.removeEnchant(index);
		} else {
			if(!tile.enchantments.contains(enchant)) {
				tile.appendEnchant(enchant);
				tile.appendLevel(1);
			} else {
				int maxLevel = Enchantment.enchantmentsList[enchant].getMaxLevel();
				tile.setLevel(tile.enchantments.indexOf((Object) enchant), Math.max(1, Math.min(maxLevel, level)));
			}
		}
		tile.updateAspectList();
		
		PacketDispatcher.sendPacketToAllPlayers(tile.getDescriptionPacket());
	}
}
