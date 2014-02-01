package vazkii.tinkerer.common.block.mobilizer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.tile.TileEntityRelay;

public class BlockMobilizerRelay extends Block {

	public BlockMobilizerRelay(int par1) {
		super(par1, Material.iron);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityRelay(world);
	}


}
