package vazkii.tinkerer.common.block;

import java.util.UUID;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import thaumcraft.common.entities.golems.EntityGolemBase;
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