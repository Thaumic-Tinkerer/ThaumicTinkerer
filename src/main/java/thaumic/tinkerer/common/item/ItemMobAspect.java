package thaumic.tinkerer.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.TileSummon;
import thaumic.tinkerer.common.core.helper.NumericAspectHelper;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.*;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.List;

public class ItemMobAspect extends ItemBase {

    //Real value is 16
    //Padding room inclued
    //To prevent corruption
    public static final int aspectCount = 20;
    public static IIcon[] aspectIcons = new IIcon[aspectCount * 3];

    public ItemMobAspect() {
        super();
        setMaxStackSize(16);
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

    public static ItemStack getInfusedStackFromAspect(Aspect a) {
        ItemStack result = new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemMobAspect.class));
        result.setItemDamage(40 + NumericAspectHelper.getNumber(a));
        return result;
    }

    public static boolean isCondensed(ItemStack item) {
        return item.getItemDamage() >= aspectCount && item.getItemDamage() < aspectCount * 2;
    }

    public static boolean isInfused(ItemStack item) {
        return item.getItemDamage() >= aspectCount * 2;
    }

    public static void markLastUsedTablet(ItemStack stack, TileSummon tablet) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        stack.stackTagCompound.setInteger("LastX", tablet.xCoord);
        stack.stackTagCompound.setInteger("LastY", tablet.yCoord);
        stack.stackTagCompound.setInteger("LastZ", tablet.zCoord);
    }

    public static boolean lastUsedTabletMatches(ItemStack stack, TileSummon tablet) {
        if (stack.stackTagCompound == null) {
            return true;
        }

        return (stack.stackTagCompound.getInteger("LastX") == tablet.xCoord &&
                stack.stackTagCompound.getInteger("LastY") == tablet.yCoord &&
                stack.stackTagCompound.getInteger("LastZ") == tablet.zCoord);
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

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

            ThaumcraftApi.registerObjectTag(new ItemStack(this, 1, i), new int[]{i}, new AspectList().add(NumericAspectHelper.getAspect(i), 8));
            recipeMulti.addRecipe(new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_SUMMON + "1", new ItemStack(this, 1, i + 20), "XXX", "XXX", "XXX", 'X', new ItemStack(this, 1, i)));

            ItemStack input = new ItemStack(this, 1, i + 20);
            recipeMulti.addRecipe(new ThaumicTinkererInfusionRecipe(LibResearch.KEY_SUMMON, new ItemStack(this, 1, i + 40), 4,
                    new AspectList().add(getAspect(new ItemStack(this, 1, i)), 10), input,
                    new ItemStack[]{input, input, input, input, input, input, input, input}));

        }
        return recipeMulti;
    }

    @Override
    public void getSubItems(Item par1Item, CreativeTabs par2CreativeTabs, List par3List) {
        for (NumericAspectHelper aspect : NumericAspectHelper.values) {
            par3List.add(getStackFromAspect(aspect.getAspect()));
            par3List.add(getInfusedStackFromAspect(aspect.getAspect()));
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

    @Override
    public String getItemName() {
        return LibItemNames.MOB_ASPECT;
    }
}
