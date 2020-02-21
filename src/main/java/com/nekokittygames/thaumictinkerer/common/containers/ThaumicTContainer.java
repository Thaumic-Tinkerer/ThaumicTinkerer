package com.nekokittygames.thaumictinkerer.common.containers;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMagnet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.jetbrains.annotations.NotNull;

/**
 * Base Container class for Thaumic Tinkerer Containers
 * @param <T> Tile Entity this container is for
 */
public abstract class ThaumicTContainer<T extends TileEntity>  extends Container {
    /**
     * Tile Entity Instance
     */
    protected final T tileEntity;

    /**
     * Constructor for Container
     * @param playerInventory Inventory for the player accessing the inventory
     * @param tileEntity Tile entity being opened
     */
    public ThaumicTContainer(IInventory playerInventory, T tileEntity) {

        this.tileEntity = tileEntity;

        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    /**
     * Adds all the player's slots in the usual place
     * @param playerInventory Player inventory
     */
    protected void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 8 + col * 18;
                int y = row * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 9 + row * 18;
            int y = 58 + 84;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    /**
     * Adds the tile entity slots
     */
    protected abstract void addOwnSlots();

    /**
     * Transfer stack to and from slot on shift click
     * @param playerIn player to transfer
     * @param index Index of slot to transfer
     * @return Stack being transfered
     */
    @Override
    public @NotNull ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 1) {
                if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Can the player open this inventory
     * @param entityPlayer player in question
     * @return <c>true</c> if player can open <c>false</c> otherwise
     */
    @Override
    public boolean canInteractWith(@NotNull EntityPlayer entityPlayer) {
        return true;
    }
}
