/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 * 
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [6 Jul 2013, 15:16:20 (GMT)]
 */
package vazkii.tinkerer.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.common.Config;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.lib.LibGuiIDs;
import vazkii.tinkerer.lib.LibRenderIDs;
import vazkii.tinkerer.tile.TileEntityMobMagnet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMobMagnet extends BlockModContainer {

	Random random;

	public BlockMobMagnet(int par1) {
		super(par1, Material.iron);
		setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 1F / 16F * 2F, 0.9375F);
		setHardness(1.7F);
		setResistance(1F);
		setStepSound(soundWoodFootstep);

		random = new Random();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(par5EntityPlayer.isSneaking()) {
			if(!par1World.isRemote) {
				TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
				if(tile != null) {
					par5EntityPlayer.openGui(ThaumicTinkerer.modInstance, LibGuiIDs.ID_MOB_MAGNET, par1World, par2, par3, par4);
				}
			}
		}
		else {
			int meta = par1World.getBlockMetadata(par2, par3, par4);
			par1World.setBlockMetadataWithNotify(par2, par3, par4, meta == 0 ? 1 : 0, 2);
			if(!par1World.isRemote)
				par1World.playSoundEffect(par2, par3, par4, "random.click", 1F, 0.5F);
		}
		return true;
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		TileEntityMobMagnet mobMagnet = (TileEntityMobMagnet) par1World.getBlockTileEntity(par2, par3, par4);

		if (mobMagnet != null) {
			for (int j1 = 0; j1 < mobMagnet.getSizeInventory(); ++j1) {
				ItemStack itemstack = mobMagnet.getStackInSlot(j1);

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
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return Config.blockWooden.getIcon(par1, 0);
	}

	@Override
	public int getRenderType() {
		return LibRenderIDs.idMobMagnet;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMobMagnet();
	}
}