package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.api.INoRemoveEnchant;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class SpellClothRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {

    Item item;

    public SpellClothRecipe(Item item) {
        this.item = item;
        this.setRegistryName(LibMisc.MOD_ID,"spellbinding_cloth");
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean foundCloth = false;
        boolean foundEnchanted = false;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                // TODO: Add config / IMC based blacklist
                if (stack.isItemEnchanted() && !(stack.getItem() instanceof INoRemoveEnchant) && !foundEnchanted)
                    foundEnchanted = true;

                else if (stack.getItem() == item && !foundCloth)
                    foundCloth = true;

                else return false; // Found an invalid item, breaking the recipe
            }
        }

        return foundCloth && foundEnchanted;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack stackToDisenchant = ItemStack.EMPTY;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && stack.isItemEnchanted()) {
                stackToDisenchant = stack.copy();
                break;
            }
        }

        if (stackToDisenchant == ItemStack.EMPTY)
            return ItemStack.EMPTY;
        NBTTagCompound cmp = (NBTTagCompound) stackToDisenchant.getTagCompound().copy();
        cmp.removeTag("ench"); // Remove enchantments
        stackToDisenchant.setTagCompound(cmp);

        return stackToDisenchant;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width>=2 && height>=2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
