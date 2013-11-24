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

import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvector;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorDislocator;
import cpw.mods.fml.common.network.PacketDispatcher;

public class BlockTransvectorDislocator extends BlockTransvector {

	Icon[] icons = new Icon[2];
	
	public BlockTransvectorDislocator(int par1) {
		super(par1, Material.iron);
        setHardness(3F);
        setResistance(10F);
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		icons[0] = IconHelper.forBlock(par1IconRegister, this, 0);
		icons[1] = IconHelper.forBlock(par1IconRegister, this, 1);
	}
	
	@Override
	public Icon getIconFromSideAfterCheck(TileEntity tile, int meta, int side) {
		return icons[((TileTransvectorDislocator) tile).orientation == side ? 1 : 0];
	}
	
	@Override
	public Icon getIcon(int par1, int par2) {
		return icons[0];
	}
	
	@Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		int orientation = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, par5EntityLivingBase);
		((TileTransvectorDislocator) tile).orientation = orientation;
		PacketDispatcher.sendPacketToAllInDimension(tile.getDescriptionPacket(), par1World.provider.dimensionId);
    }
	
	@Override
	public TileTransvector createNewTileEntity(World world) {
		return new TileTransvectorDislocator();
	}
	
}
