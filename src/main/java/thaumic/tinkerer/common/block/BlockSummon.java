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
 * File Created @ [9 Sep 2013, 15:52:53 (GMT)]
 */
package thaumic.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigBlocks;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.TileSummon;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererBlock;
import thaumic.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.Random;

public class BlockSummon extends Block implements ITTinkererBlock {

	IIcon iconTop;
	IIcon iconSide;

	Random random;

	public BlockSummon() {
		super(Material.iron);
		setBlockBounds(0F, 0F, 0F, 1F, 1F / 16F * 2F, 1F);
		setHardness(3F);
		setResistance(50F);
		setStepSound(Block.soundTypeMetal);

		random = new Random();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TileSummon();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		iconTop = IconHelper.forBlock(iconRegister, this, 1);
		iconSide = IconHelper.forBlock(iconRegister, this, 2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int meta) {
		return par1 == ForgeDirection.UP.ordinal() ? iconTop : par1 == ForgeDirection.DOWN.ordinal() ? Block.getBlockFromName("obsidian").getIcon(0, 0) : iconSide;
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.SPAWNER;
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
		return TileSummon.class;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
        return (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_SUMMON, new AspectList().add(Aspect.WEAPON, 1).add(Aspect.BEAST, 3).add(Aspect.MAGIC, 3), -5, 8, 3, new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockSummon.class))).setWarp(3).setParents(LibResearch.KEY_BLOOD_SWORD)
                .setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_SUMMON + "0"), ResearchHelper.recipePage(LibResearch.KEY_SUMMON + "1"), ResearchHelper.infusionPage(LibResearch.KEY_SUMMON), new ResearchPage("1"));

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_SUMMON + "0", LibResearch.KEY_SUMMON, new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockSummon.class)), new AspectList().add(Aspect.ORDER, 50).add(Aspect.ENTROPY, 50), "WWW", "SSS", 'S', new ItemStack(Blocks.stone), 'W', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1));
	}
}
