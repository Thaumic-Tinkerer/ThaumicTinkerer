package thaumic.tinkerer.common.core.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by pixlepix on 9/28/14.
 */
public class FakeContainerCrafting extends Container {
    public FakeContainerCrafting(ItemStack item) {
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(new FakeInventory(item), i, 0, 0));
            inventoryItemStacks.set(i, item.copy());
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return false;
    }

    private class FakeInventory implements IInventory {

        ItemStack itemStack;

        FakeInventory(ItemStack item) {
            this.itemStack = item;
        }

        @Override
        public int getSizeInventory() {
            return 1;
        }

        @Override
        public ItemStack getStackInSlot(int p_70301_1_) {
            return itemStack;
        }

        @Override
        public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
            return null;
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
            return itemStack;
        }

        @Override
        public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

        }

        @Override
        public String getInventoryName() {
            return "FakeInventory";
        }

        @Override
        public boolean hasCustomInventoryName() {
            return false;
        }

        @Override
        public int getInventoryStackLimit() {
            return 64;
        }

        @Override
        public void markDirty() {

        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
            return false;
        }

        @Override
        public void openInventory() {

        }

        @Override
        public void closeInventory() {

        }

        @Override
        public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
            return true;
        }
    }

}
