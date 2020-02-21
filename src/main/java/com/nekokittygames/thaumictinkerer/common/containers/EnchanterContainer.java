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

/**
 * Enchanter container
 */
public class EnchanterContainer extends ThaumicTContainer<TileEntityEnchanter> {

    /**
     * Constructor for EnchanterContainer
     * @param playerInventory Inventory for the player accessing the inventory
     * @param tileEntity Tile entity being opened
     */
    public EnchanterContainer(IInventory playerInventory, TileEntityEnchanter tileEntity) {
        super(playerInventory, tileEntity);
    }

    /**
     * Adds the Enchanter's slots
     */
    @Override
    protected void addOwnSlots() {
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
            this.addSlotToContainer(slots[i]);
        }
    }
}
