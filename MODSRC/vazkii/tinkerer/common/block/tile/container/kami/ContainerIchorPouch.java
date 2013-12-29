/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [Dec 29, 2013, 10:37:08 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.container.kami;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.common.container.InventoryFocusPouch;
import thaumcraft.common.items.wands.ItemFocusPouch;
import vazkii.tinkerer.common.block.tile.container.ContainerPlayerInv;

public class ContainerIchorPouch extends ContainerPlayerInv {

	private static class InventoryIchorPouch extends InventoryFocusPouch {

		public InventoryIchorPouch(Container par1Container) {
			super(par1Container);
			stackList = new ItemStack[13 * 9];
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public boolean isItemValidForSlot(int i, ItemStack itemstack) {
			return true;
		}

	}

	public IInventory inv = new InventoryIchorPouch(this);
	EntityPlayer player;
	ItemStack pouch;
	int blockSlot;

	public ContainerIchorPouch(EntityPlayer player) {
		super(player.inventory);

		this.player = player;
		pouch = player.getCurrentEquippedItem();
		blockSlot = player.inventory.currentItem + 27 + (13 * 9);
		
		for(int y = 0; y < 9; y++)
			for(int x = 0; x < 13; x++)
				addSlotToContainer(new Slot(inv, y * 13 + x, -37 + 9 + x * 18, -42 + 6 + y * 18));
		initPlayerInv();

		if (!player.worldObj.isRemote)
			try{
				((InventoryIchorPouch) inv).stackList = ((ItemFocusPouch) pouch.getItem()).getInventory(pouch);
			} catch (Exception e) { }
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slot) {
		if (slot == this.blockSlot)
			return null;

		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			if (slot < (13 * 9)) {
				if (!inv.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 18, inventorySlots.size(), true))
					return null;
			}
			else if (!inv.isItemValidForSlot(slot, stackInSlot) || !mergeItemStack(stackInSlot, 0, 18, false)) {
				return null;
			}
			if (stackInSlot.stackSize == 0)
				slotObject.putStack(null);
			else slotObject.onSlotChanged();
		}
		
		return stack;
	}


	public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer par4EntityPlayer)
	{
		if (par1 == this.blockSlot) {
			return null;
		}
		return super.slotClick(par1, par2, par3, par4EntityPlayer);
	}

	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		if (!player.worldObj.isRemote) {
			((ItemFocusPouch) pouch.getItem()).setInventory(pouch, ((InventoryIchorPouch) inv).stackList);
			if (player == null)
				return;
			if (player.getHeldItem() != null && player.getHeldItem().isItemEqual(pouch))
				player.setCurrentItemOrArmor(0, this.pouch);
			
			player.inventory.onInventoryChanged();
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public int getInvXStart() {
		return 8;
	}
	
	@Override
	public int getInvYStart() {
		return 130;
	}
}
