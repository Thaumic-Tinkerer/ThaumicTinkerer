package com.nekokittygames.thaumictinkerer.common.containers;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class EnchanterContainer extends Container {

    private TileEntityEnchanter tileEntity;

    public EnchanterContainer(IInventory playerInventory, TileEntityEnchanter tileEntity) {

        this.tileEntity = tileEntity;

        addOwnSlots();
        addPlayerSlots(playerInventory);

    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 8 + col * 18;
                int y = row * 18 + 84;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 8 + row * 18;
            int y = 58 + 84;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 8;
        int y = 32;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y) {
            @Override
            public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_) {
                tileEntity.sendUpdates();
                super.onSlotChange(p_75220_1_, p_75220_2_);
            }
        });

        TTGhostSlot[] slots = new TTGhostSlot[6];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new TTGhostSlot(ItemStack.EMPTY, i + 1, 177 + (i * 17), 17);
        }
    }


    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
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

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
