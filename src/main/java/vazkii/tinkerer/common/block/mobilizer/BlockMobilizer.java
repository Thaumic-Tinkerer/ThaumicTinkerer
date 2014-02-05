package vazkii.tinkerer.common.block.mobilizer;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.BlockMod;
import vazkii.tinkerer.common.block.tile.TileEntityMobilizer;
import vazkii.tinkerer.common.lib.LibMisc;

public class BlockMobilizer extends BlockMod {

	public BlockMobilizer(int par1) {
		super(par1, Material.iron);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		this.setLightOpacity(255);
	}

	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3,
			int par4, AxisAlignedBB par5AxisAlignedBB, List par6List,
			Entity par7Entity) {
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}
	
	@Override
	public boolean isOpaqueCube()
    {
		return false;
    }

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}



	@Override
	@SideOnly(Side.CLIENT)
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess,
			int par2, int par3, int par4, int par5) {
		// TODO Auto-generated method stub
		return super.shouldSideBeRendered(par1iBlockAccess, par2, par3, par4, par5);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityMobilizer(world);
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
