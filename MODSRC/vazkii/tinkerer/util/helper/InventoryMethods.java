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
 * File Created @ [28 Apr 2013, 17:47:08 (GMT)]
 */
package vazkii.tinkerer.util.helper;

import net.minecraft.item.ItemStack;

public final class InventoryMethods {

	public static ItemStack regularGetStackInSlot(ItemStack[] stacks, int slot) {
		return stacks[slot];
	}
	
	public static ItemStack regularDecreaseStackSize(ItemStack[] stacks, int slot, int qtd) {
		if (stacks[slot] != null) {
            ItemStack stackAt;

            if (stacks[slot].stackSize <= qtd) {
                stackAt = stacks[slot];
                stacks[slot] = null;
                return stackAt;
            } else {
                stackAt = stacks[slot].splitStack(qtd);

                if (stacks[slot].stackSize == 0)
                    stacks[slot] = null;

                return stackAt;
            }
        }

        return null;
	}
	
	public static void regularSetInventorySlotContents(ItemStack[] stacks, int slot, ItemStack stack) {
		stacks[slot] = stack;
	}
	
}
