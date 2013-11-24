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
 * File Created @ [Nov 24, 2013, 6:48:04 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.transvector;

import vazkii.tinkerer.common.lib.LibFeatures;

public class TileTransvectorDislocator extends TileTransvector {

	@Override
	public int getMaxDistance() {
		return LibFeatures.DISLOCATOR_DISTANCE;
	}

}
