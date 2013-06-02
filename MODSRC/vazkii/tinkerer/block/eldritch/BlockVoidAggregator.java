/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [2 Jun 2013, 19:36:53 (GMT)]
 */
package vazkii.tinkerer.block.eldritch;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.block.BlockModContainer;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.lib.LibGuiIDs;
import vazkii.tinkerer.tile.TileEntityVoidAggregator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVoidAggregator extends BlockModContainer {

	Icon iconBottom;
	Icon iconTop;
	Icon iconSides;

	public BlockVoidAggregator(int par1) {
		super(par1, Material.iron);
		setHardness(Block.obsidian.blockHardness);
        setBlockBounds(0.25F, 0F, 0.25F, 0.75F, 0.3125F, 0.75F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		iconBottom = IconHelper.forBlock(par1IconRegister, this, 0);
		iconTop = IconHelper.forBlock(par1IconRegister, this, 1);
		iconSides = IconHelper.forBlock(par1IconRegister, this, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return par1 == ForgeDirection.UP.ordinal() ? iconTop : par1 == ForgeDirection.DOWN.ordinal() ? iconBottom : iconSides;
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(!par1World.isRemote) {
			TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
			if(tile != null)
				par5EntityPlayer.openGui(ThaumicTinkerer.modInstance, LibGuiIDs.ID_VOID_AGGREGATOR, par1World, par2, par3, par4);
		}

		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityVoidAggregator();
	}
}
