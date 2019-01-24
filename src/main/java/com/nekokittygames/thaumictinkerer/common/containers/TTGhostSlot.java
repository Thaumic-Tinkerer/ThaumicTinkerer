package com.nekokittygames.thaumictinkerer.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class TTGhostSlot extends Slot {
    private static IInventory emptyInventory = new InventoryBasic("[Null]", true, 0);
    private ItemStack itemStack;

    public TTGhostSlot(ItemStack inventoryIn, int index, int xPosition, int yPosition) {
        super(emptyInventory, index, xPosition, yPosition);
        this.itemStack = inventoryIn;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack getStack() {
        return itemStack.copy();
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return itemStack.copy();
    }
}
