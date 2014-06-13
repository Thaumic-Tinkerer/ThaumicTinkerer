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
 * File Created @ [Dec 11, 2013, 10:31:09 PM (GMT)]
 */
package vazkii.tinkerer.common.block;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.tile.TileAspectAnalyzer;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibGuiIDs;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.Random;

public class BlockAspectAnalyzer extends BlockModContainer {

	IIcon[] icons = new IIcon[5];
	Random random;

	protected BlockAspectAnalyzer() {
		super(Material.wood);
		setHardness(1.7F);
		setResistance(1F);
		setStepSound(Block.soundTypeWood);

		random = new Random();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if (!par1World.isRemote) {
			TileEntity tile = par1World.getTileEntity(par2, par3, par4);
			if (tile != null)
				par5EntityPlayer.openGui(ThaumicTinkerer.instance, LibGuiIDs.GUI_ID_ASPECT_ANALYZER, par1World, par2, par3, par4);
		}

		return true;
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		TileAspectAnalyzer analyzer = (TileAspectAnalyzer) par1World.getTileEntity(par2, par3, par4);

		if (analyzer != null) {
			for (int j1 = 0; j1 < analyzer.getSizeInventory(); ++j1) {
				ItemStack itemstack = analyzer.getStackInSlot(j1);

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
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		for (int i = 0; i < 5; i++)
			icons[i] = IconHelper.forBlock(par1IconRegister, this, i);
	}

	@Override
	public IIcon getIcon(int par1, int par2) {
		return icons[par1 == 0 || par1 == 1 ? 0 : par1 - 1];
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileAspectAnalyzer();
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.ASPECT_ANALYZER;
	}

	@Override
	public boolean shouldRegister() {
		return Loader.isModLoaded("ComputerCraft");
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_ASPECT_ANALYZER, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.SENSES, 1).add(Aspect.MIND, 1), 0, 1, 2, new ItemStack(this)).setParents(LibResearch.KEY_PERIPHERALS).setParentsHidden("GOGGLES", "THAUMIUM").setConcealed().setRound()
				.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ASPECT_ANALYZER));
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ASPECT_ANALYZER, LibResearch.KEY_ASPECT_ANALYZER, new ItemStack(this), new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
				"TWT", "WMW", "TWT",
				'W', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
				'M', new ItemStack(ConfigItems.itemThaumometer),
				'T', new ItemStack(ConfigItems.itemResource, 1, 2));
	}
}
