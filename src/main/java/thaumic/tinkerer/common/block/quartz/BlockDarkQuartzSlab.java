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
package thaumic.tinkerer.common.block.quartz;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.item.quartz.ItemDarkQuartzSlab;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererBlock;
import thaumic.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;
import java.util.Random;

public class BlockDarkQuartzSlab extends BlockSlab implements ITTinkererBlock {

    public BlockDarkQuartzSlab(boolean par2) {
        super(par2, Material.rock);
        setHardness(0.8F);
        setResistance(10F);
        if (!par2) {
            setLightOpacity(0);
            setCreativeTab(ModCreativeTab.INSTANCE);
        }
    }

    public BlockDarkQuartzSlab(Boolean par2) {
        this(par2.booleanValue());
    }

    public BlockDarkQuartzSlab() {
        this(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class).getBlockTextureFromSide(par1);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartzSlab.class));
    }

    //@Override
    //public int idDropped(int par1, Random par2Random, int par3) {
    //	return ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartzSlab.class).blockID;
    //}

    @Override
    public ItemStack createStackedBlock(int par1) {
        return new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartzSlab.class));
    }

    @Override
    public String func_150002_b(int i) {
        return "tile." + LibBlockNames.DARK_QUARTZ_SLAB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        // NO-OP
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        ArrayList result = new ArrayList();
        result.add(true);
        return result;
    }

    @Override
    public String getBlockName() {
        return field_150004_a ? LibBlockNames.DARK_QUARTZ_SLAB_FULL : LibBlockNames.DARK_QUARTZ_SLAB;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return !field_150004_a;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        return ItemDarkQuartzSlab.class;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return null;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        if (isOpaqueCube()) {
            return null;
        }
        return new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 2, new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartzSlab.class), 6),
                "QQQ",
                'Q', ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class));
    }
}