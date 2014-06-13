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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.block.tile.TileFunnel;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.Random;

public class BlockFunnel extends BlockModContainer {

	IIcon sideIcon, topIcon;

	Random random;

	protected BlockFunnel() {
		super(Material.rock);
		setHardness(3.0F);
		setResistance(8.0F);
		setStepSound(Block.soundTypeStone);
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
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		topIcon = IconHelper.forBlock(par1IconRegister, this, 0);
		sideIcon = IconHelper.forBlock(par1IconRegister, this, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return par1 > 1 ? sideIcon : topIcon;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1, par4 + 1);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return par1World.getBlock(par2, par3 - 1, par4) == Block.getBlockFromName("hopper");
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5) {
		if (par1World.getBlock(par2, par3 - 1, par4) != Block.getBlockFromName("hopper")) {
			dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileFunnel funnel = (TileFunnel) par1World.getTileEntity(par2, par3, par4);

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
						entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (float) random.nextGaussian() * f3;
						entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) random.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
					}
				}
			}

			par1World.func_147453_f(par2, par3, par4, par5);
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		TileFunnel funnel = (TileFunnel) par1World.getTileEntity(par2, par3, par4);
		ItemStack stack = funnel.getStackInSlot(0);

		if (stack == null) {
			ItemStack playerStack = par5EntityPlayer.getCurrentEquippedItem();
			if (funnel.canInsertItem(0, playerStack, 1)) {
				funnel.setInventorySlotContents(0, playerStack.splitStack(1));

				if (playerStack.stackSize <= 0)
					par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);
				funnel.markDirty();
				return true;
			}
		} else {
			if (!par5EntityPlayer.inventory.addItemStackToInventory(stack))
				par5EntityPlayer.dropPlayerItemWithRandomChoice(stack, false);

			funnel.setInventorySlotContents(0, null);
			funnel.markDirty();
			return true;
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileFunnel();
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.FUNNEL;
	}

	@Override
	public boolean shouldRegister() {
		return true;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_FUNNEL, new AspectList().add(Aspect.TOOL, 1).add(Aspect.TRAVEL, 2), 0, -7, 1, new ItemStack(this)).setParentsHidden("DISTILESSENTIA").setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed()
				.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_FUNNEL)).setSecondary();

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_FUNNEL, LibResearch.KEY_FUNNEL, new ItemStack(this), new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
				"STS",
				'S', new ItemStack(Blocks.stone),
				'T', new ItemStack(ConfigItems.itemResource, 1, 2));
	}
}
