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
 * File Created @ [12 Sep 2013, 17:01:27 (GMT)]
 */
package vazkii.tinkerer.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.client.lib.LibRenderIDs;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.tile.TileMagnet;
import vazkii.tinkerer.common.block.tile.TileMobMagnet;
import vazkii.tinkerer.common.lib.LibGuiIDs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMagnet extends BlockModContainer {

	Random random;

	public BlockMagnet() {
		super(Material.iron);
		setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 1F / 16F * 2F, 0.9375F);
		setHardness(1.7F);
		setResistance(1F);
		setStepSound(Block.soundTypeWood);

		random = new Random();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(par5EntityPlayer.getCurrentEquippedItem() != null) {
			if(par5EntityPlayer.getCurrentEquippedItem().getItem() instanceof ItemWandCasting) {
				TileEntity tile = par1World.getTileEntity(par2, par3, par4);
				if(tile != null && tile instanceof TileMobMagnet) {
					par5EntityPlayer.openGui(ThaumicTinkerer.instance, LibGuiIDs.GUI_ID_MOB_MAGNET, par1World, par2, par3, par4);
					if(!par1World.isRemote) {
						par1World.playSoundEffect(par2, par3, par4, "thaumcraft:key", 1F, 0.5F);
					}
				}
				return true;
			}
		}

		int meta = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, (meta & 1) == 0 ? meta + 1 : meta - 1, 2);
		if(!par1World.isRemote) {
			par1World.playSoundEffect(par2, par3, par4, "random.click", 1F, 0.5F);
		}
		return true;
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileMagnet magnet = (TileMagnet) par1World.getTileEntity(par2, par3, par4);
		TileMobMagnet mobMagnet = magnet instanceof TileMobMagnet ? (TileMobMagnet) magnet : null;

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
						entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float)random.nextGaussian() * f3;
						entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float)random.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
					}
				}
			}

			par1World.func_147453_f(par2, par3, par4, par5);
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public int damageDropped(int par1) {
		switch(par1) {
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 1;
		default:
			return 0;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		super.getSubBlocks(par1, par2CreativeTabs, par3List);
		par3List.add(new ItemStack(par1, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return Block.getBlockFromName("log").getIcon(par1, 1);
	}

	@Override
	public int getRenderType() {
		return LibRenderIDs.idMagnet;
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
	public TileEntity createNewTileEntity(World world, int metadata) {
		return (metadata & 2) == 2 ? new TileMobMagnet() : new TileMagnet();
	}
}
