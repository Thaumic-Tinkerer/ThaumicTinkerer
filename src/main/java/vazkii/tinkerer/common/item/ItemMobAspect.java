package vazkii.tinkerer.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.helper.NumericAspectHelper;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.*;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.List;

public class ItemMobAspect extends ItemBase {

	//Real value is 16
	//Padding room inclued
	//To prevent corruption
	public static final int aspectCount = 20;

	public ItemMobAspect() {
		super();
		setMaxStackSize(16);
	}

	@Override
	public boolean getHasSubtypes() {
		return true;
	}

	public static IIcon[] aspectIcons = new IIcon[aspectCount * 3];

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);

		for (NumericAspectHelper aspect : NumericAspectHelper.values) {
			aspectIcons[aspect.num] = IconHelper.forName(par1IconRegister, aspect.getAspect().getName().toLowerCase());
			aspectIcons[aspect.num + aspectCount] = IconHelper.forName(par1IconRegister, aspect.getAspect().getName().toLowerCase() + "_condensed");

			aspectIcons[aspect.num + 2 * aspectCount] = IconHelper.forName(par1IconRegister, aspect.getAspect().getName().toLowerCase());

		}
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return isInfused(par1ItemStack);
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
		ThaumicTinkererRecipeMulti recipeMulti = new ThaumicTinkererRecipeMulti();
		for (int i = 0; i < NumericAspectHelper.values.size(); i++) {

			ThaumcraftApi.registerObjectTag(new ItemStack(this, 1, i), new int[]{ i }, new AspectList().add(NumericAspectHelper.getAspect(i), 8));
			recipeMulti.addRecipe(new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_SUMMON + "1", new ItemStack(this, 1, i + 20), "XXX", "XXX", "XXX", 'X', new ItemStack(this, 1, i)));

			ItemStack input = new ItemStack(this, 1, i + 20);
			recipeMulti.addRecipe(new ThaumicTinkererInfusionRecipe(LibResearch.KEY_SUMMON, new ItemStack(this, 1, i + 40), 4,
					new AspectList().add(getAspect(new ItemStack(this, 1, i)), 10), input,
					new ItemStack[]{ input, input, input, input, input, input, input, input }));

		}
		return recipeMulti;
	}

	@Override
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTabs, List par3List) {
		for (NumericAspectHelper aspect : NumericAspectHelper.values) {
			par3List.add(getStackFromAspect(aspect.getAspect()));
		}
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return aspectIcons[par1];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (isCondensed(stack)) {
			return super.getUnlocalizedName(stack) + ".condensed";
		}
		if (isInfused(stack)) {
			return super.getUnlocalizedName(stack) + ".infused";
		}
		return super.getUnlocalizedName(stack);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		list.add(getAspect(itemStack).getName());
	}

	public static Aspect getAspect(ItemStack item) {
		if (item == null) {
			return null;
		}
		return NumericAspectHelper.getAspect(item.getItemDamage() % aspectCount);
	}

	public static ItemStack getStackFromAspect(Aspect a) {
		ItemStack result = new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemMobAspect.class));
		result.setItemDamage(NumericAspectHelper.getNumber(a));
		return result;
	}

	public static boolean isCondensed(ItemStack item) {
		return item.getItemDamage() >= aspectCount && item.getItemDamage() < aspectCount * 2;
	}

	public static boolean isInfused(ItemStack item) {
		return item.getItemDamage() >= aspectCount * 2;
	}

	@Override
	public String getItemName() {
		return LibItemNames.MOB_ASPECT;
	}
}
