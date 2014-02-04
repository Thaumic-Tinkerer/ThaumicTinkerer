package vazkii.tinkerer.common.block.mobilizer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.BlockModContainer;
import vazkii.tinkerer.common.block.tile.TileEntityMobilizer;
import vazkii.tinkerer.common.block.tile.TileEntityRelay;

public class BlockMobilizerRelay  extends BlockModContainer<TileEntityRelay> {

	public BlockMobilizerRelay(int par1) {
		super(par1, Material.iron);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}


	@Override
	public TileEntityRelay createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return new TileEntityRelay(world);
	}


}
