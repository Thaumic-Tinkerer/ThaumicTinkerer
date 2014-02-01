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

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
		if(par1World.getBlockTileEntity(par2, par3, par4) instanceof  TileEntityRelay){
			((TileEntityRelay) par1World.getBlockTileEntity(par2, par3, par4)).checkForPartner();
		}
		return 0;
	}
}
