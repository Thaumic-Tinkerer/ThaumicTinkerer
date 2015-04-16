package thaumic.tinkerer.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.quartz.BlockDarkQuartz;
import thaumic.tinkerer.common.block.tile.TileWardedSlab;
import thaumic.tinkerer.common.core.handler.ModCreativeTab;
import thaumic.tinkerer.common.item.ItemTravelSlab;
import thaumic.tinkerer.common.item.ItemWardSlab;
import thaumic.tinkerer.common.item.quartz.ItemDarkQuartzSlab;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererBlock;
import thaumic.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Katrina on 13/04/2015.
 */
public class BlockWardSlab extends BlockSlab implements ITTinkererBlock,ITileEntityProvider {

    public BlockWardSlab(boolean par2) {
        super(par2, Material.rock);
        setHardness(0.8F);
        setResistance(10F);
        isBlockContainer=true;
        if (!par2) {
            setLightOpacity(0);
            setCreativeTab(ModCreativeTab.INSTANCE);
        }
    }

    public BlockWardSlab(Boolean par2) {
        this(par2.booleanValue());
    }

    public BlockWardSlab() {
        this(false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return ConfigBlocks.blockCosmeticSolid.getIcon(par1,3);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockWardSlab.class));
    }

    //@Override
    //public int idDropped(int par1, Random par2Random, int par3) {
    //	return ThaumicTinkerer.registry.getFirstBlockFromClass(BlockDarkQuartzSlab.class).blockID;
    //}

    @Override
    public ItemStack createStackedBlock(int par1) {
        return new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockWardSlab.class));
    }

    @Override
    public String func_150002_b(int i) {
        return "tile." + LibBlockNames.WARD_SLAB;
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
        return field_150004_a ? LibBlockNames.WARD_SLAB_FULL : LibBlockNames.WARD_SLAB;
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
        return ItemWardSlab.class;
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return TileWardedSlab.class;
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
        return new ThaumicTinkererCraftingBenchRecipe("PAVEWARD", new ItemStack(ThaumicTinkerer.registry.getFirstBlockFromClass(BlockWardSlab.class), 6),
                "QQQ",
                'Q', new ItemStack(ConfigBlocks.blockCosmeticSolid,3));
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (world.getBlock(x, y, z) == this) {
            int list;
            if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
                for (list = 0; list < Thaumcraft.proxy.particleCount(2); ++list) {
                    Thaumcraft.proxy.blockRunes(world, (double) x, (double) ((float) y + 0.7F), (double) z, 0.2F + world.rand.nextFloat() * 0.4F, world.rand.nextFloat() * 0.3F, 0.8F + world.rand.nextFloat() * 0.2F, 20, -0.02F);
                }
            } else if (world.getBlock(x, y + 1, z) != ConfigBlocks.blockAiry && world.getBlock(x, y + 1, z).getBlocksMovement(world, x, y + 1, z) || world.getBlock(x, y + 2, z) != ConfigBlocks.blockAiry && world.getBlock(x, y + 1, z).getBlocksMovement(world, x, y + 1, z)) {
                for (list = 0; list < Thaumcraft.proxy.particleCount(3); ++list) {
                    Thaumcraft.proxy.blockRunes(world, (double) x, (double) ((float) y + 0.7F), (double) z, 0.9F + world.rand.nextFloat() * 0.1F, world.rand.nextFloat() * 0.3F, world.rand.nextFloat() * 0.3F, 24, -0.02F);
                }
            } else {
                List var10 = world.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getBoundingBox((double) x, (double) y, (double) z, (double) (x + 1), (double) (y + 1), (double) (z + 1)).expand(1.0D, 1.0D, 1.0D));
                if (!var10.isEmpty()) {
                    Iterator iterator = var10.iterator();

                    while (iterator.hasNext()) {
                        Entity entity = (Entity) iterator.next();
                        if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer)) {
                            Thaumcraft.proxy.blockRunes(world, (double) x, (double) ((float) y + 0.6F + world.rand.nextFloat() * Math.max(0.8F, entity.getEyeHeight())), (double) z, 0.6F + world.rand.nextFloat() * 0.4F, 0.0F, 0.3F + world.rand.nextFloat() * 0.7F, 20, 0.0F);
                            break;
                        }
                    }
                }
            }
        }

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

    @Override
    public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
        return !((TileWardedSlab)world.getTileEntity(x,y,z)).gettingPower();
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileWardedSlab();
    }
}
