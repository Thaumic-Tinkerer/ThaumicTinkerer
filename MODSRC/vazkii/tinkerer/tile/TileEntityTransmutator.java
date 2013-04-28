/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [28 Apr 2013, 17:32:35 (GMT)]
 */
package vazkii.tinkerer.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.lib.LibBlockNames;

public class TileEntityTransmutator extends TileEntity implements ISidedInventory, net.minecraftforge.common.ISidedInventory {

	ItemStack[] inventorySlots = new ItemStack[2];

	@Override
	public int getSizeInventory() {
		return inventorySlots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventorySlots[i];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (inventorySlots[par1] != null) {
            ItemStack stackAt;

            if (inventorySlots[par1].stackSize <= par2) {
                stackAt = inventorySlots[par1];
                inventorySlots[par1] = null;
                return stackAt;
            } else {
                stackAt = inventorySlots[par1].splitStack(par2);

                if (inventorySlots[par1].stackSize == 0)
                    inventorySlots[par1] = null;

                return stackAt;
            }
        }

        return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return getStackInSlot(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventorySlots[i] = itemstack;
	}

	@Override
	public String getInvName() {
		return LibBlockNames.TRANSMUTATOR_D;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

	@Override
	public void openChest() {
		// NO-OP
	}

	@Override
	public void closeChest() {
		// NO-OP
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return i == 1;
	}

	@Override
	public int[] getSizeInventorySide(int var1) {
		return var1 == 1 ? new int[] { 1 } : new int[0];
	}

	@Override // Put
	public boolean func_102007_a(int i, ItemStack itemstack, int j) {
		return i == 1;
	}

	@Override // Take
	public boolean func_102008_b(int i, ItemStack itemstack, int j) {
		return func_102007_a(i, itemstack, j);
	}

	@Override
	@Deprecated
	public int getStartInventorySide(ForgeDirection side) {
		return 1;
	}

	@Override
	@Deprecated
	public int getSizeInventorySide(ForgeDirection side) {
		return 1;
	}

}
