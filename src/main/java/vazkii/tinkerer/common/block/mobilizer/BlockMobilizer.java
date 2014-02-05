package vazkii.tinkerer.common.block.mobilizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.BlockMod;
import vazkii.tinkerer.common.block.tile.TileEntityMobilizer;
import vazkii.tinkerer.common.lib.LibMisc;

public class BlockMobilizer extends BlockMod {



	@Override
	public void onBlockPreDestroy(World par1World, int par2, int par3,
			int par4, int par5) {
		System.out.println(1);
		TileEntity tile=par1World.getBlockTileEntity(par2, par3, par4);
		if(tile!=null&& tile instanceof TileEntityMobilizer)
		{
			System.out.println(2);
		
			((TileEntityMobilizer)tile).dead=true;
		}
		super.onBlockPreDestroy(par1World, par2, par3, par4, par5);
	}

	public BlockMobilizer(int par1) {
		super(par1, Material.iron);
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
	private Icon iconTop;
	@SideOnly(Side.CLIENT)
	private Icon iconBottom;
	@SideOnly(Side.CLIENT)
	private Icon iconSide;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconBottom = IconHelper.forBlock(iconRegister, this, 0);
		iconTop = IconHelper.forBlock(iconRegister, this, 1);
		iconSide = IconHelper.forBlock(iconRegister, this, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int meta) {
		return par1 == ForgeDirection.UP.ordinal() ? iconTop : par1 == ForgeDirection.DOWN.ordinal() ? iconBottom : iconSide;
	}
}
