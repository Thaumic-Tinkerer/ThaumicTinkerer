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
package vazkii.tinkerer.common.network.packet;

import vazkii.tinkerer.common.block.tile.TileMobMagnet;

public class PacketMobMagnetButton extends PacketTile<TileMobMagnet> {

	private static final long serialVersionUID = 7613980953987386713L;
	boolean adult;

	public PacketMobMagnetButton(TileMobMagnet tile) {
		super(tile);

		adult = tile.adult;
	}

	@Override
	public void handle() {
		tile.adult = adult;
	}

}
