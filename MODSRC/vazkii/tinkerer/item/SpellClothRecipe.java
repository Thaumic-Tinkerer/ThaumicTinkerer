/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [25 Apr 2013, 14:28:58 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SpellClothRecipe implements IRecipe {

	Item item;

	public SpellClothRecipe(Item item) {
		this.item = item;
	}

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		boolean foundCloth = false;
		boolean foundEnchanted = false;
		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(stack != null) {
				if(stack.isItemEnchanted() && !(stack.getItem() instanceof INoRemoveEnchant) && !foundEnchanted)
					foundEnchanted = true;

				else if(stack.itemID == item.itemID && !foundCloth)
					foundCloth = true;

				else return false; // Found an invalid item, breaking the recipe
			}
		}

		return foundCloth && foundEnchanted;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		ItemStack stackToDisenchant = null;
		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(stack != null && stack.isItemEnchanted()) {
				stackToDisenchant = stack.copy();
				break;
			}
		}

		if(stackToDisenchant == null)
			return null;

		NBTTagCompound cmp = (NBTTagCompound) stackToDisenchant.getTagCompound().copy();
		cmp.removeTag("ench"); // Remove enchantments
		stackToDisenchant.setTagCompound(cmp);

		return stackToDisenchant;
	}

	@Override
    public int getRecipeSize() {
        return 10;
    }

	@Override
    public ItemStack getRecipeOutput() {
        return null;
    }

}
