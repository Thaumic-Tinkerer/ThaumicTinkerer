/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Nov 24, 2013, 10:08:39 PM (GMT)]
 */
package vazkii.tinkerer.common.block.transvector;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.BlockCamo;
import vazkii.tinkerer.common.block.tile.TileCamo;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorDislocator;

import java.util.Random;

public class BlockTransvectorDislocator extends BlockCamo {

	IIcon[] icons = new IIcon[2];

	public BlockTransvectorDislocator() {
		super(Material.iron);
        setHardness(3F);
        setResistance(10F);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        TileEntity tile = par1World.getTileEntity(par2, par3, par4);

    	TileTransvectorDislocator dislocator = (TileTransvectorDislocator) tile;
    	ItemStack currentStack = par5EntityPlayer.getCurrentEquippedItem();

    	if(currentStack != null && currentStack.getItem()== ConfigItems.itemWandCasting) {
    		dislocator.orientation = par6;
			par1World.playSoundEffect(par2, par3, par4, "thaumcraft:tool", 0.6F, 1F);

    		par1World.markBlockForUpdate(par2,par3,par4);

    		return true;
    	}

		return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5) {
		if(par1World.isRemote)
			return;

		boolean power = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		boolean on = meta != 0;

		if (power && !on) {
			par1World.scheduleBlockUpdate(par2, par3, par4, this, tickRate(par1World));
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 4);
		} else if (!power && on)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 4);
	}

	@Override
	public int tickRate(World par1World) {
		return 1;
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		TileEntity tile = par1World.getTileEntity(par2, par3, par4);
		if(tile != null && tile instanceof TileTransvectorDislocator) {
			TileTransvectorDislocator dislocator = (TileTransvectorDislocator) tile;
			dislocator.receiveRedstonePulse();
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		icons[0] = IconHelper.forBlock(par1IconRegister, this, 0);
		icons[1] = IconHelper.forBlock(par1IconRegister, this, 1);
	}

	@Override
	public IIcon getIconFromSideAfterCheck(TileEntity tile, int meta, int side) {
		return icons[((TileTransvectorDislocator) tile).orientation == side ? 1 : 0];
	}

	@Override
	public IIcon getIcon(int par1, int par2) {
		return icons[0];
	}

	@Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		TileEntity tile = par1World.getTileEntity(par2, par3, par4);
		int orientation = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, par5EntityLivingBase);
		((TileTransvectorDislocator) tile).orientation = orientation;
		par1World.markBlockForUpdate(par2,par3,par4);
    }


    @Override
    public TileCamo createNewTileEntity(World world, int var2) {
        return new TileTransvectorDislocator();
    }
}
