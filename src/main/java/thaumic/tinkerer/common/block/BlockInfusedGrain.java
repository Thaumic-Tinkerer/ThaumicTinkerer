package thaumic.tinkerer.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.TileInfusedGrain;
import thaumic.tinkerer.common.item.ItemInfusedGrain;
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
public class  BlockInfusedGrain extends BlockCrops implements ITTinkererBlock {



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
        return this.icons[0][0];
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
        for(int j = 0; j<names.length; j++) {
            String s = names[j];
            for (int i = 0; i < 4; i++) {
                icons[j][i] = IconHelper.forName(par1IconRegister, "crop_" + s + "_" + i);
            }
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			if (item != null) {
				ret.add(new ItemStack(item, 1, damageDropped(metadata)));
			}
		}
		if (metadata >= 7) {
			for (int i = 0; i < 3 + fortune; ++i) {
				if (world.rand.nextInt(15) <= metadata) {
					ret.add(new ItemStack(this.func_149866_i(), 1, damageDropped(metadata)));
				}
			}
		}

		return ret;
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
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return null;
	}

    public static Aspect getAspect(IBlockAccess world, int x, int y, int z) {
        return ((TileInfusedGrain)world.getTileEntity(x, y, z)).aspect;
    }

    public static void setAspect(IBlockAccess world, int x, int y, int z, Aspect aspect) {
        ((TileInfusedGrain) world.getTileEntity(x, y, z)).aspect = aspect;
    }

}
