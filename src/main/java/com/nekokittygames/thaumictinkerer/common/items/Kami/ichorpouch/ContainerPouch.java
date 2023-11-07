package com.nekokittygames.thaumictinkerer.common.items.Kami.ichorpouch;

import com.nekokittygames.thaumictinkerer.common.utils.ContainerPlayerInv;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import thaumcraft.common.items.casters.ItemFocusPouch;

public class ContainerPouch extends ContainerPlayerInv implements IInventoryChangedListener {
    public IInventory inv = new InventoryPouch(this);
    EntityPlayer player;
    ItemStack pouch;
    int blockSlot;
    public ContainerPouch(EntityPlayer player) {
        super(player.inventory);

        this.player = player;
        pouch = player.inventory.getCurrentItem();
        blockSlot = player.inventory.currentItem + 27 + 13 * 9;

        for (int y = 0; y < 9; y++)
            for (int x = 0; x < 13; x++)
                addSlotToContainer(new SlotNoPouches(inv, y * 13 + x, 12 + x * 18, 8 + y * 18));
        initPlayerInv();

        if (!player.world.isRemote)
            try {
                NonNullList<ItemStack> list = ((IchorPouch) pouch.getItem()).getInventory(pouch);
                for (int i = 0; i < list.size(); i++)
                    this.inv.setInventorySlotContents(i, (ItemStack)list.get(i));
            } catch (Exception e) {
            }
    }
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot) {
        if (slot == this.blockSlot) return ItemStack.EMPTY;
        ItemStack stack = ItemStack.EMPTY;
        Slot slotObject = this.inventorySlots.get(slot);


        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            if (slot < 117) {
                if (!this.inv.isItemValidForSlot(slot, stackInSlot) ||
                        !mergeItemStack(stackInSlot, 117, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.inv.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 0, 117, false)) {
                return ItemStack.EMPTY;
            }

            if (stackInSlot.getCount() == 0) {
                slotObject.putStack(ItemStack.EMPTY);
            } else {
                slotObject.onSlotChanged();
            }
        }

        return stack;
    }

    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        if (!player.world.isRemote) {

            NonNullList<ItemStack> list = NonNullList.withSize(117, ItemStack.EMPTY);
            for (int a = 0; a < list.size(); a++)
                list.set(a, this.inv.getStackInSlot(a));
            if (this.pouch.getItem() instanceof ItemFocusPouch)
                ((ItemFocusPouch)this.pouch.getItem()).setInventory(this.pouch, list);
            if (this.player == null)
                return;  if (this.player.getHeldItem(this.player.getActiveHand()).isItemEqual(this.pouch))
                this.player.setHeldItem(this.player.getActiveHand(), this.pouch);
            this.player.inventory.markDirty();
        }
    }

    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
        if (slotId == this.blockSlot) return ItemStack.EMPTY;
        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public int getInvXStart() {
        return 48;
    }

    @Override
    public int getInvYStart() {
        return 177;
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {
        detectAndSendChanges();
    }

    private static class InventoryPouch extends InventoryBasic {

        public InventoryPouch(IInventoryChangedListener par1Container) {
            super("container.focuspouch", false, 117);
            addInventoryChangeListener(par1Container);
        }

        @Override
        public int getInventoryStackLimit() {
            return 64;
        }

        @Override
        public boolean isItemValidForSlot(int i, ItemStack itemstack) {
            return itemstack != null && !(itemstack.getItem() instanceof ItemFocusPouch);
        }
    }
}
