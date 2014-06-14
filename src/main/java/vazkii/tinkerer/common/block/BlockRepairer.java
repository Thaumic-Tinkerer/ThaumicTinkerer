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
 * File Created @ [Nov 30, 2013, 5:36:26 PM (GMT)]
 */
package vazkii.tinkerer.common.block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.client.lib.LibRenderIDs;
import vazkii.tinkerer.common.block.tile.TileRepairer;
import vazkii.tinkerer.common.compat.TinkersConstructCompat;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.Random;

public class BlockRepairer extends BlockModContainer {

	Random random;

	public BlockRepairer() {
		super(Material.iron);
		setHardness(5F);
		setResistance(10F);

		random = new Random();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		TileRepairer repairer = (TileRepairer) par1World.getTileEntity(par2, par3, par4);
		ItemStack stack = repairer.getStackInSlot(0);

		if (stack == null) {
			ItemStack playerStack = par5EntityPlayer.getCurrentEquippedItem();
			if (repairer.canInsertItem(0, playerStack, 1)) {
				repairer.setInventorySlotContents(0, playerStack.splitStack(1));

				if (playerStack.stackSize <= 0)
					par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);

				repairer.markDirty();
				return true;
			}
		} else {
			if (!par5EntityPlayer.inventory.addItemStackToInventory(stack))
				par5EntityPlayer.dropPlayerItemWithRandomChoice(stack, false);

			repairer.setInventorySlotContents(0, null);
			repairer.markDirty();
			return true;
		}

		return false;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
		byte b0 = 0;
		int l1 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l1 == 0)
			b0 = 2;

		if (l1 == 1)
			b0 = 5;

		if (l1 == 2)
			b0 = 3;

		if (l1 == 3)
			b0 = 4;

		par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileRepairer repairer = (TileRepairer) par1World.getTileEntity(par2, par3, par4);

		if (repairer != null) {
			for (int j1 = 0; j1 < repairer.getSizeInventory(); ++j1) {
				ItemStack itemstack = repairer.getStackInSlot(j1);

				if (itemstack != null) {
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; par1World.spawnEntityInWorld(entityitem)) {
						int k1 = random.nextInt(21) + 10;

						if (k1 > itemstack.stackSize)
							k1 = itemstack.stackSize;

						itemstack.stackSize -= k1;
						int dmg;
						if (Loader.isModLoaded("TConstruct")) {
							if (TinkersConstructCompat.isTConstructTool(itemstack)) {
								dmg = TinkersConstructCompat.getDamage(itemstack);
							} else
								dmg = itemstack.getItemDamage();
						} else
							dmg = itemstack.getItemDamage();
						entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), k1, dmg));
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
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		return ConfigBlocks.blockCosmeticSolid.getIcon(par1, 4);
	}

	@Override
	public int getRenderType() {
		return LibRenderIDs.idRepairer;
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
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileRepairer();
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.REPAIRER;
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
	public Class<? extends ItemBlock> getItemBlock() {
		return null;
	}

	@Override
	public Class<? extends TileEntity> getTileEntity() {
		return TileRepairer.class;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_REPAIRER, new AspectList().add(Aspect.TOOL, 2).add(Aspect.CRAFT, 1).add(Aspect.ORDER, 1).add(Aspect.MAGIC, 1), -1, -9, 3, new ItemStack(this)).setConcealed().setParents(LibResearch.KEY_FUNNEL).setParentsHidden("THAUMIUM", "ENCHFABRIC")
				.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_REPAIRER));
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_REPAIRER, new ItemStack(this), 8, new AspectList().add(Aspect.TOOL, 15).add(Aspect.CRAFT, 20).add(Aspect.ORDER, 10).add(Aspect.MAGIC, 15), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.gold_ingot), new ItemStack(Items.diamond), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.planks), new ItemStack(Items.leather), new ItemStack(ConfigItems.itemResource, 1, 7), new ItemStack(ConfigItems.itemResource, 1, 2));
	}
}
