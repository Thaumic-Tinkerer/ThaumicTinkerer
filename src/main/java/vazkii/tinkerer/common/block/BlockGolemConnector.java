package vazkii.tinkerer.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.tile.TileCamo;
import vazkii.tinkerer.common.block.tile.TileGolemConnector;

public class BlockGolemConnector extends BlockCamo {

	
	
	protected BlockGolemConnector() {
		super(Material.wood);
	}

	@Override
	public TileCamo createNewTileEntity(World world, int meta) {
		// TODO Auto-generated method stub
		return new TileGolemConnector();
	}
	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

}