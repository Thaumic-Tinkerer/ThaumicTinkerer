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
 * File Created @ [8 Sep 2013, 15:59:37 (GMT)]
 */
package vazkii.tinkerer.common.block.quartz;

import java.util.Random;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import vazkii.tinkerer.common.lib.LibBlockNames;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDarkQuartzSlab extends BlockHalfSlab {

	public BlockDarkQuartzSlab(int par1, boolean par2) {
		super(par1, par2, Material.rock);
		setHardness(0.8F);
		setResistance(10F);
		if(!par2) {
			setLightOpacity(0);
			setCreativeTab(ModCreativeTab.INSTANCE);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return ModBlocks.darkQuartz.getBlockTextureFromSide(par1);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return ModBlocks.darkQuartzSlab.blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return blockID;
	}

	@Override
	public ItemStack createStackedBlock(int par1) {
		return new ItemStack(ModBlocks.darkQuartzSlab);
	}

	@Override
	public String getFullSlabName(int i) {
		return "tile." + LibBlockNames.DARK_QUARTZ_SLAB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		// NO-OP
	}
}