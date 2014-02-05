package vazkii.tinkerer.common.block.mobilizer;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.BlockMod;
import vazkii.tinkerer.common.block.tile.TileEntityMobilizer;
import vazkii.tinkerer.common.lib.LibMisc;

public class BlockMobilizer extends BlockMod {

	public BlockMobilizer(int par1) {
		super(par1, Material.iron);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityMobilizer(world);
	}

	private Icon top;
	private Icon bottom;
	private Icon side;

	@Override
	public void registerIcons(IconRegister iconRegister) {
		top=iconRegister.registerIcon(LibMisc.MOD_ID+":mobilizer_top");
		bottom=iconRegister.registerIcon(LibMisc.MOD_ID+":mobilizer_bottom");
		side=iconRegister.registerIcon(LibMisc.MOD_ID+":mobilizer_side");
	}

	@Override
	public Icon getIcon(int s, int meta) {
		switch(s){
			case 0:
				return bottom;
			case 1:
				return top;
			default:
				return side;
		}
	}
}
