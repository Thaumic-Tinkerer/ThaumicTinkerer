package thaumic.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.block.BlockInfusedGrain;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ITTinkererItem;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipeMulti;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 4/14/14.
 */
public class ItemInfusedSeeds extends ItemSeeds implements ITTinkererItem {

	public ItemInfusedSeeds() {
		super(Blocks.wheat, Blocks.farmland);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add(getAspect(par1ItemStack).getName());
	}

	public static int getMetaForAspect(Aspect aspect) {
		for (PRIMAL_ASPECT_ENUM e : PRIMAL_ASPECT_ENUM.values()) {
			if (aspect == e.aspect) {
				return e.ordinal();
			}
		}
		return 0;
	}

	public Aspect getAspect(ItemStack stack) {
		return PRIMAL_ASPECT_ENUM.values()[stack.getItemDamage()].aspect;
	}

	public Block getCropBlock(ItemStack stack) {
		return BlockInfusedGrain.getBlockFromAspect(getAspect(stack));

	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List l) {
		for (PRIMAL_ASPECT_ENUM primal : PRIMAL_ASPECT_ENUM.values()) {
			l.add(new ItemStack(item, 1, primal.ordinal()));
		}
	}
    private IIcon[] icons;

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        icons = new IIcon[7];
        icons[0] = IconHelper.forName(par1IconRegister, "seed_aer");
        icons[1] = IconHelper.forName(par1IconRegister, "seed_ignis");
        icons[2] = IconHelper.forName(par1IconRegister, "seed_terra");
        icons[3] = IconHelper.forName(par1IconRegister, "seed_aqua");

        icons[4] = IconHelper.forName(par1IconRegister, "seed_ordo");
        icons[5] = IconHelper.forName(par1IconRegister, "seed_perditio");

        icons[6] = IconHelper.forName(par1IconRegister, "seed_complex");
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return icons[par1];
    }

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getItemName() {
		return LibItemNames.INFUSED_SEEDS;
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
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererRecipeMulti(
				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 0, new ItemStack(this, 1, 0), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigItems.itemShard, 1, 0)),

				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 1, new ItemStack(this, 1, 1), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 1), new ItemStack(ConfigItems.itemShard, 1, 1), new ItemStack(ConfigItems.itemShard, 1, 1), new ItemStack(ConfigItems.itemShard, 1, 1)),

				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 2, new ItemStack(this, 1, 2), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 3)),

				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 3, new ItemStack(this, 1, 3), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 2), new ItemStack(ConfigItems.itemShard, 1, 2), new ItemStack(ConfigItems.itemShard, 1, 2), new ItemStack(ConfigItems.itemShard, 1, 2)),


                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 4, new ItemStack(this, 1, 4), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 4), new ItemStack(ConfigItems.itemShard, 1, 4), new ItemStack(ConfigItems.itemShard, 1, 4), new ItemStack(ConfigItems.itemShard, 1, 4)),

                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_POTIONS + 5, new ItemStack(this, 1, 5), 5, new AspectList().add(Aspect.CROP, 32).add(Aspect.HARVEST, 32), new ItemStack(Items.wheat_seeds), new ItemStack(ConfigItems.itemShard, 1, 5), new ItemStack(ConfigItems.itemShard, 1, 5), new ItemStack(ConfigItems.itemShard, 1, 5), new ItemStack(ConfigItems.itemShard, 1, 5))
		);
	}

	private enum PRIMAL_ASPECT_ENUM {
		AIR(Aspect.AIR),
		FIRE(Aspect.FIRE),
		EARTH(Aspect.EARTH),
		WATER(Aspect.WATER),
        ORDER(Aspect.ORDER),
        CHAOS(Aspect.ENTROPY);

		Aspect aspect;

		PRIMAL_ASPECT_ENUM(Aspect a) {
			this.aspect = a;
		}
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (par7 != 1) {
			return false;
		} else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)) {
			if (par3World.getBlock(par4, par5, par6).canSustainPlant(par3World, par4, par5, par6, ForgeDirection.UP, this) && par3World.isAirBlock(par4, par5 + 1, par6)) {
				par3World.setBlock(par4, par5 + 1, par6, getCropBlock(par1ItemStack));
				--par1ItemStack.stackSize;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
