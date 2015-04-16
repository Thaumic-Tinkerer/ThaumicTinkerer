package thaumic.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.quartz.BlockDarkQuartz;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.item.ItemTravelSlab;
import thaumic.tinkerer.common.item.quartz.ItemDarkQuartzSlab;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererBlock;
import thaumic.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Katrina on 13/04/2015.
 */
public class BlockTravelSlab extends BlockSlab implements ITTinkererBlock {

    public BlockTravelSlab(boolean par2) {
        super(par2, Material.rock);
        setHardness(0.8F);
        setResistance(10F);
        if (!par2) {
            setLightOpacity(0);
            setCreativeTab(ModCreativeTab.INSTANCE);
        }
    }

    public BlockTravelSlab(Boolean par2) {
        this(par2.booleanValue());
    }

    public BlockTravelSlab() {
        this(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return ConfigBlocks.blockCosmeticSolid.getIcon(par1,2);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockTravelSlab.class));
    }

    //@Override
    //public int idDropped(int par1, Random par2Random, int par3) {
    //	return ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartzSlab.class).blockID;
    //}

    @Override
    public ItemStack createStackedBlock(int par1) {
        return new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockTravelSlab.class));
    }

    @Override
    public String func_150002_b(int i) {
        return "tile." + LibBlockNames.TRAVEL_SLAB;
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
        return field_150004_a ? LibBlockNames.TRAVEL_SLAB_FULL : LibBlockNames.TRAVEL_SLAB;
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
        return ItemTravelSlab.class;
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
        return new ThaumicTinkererCraftingBenchRecipe("PAVETRAVEL", new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockTravelSlab.class), 6),
                "QQQ",
                'Q', new ItemStack(ConfigBlocks.blockCosmeticSolid,2));
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity e) {
        if(world.getBlock(x, y, z) == this) {
            if(e instanceof EntityLivingBase) {
                if(world.isRemote) {
                    Thaumcraft.proxy.blockSparkle(world, x, y, z, '?', 5);
                }

                ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 40, 1));
                ((EntityLivingBase)e).addPotionEffect(new PotionEffect(Potion.jump.id, 40, 0));
            }

            super.onEntityWalking(world, x, y, z, e);
        }
    }

    
}
