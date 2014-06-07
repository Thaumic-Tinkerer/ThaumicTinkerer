package vazkii.tinkerer.common.block.mobilizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.BlockMod;
import vazkii.tinkerer.common.block.tile.TileEntityMobilizer;

public class BlockMobilizer extends BlockMod {

	@Override
	public void onBlockPreDestroy(World par1World, int par2, int par3,
	                              int par4, int par5) {
		TileEntity tile = par1World.getTileEntity(par2, par3, par4);
		if (tile != null && tile instanceof TileEntityMobilizer) {

			((TileEntityMobilizer) tile).dead = true;
		}
		super.onBlockPreDestroy(par1World, par2, par3, par4, par5);
	}

	public BlockMobilizer() {
		super(Material.iron);
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityMobilizer();
	}

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;
	@SideOnly(Side.CLIENT)
	private IIcon iconSide;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		iconBottom = IconHelper.forBlock(iconRegister, this, 0);
		iconTop = IconHelper.forBlock(iconRegister, this, 1);
		iconSide = IconHelper.forBlock(iconRegister, this, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int meta) {
		return par1 == ForgeDirection.UP.ordinal() ? iconTop : par1 == ForgeDirection.DOWN.ordinal() ? iconBottom : iconSide;
	}
}
