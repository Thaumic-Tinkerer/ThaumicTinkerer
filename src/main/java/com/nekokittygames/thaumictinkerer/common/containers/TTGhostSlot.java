package com.nekokittygames.thaumictinkerer.common.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * Ghost slot for Thaumic Tinkerer
 */
public class TTGhostSlot extends Slot {
    private static final IInventory emptyInventory = new InventoryBasic("[Null]", true, 0);
    private ItemStack itemStack;

    /**
     * Constructor for ghost slot
     * @param inventoryIn Item stack for ghost slot
     * @param index index in container
     * @param xPosition X Position of the slot
     * @param yPosition Y Position of the slot
     */
    public TTGhostSlot(ItemStack inventoryIn, int index, int xPosition, int yPosition) {
        super(emptyInventory, index, xPosition, yPosition);
        this.itemStack = inventoryIn;
    }

    /**
     * Gets the item stack showing
     * @return item stack
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Sets the item stack of the slot
     * @param itemStack item stack to use as ghost
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Gets a copy of the stack
     * @return copy of the item stack
     */
    @Override
    public @NotNull ItemStack getStack() {
        return itemStack.copy();
    }

    /**
     * Can the player take the stack (Always false)
     * @param playerIn Player
     * @return <c>false</c> always
     */
    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return false;
    }

    /**
     * Just returns a copy of the stack
     * @param amount amount to decrease by (ignored)
     * @return copy of the item stack
     */
    @Override
    public @NotNull ItemStack decrStackSize(int amount) {
        return itemStack.copy();
    }
}
