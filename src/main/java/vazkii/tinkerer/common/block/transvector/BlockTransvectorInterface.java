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
 * File Created @ [8 Sep 2013, 18:57:25 (GMT)]
 */
package vazkii.tinkerer.common.block.transvector;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.BlockCamo;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvector;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;

public class BlockTransvectorInterface extends BlockCamo {

	public BlockTransvectorInterface() {
		super(Material.iron);
		setHardness(3F);
		setResistance(10F);
	}

	@Override
	public TileTransvector createNewTileEntity(World var1, int var2) {
		return new TileTransvectorInterface();
	}
}