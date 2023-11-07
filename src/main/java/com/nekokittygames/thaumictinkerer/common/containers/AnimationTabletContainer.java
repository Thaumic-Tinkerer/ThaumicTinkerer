package com.nekokittygames.thaumictinkerer.common.containers;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityAnimationTablet;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Animation tablet container
 */
public class AnimationTabletContainer extends ThaumicTContainer<TileEntityAnimationTablet> {


    /**
     * Constructor for AnimationTabletContainer
     * @param playerInventory Inventory for the player accessing the inventory
     * @param tileEntity Tile entity being opened
     */
    public AnimationTabletContainer(IInventory playerInventory, TileEntityAnimationTablet tileEntity) {
        super(playerInventory, tileEntity);
    }

    /**
     * Adds the Animation Tablet's slots
     */
    @Override
    protected void addOwnSlots() {
        IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int x = 80;
        int y = 15;
        this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y) {
            @Override
            public void onSlotChange(@Nonnull ItemStack p_75220_1_, @Nonnull ItemStack p_75220_2_) {
                tileEntity.sendUpdates();
                super.onSlotChange(p_75220_1_, p_75220_2_);
            }
        });
    }
}
