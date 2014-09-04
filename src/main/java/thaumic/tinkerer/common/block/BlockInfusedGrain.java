package thaumic.tinkerer.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.research.ResearchManager;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.client.core.proxy.TTClientProxy;
import thaumic.tinkerer.client.lib.LibRenderIDs;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.TileInfusedFarmland;
import thaumic.tinkerer.common.block.tile.TileInfusedGrain;
import thaumic.tinkerer.common.core.helper.AspectCropLootManager;
import thaumic.tinkerer.common.item.ItemInfusedSeeds;
import thaumic.tinkerer.common.lib.LibBlockNames;
import thaumic.tinkerer.common.registry.ITTinkererBlock;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pixlepix on 4/14/14.
 */
public class BlockInfusedGrain extends BlockCrops implements ITTinkererBlock {


    //Code based off vanilla potato code


    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int meta) {
        if (meta < 7) {
            if (meta == 6) {
                meta = 5;
            }
            return this.icons[getNumberFromAspectForTexture(getAspect(world, x, y, z))][meta >> 1];
        } else {
            return this.icons[getNumberFromAspectForTexture(getAspect(world, x, y, z))][3];
        }
    }

    //Override BlockCrop's getIcon to prevent a crash with mods such as WAILA
    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return this.icons[1][0];
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return LibRenderIDs.idGrain;
    }

    //Returns 0-5 for primal aspects, or 6 if compound aspect
    public static int getNumberFromAspectForTexture(Aspect aspect) {
        if (aspect == Aspect.AIR) {
            return 0;
        }
        if (aspect == Aspect.FIRE) {
            return 1;
        }
        if (aspect == Aspect.WATER) {
            return 2;
        }
        if (aspect == Aspect.EARTH) {
            return 3;
        }
        if (aspect == Aspect.ORDER) {
            return 4;
        }
        if (aspect == Aspect.ENTROPY) {
            return 5;
        }
        return 6;
    }


    private IIcon[][] icons;

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        icons = new IIcon[7][4];
        String[] names = {"aer", "ignis", "aqua", "terra", "ordo", "perditio", "generic"};
        for (int j = 0; j < names.length; j++) {
            String s = names[j];
            for (int i = 0; i < 4; i++) {
                icons[j][i] = IconHelper.forName(par1IconRegister, "crop_" + s + "_" + i);
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        Random rand = new Random();
        int count = 1;
        for (int i = 0; i < count; i++) {
            ItemStack seedStack = new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedSeeds.class));
            ItemInfusedSeeds.setAspect(seedStack, getAspectDropped(world, x, y, z, metadata));
            ItemInfusedSeeds.setAspectTendencies(seedStack, ((TileInfusedGrain) world.getTileEntity(x, y, z)).primalTendencies);
            while (rand.nextInt(10000) < Math.pow(getPrimalTendencyCount(world, x, y, z, Aspect.ENTROPY), 2)) {
                seedStack.stackSize++;
            }
            ret.add(seedStack);
            fertilizeSoil(world, x, y, z, metadata);
        }
        if (metadata >= 7) {
            do {
            ret.add(AspectCropLootManager.getLootForAspect(getAspect(world, x, y, z)));

            } while (world.rand.nextInt(75) < getPrimalTendencyCount(world, x, y, z, Aspect.ORDER));
        }
        for (ItemStack item : ret) {
            float f = 0.7F;
            double d0 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, item);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }

        super.breakBlock(world, x, y, z, block, metadata);

    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<ItemStack>();
    }

    public int getPrimalTendencyCount(World world, int x, int y, int z, Aspect aspect) {
        return world.getTileEntity(x, y, z) instanceof TileInfusedGrain ? ((TileInfusedGrain) world.getTileEntity(x, y, z)).primalTendencies.getAmount(aspect) : 0;
    }

    private void fertilizeSoil(World world, int x, int y, int z, int metadata) {
        if (metadata >= 7) {
            do {
                if (world.getTileEntity(x, y - 1, z) instanceof TileInfusedFarmland) {
                    Aspect currentAspect = getAspect(world, x, y, z);
                    ((TileInfusedFarmland) world.getTileEntity(x, y - 1, z)).aspectList.add(currentAspect, 1);
                    ((TileInfusedFarmland) world.getTileEntity(x, y - 1, z)).reduceSaturatedAspects();
                    world.markBlockForUpdate(x, y - 1, z);
                }
            } while (world.rand.nextInt(55) < getPrimalTendencyCount(world, x, y, z, Aspect.EARTH));
        }
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {
        //Prevent normal growth from occuring
        //Growth takes place in the tile entity
        checkAndDropBlock(world, x, y, z);
    }


    public static final int BREEDING_CHANCE = 10;

    public Aspect getAspectDropped(World world, int x, int y, int z, int metadata) {
        Aspect currentAspect = getAspect(world, x, y, z);
        if (metadata >= 7 && currentAspect != null) {
            if (world.getTileEntity(x, y - 1, z) instanceof TileInfusedFarmland) {
                AspectList farmlandAspectList = ((TileInfusedFarmland) world.getTileEntity(x, y - 1, z)).aspectList;
                for (Aspect aspect : farmlandAspectList.getAspects()) {
                    Random rand = new Random();
                    if (rand.nextInt(BREEDING_CHANCE) < (getPrimalTendencyCount(world, x, y, z, Aspect.FIRE) + 1) * farmlandAspectList.getAmount(aspect) * farmlandAspectList.getAmount(aspect)) {
                        if (ResearchManager.getCombinationResult(aspect, currentAspect) != null) {
                            return ResearchManager.getCombinationResult(aspect, currentAspect);
                        }
                    }
                }
            }
        }
        return currentAspect;
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return null;
    }

    @Override
    public String getBlockName() {
        return LibBlockNames.INFUSED_GRAIN_BLOCK;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return false;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlock() {
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return TileInfusedGrain.class;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileInfusedGrain();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return null;
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return null;
    }


    public static Aspect getAspect(IBlockAccess world, int x, int y, int z) {
        return world.getTileEntity(x, y, z) instanceof TileInfusedGrain ? ((TileInfusedGrain) world.getTileEntity(x, y, z)).aspect : null;
    }

    public static void setAspect(IBlockAccess world, int x, int y, int z, Aspect aspect) {
        if (world.getTileEntity(x, y, z) instanceof TileInfusedGrain) {
            ((TileInfusedGrain) world.getTileEntity(x, y, z)).aspect = aspect;
        }
    }

}
