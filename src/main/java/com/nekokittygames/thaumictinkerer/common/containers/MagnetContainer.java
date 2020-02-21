package com.nekokittygames.thaumictinkerer.common.containers;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMagnet;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Magnet container
 */
public class MagnetContainer extends ThaumicTContainer<TileEntityMagnet> {

    /**
     * Constructor for MagnetContainer
     * @param playerInventory Inventory for the player accessing the inventory
     * @param tileEntity Tile entity being opened
     */
    public MagnetContainer(IInventory playerInventory, TileEntityMagnet tileEntity) {
        super(playerInventory, tileEntity);
    }

    /**
     * Adds the Magnet's own slots
     */
    @Override
    protected void addOwnSlots() {
        IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 54;
        int y = 38;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y) {
            @Override
            public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_) {
                tileEntity.sendUpdates();
                super.onSlotChange(p_75220_1_, p_75220_2_);
            }
        });
    }
}
