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
package vazkii.tinkerer.common.network.packet;

import vazkii.tinkerer.common.block.tile.TileEnchanter;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketEnchanterStartWorking extends PacketTile<TileEnchanter> {

	private static final long serialVersionUID = -9086252088394185376L;

	public PacketEnchanterStartWorking(TileEnchanter tile) {
		super(tile);
	}

	@Override
	public void handle() {
		if(!tile.working && !tile.enchantments.isEmpty() && !tile.levels.isEmpty()) {
			tile.working = true;
			PacketDispatcher.sendPacketToAllPlayers(tile.getDescriptionPacket());
		}
	}

}
