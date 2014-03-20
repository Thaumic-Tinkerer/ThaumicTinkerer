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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.tile.TileFunnel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFunnel extends BlockModContainer {

	Icon sideIcon, topIcon;

	Random random;

	protected BlockFunnel() {
		super(Material.rock);
		setHardness(3.0F);
		setResistance(8.0F);
		setStepSound(Block.soundStoneFootstep);
		setBlockBounds(0F, 0F, 0F, 1F, 1F / 8F, 1F);

		random = new Random();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
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
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileFunnel funnel = (TileFunnel) par1World.getBlockTileEntity(par2, par3, par4);

		if (funnel != null) {
			for (int j1 = 0; j1 < funnel.getSizeInventory(); ++j1) {
				ItemStack itemstack = funnel.getStackInSlot(j1);

				if (itemstack != null) {
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem)) {
						int k1 = random.nextInt(21) + 10;

						if (k1 > itemstack.stackSize)
							k1 = itemstack.stackSize;

						itemstack.stackSize -= k1;
						entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float)random.nextGaussian() * f3;
						entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float)random.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
					}
				}
			}

			par1World.func_96440_m(par2, par3, par4, par5);
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		TileFunnel funnel = (TileFunnel) par1World.getBlockTileEntity(par2, par3, par4);
		ItemStack stack = funnel.getStackInSlot(0);

		if(stack == null) {
			ItemStack playerStack = par5EntityPlayer.getCurrentEquippedItem();
			if(funnel.canInsertItem(0, playerStack, 1)) {
				funnel.setInventorySlotContents(0, playerStack.splitStack(1));

				if(playerStack.stackSize <= 0)
					par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);

				funnel.onInventoryChanged();
				return true;
			}
		} else {
			if(!par5EntityPlayer.inventory.addItemStackToInventory(stack))
				par5EntityPlayer.dropPlayerItem(stack);

			funnel.setInventorySlotContents(0, null);
			funnel.onInventoryChanged();
			return true;
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileFunnel();
	}
}
