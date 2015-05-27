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
 * File Created @ [8 Sep 2013, 15:59:00 (GMT)]
 */
package thaumic.tinkerer.common.block.quartz;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockMod;
import thaumic.tinkerer.common.item.quartz.ItemDarkQuartz;
import thaumic.tinkerer.common.item.quartz.ItemDarkQuartzBlock;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipeMulti;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;
import java.util.List;

public class BlockDarkQuartz extends BlockMod {

    private static final String[] iconNames = new String[]{"darkQuartz0", "chiseledDarkQuartz0", "pillarDarkQuartz0", null, null};
    private IIcon[] darkQuartzIcons;
    private IIcon chiseledDarkQuartzIcon;
    private IIcon pillarDarkQuartzIcon;
    private IIcon darkQuartzTopIcon;

    public BlockDarkQuartz() {
        super(Material.rock);
        setHardness(0.8F);
        setResistance(10F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        if (par2 != 2 && par2 != 3 && par2 != 4) {
            if (par1 != 1 && (par1 != 0 || par2 != 1)) {
                if (par1 == 0)
                    return darkQuartzTopIcon;
                else {
                    if (par2 < 0 || par2 >= darkQuartzIcons.length)
                        par2 = 0;

                    return darkQuartzIcons[par2];
                }
            } else return par2 == 1 ? chiseledDarkQuartzIcon : darkQuartzTopIcon;
        } else
            return par2 == 2 && (par1 == 1 || par1 == 0) ? pillarDarkQuartzIcon : par2 == 3 && (par1 == 5 || par1 == 4) ? pillarDarkQuartzIcon : par2 == 4 && (par1 == 2 || par1 == 3) ? pillarDarkQuartzIcon : darkQuartzIcons[par2];
    }

    @Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
        if (par9 == 2) {
            switch (par5) {
                case 0:
                case 1:
                    par9 = 2;
                    break;
                case 2:
                case 3:
                    par9 = 4;
                    break;
                case 4:
                case 5:
                    par9 = 3;
            }
        }

        return par9;
    }

    @Override
    public int damageDropped(int par1) {
        return par1 != 3 && par1 != 4 ? par1 : 2;
    }

    @Override
    public ItemStack createStackedBlock(int par1) {
        return par1 != 3 && par1 != 4 ? super.createStackedBlock(par1) : new ItemStack(this, 1, 2);
    }

    @Override
    public int getRenderType() {
        return 39;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List par3List) {
        par3List.add(new ItemStack(this, 1, 0));
        par3List.add(new ItemStack(this, 1, 1));
        par3List.add(new ItemStack(this, 1, 2));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        darkQuartzIcons = new IIcon[iconNames.length];

        for (int i = 0; i < darkQuartzIcons.length; ++i) {
            if (iconNames[i] == null)
                darkQuartzIcons[i] = darkQuartzIcons[i - 1];
            else darkQuartzIcons[i] = IconHelper.forName(par1IconRegister, iconNames[i]);
        }

        darkQuartzTopIcon = IconHelper.forName(par1IconRegister, "darkQuartz1");
        chiseledDarkQuartzIcon = IconHelper.forName(par1IconRegister, "chiseledDarkQuartz1");
        pillarDarkQuartzIcon = IconHelper.forName(par1IconRegister, "pillarDarkQuartz1");
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return LibBlockNames.DARK_QUARTZ;
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
        return ItemDarkQuartzBlock.class;
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
        return new ThaumicTinkererRecipeMulti(

                new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 1, new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class)),
                        "QQ", "QQ",
                        'Q', ThaumicTinkerer.registry.getFirstItemFromClass(ItemDarkQuartz.class)),
                new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 3, new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), 2, 2),
                        "Q", "Q",
                        'Q', ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class)),
                new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 4, new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartz.class), 1, 1),
                        "Q", "Q",
                        'Q', ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartzSlab.class))
        );
    }
}
