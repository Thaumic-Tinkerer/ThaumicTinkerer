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
 * File Created @ [28 Sep 2013, 19:33:05 (GMT)]
 */
package vazkii.tinkerer.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.tile.TileFunnel;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFunnel extends BlockModContainer {

	Icon sideIcon, topIcon;
	
	protected BlockFunnel(int par1) {
		super(par1, Material.rock);
		setHardness(3.0F);
		setResistance(8.0F);
		setStepSound(Block.soundStoneFootstep);
		setBlockBounds(0F, 0F, 0F, 1F, 1F / 8F, 1F);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		topIcon = IconHelper.forBlock(par1IconRegister, this, 0);
		sideIcon = IconHelper.forBlock(par1IconRegister, this, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return par1 > 1 ? sideIcon : topIcon;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1, par4 + 1);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return par1World.getBlockId(par2, par3 - 1, par4) == Block.hopperBlock.blockID;
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if(par1World.getBlockId(par2, par3 - 1, par4) != Block.hopperBlock.blockID) {
			dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
			par1World.setBlock(par2, par3, par4, 0);
		}
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		TileFunnel funnel = (TileFunnel) par1World.getBlockTileEntity(par2, par3, par4);
		ItemStack stack = funnel.getStackInSlot(0);
		
		if(stack == null) {
			ItemStack playerStack = par5EntityPlayer.getCurrentEquippedItem();
			if(funnel.canInsertItem(0, playerStack, 1)) {
				funnel.setInventorySlotContents(0, playerStack);
				
				playerStack.stackSize--;
				if(playerStack.stackSize == 0)
					par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);
				PacketDispatcher.sendPacketToAllInDimension(funnel.getDescriptionPacket(), par1World.provider.dimensionId);
				return true;
			}
		} else {
			if(!par5EntityPlayer.inventory.addItemStackToInventory(stack))
				par5EntityPlayer.dropPlayerItem(stack);
			funnel.setInventorySlotContents(0, null);
			PacketDispatcher.sendPacketToAllInDimension(funnel.getDescriptionPacket(), par1World.provider.dimensionId);
			return true;
		}
		
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileFunnel();
	}
}
