package com.nekokittygames.thaumictinkerer.common.items.Kami.ichorpouch;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.common.items.casters.ItemFocusPouch;

public class SlotNoPouches extends Slot {

    public SlotNoPouches(IInventory par1iInventory, int par2, int par3, int par4) {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        Item item = par1ItemStack.getItem();
        return !(item instanceof ItemFocusPouch);
    }
}
