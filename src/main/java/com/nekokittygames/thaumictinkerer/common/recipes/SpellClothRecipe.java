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
 * File Created @ [9 Sep 2013, 01:20:26 (GMT)]
 */
package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.api.INoRemoveEnchant;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

public class SpellClothRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe{

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting var1, @Nonnull World var2) {
        boolean foundCloth = false;
        boolean foundEnchanted = false;

        for(int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack stack = var1.getStackInSlot(i);
            if(!stack.isEmpty()) {
                if(stack.isItemEnchanted() && !foundEnchanted && stack.getItem() != ModItems.spellbinding_cloth)
                    foundEnchanted = true;

                else if(stack.getItem() == ModItems.spellbinding_cloth && !foundCloth)
                    foundCloth = true;

                else return false; // Found an invalid item, breaking the recipe
            }
        }

        return foundCloth && foundEnchanted;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
        ItemStack stackToDisenchant = ItemStack.EMPTY;
        for(int i = 0; i < var1.getSizeInventory(); i++) {
            ItemStack stack = var1.getStackInSlot(i);
            if(!stack.isEmpty() && stack.isItemEnchanted() && stack.getItem() != ModItems.spellbinding_cloth) {
                stackToDisenchant = stack.copy();
                stackToDisenchant.setCount(1);
                break;
            }
        }

        if(stackToDisenchant.isEmpty())
            return ItemStack.EMPTY;

        stackToDisenchant.getTagCompound().removeTag("ench"); // Remove enchantments
        return stackToDisenchant;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }
}