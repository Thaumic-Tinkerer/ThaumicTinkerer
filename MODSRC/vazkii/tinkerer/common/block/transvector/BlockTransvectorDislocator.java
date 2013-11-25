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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
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
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);

    	TileTransvectorDislocator dislocator = (TileTransvectorDislocator) tile;
    	ItemStack currentStack = par5EntityPlayer.getCurrentEquippedItem();
        
    	if(currentStack != null && currentStack.itemID == ConfigItems.itemWandCasting.itemID) {
    		dislocator.orientation = par6;
			par1World.playSoundEffect(par2, par3, par4, "thaumcraft:tool", 0.6F, 1F);

    		PacketDispatcher.sendPacketToAllInDimension(tile.getDescriptionPacket(), par1World.provider.dimensionId);

    		return true;
    	}
		
		return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if(par1World.isRemote)
			return;

		boolean power = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) || par1World.isBlockIndirectlyGettingPowered(par2, par3 + 1, par4);
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		boolean on = meta != 0;

		if (power && !on) {
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
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
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if(tile != null && tile instanceof TileTransvectorDislocator) {
			TileTransvectorDislocator dislocator = (TileTransvectorDislocator) tile;
			dislocator.receiveRedstonePulse();
		}
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
