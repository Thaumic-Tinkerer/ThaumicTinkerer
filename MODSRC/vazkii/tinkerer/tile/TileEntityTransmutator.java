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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.common.aura.AuraManager;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileInfusionWorkbench;
import vazkii.tinkerer.inventory.slot.SlotTransmutator;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketTransmutatorSync;

public class TileEntityTransmutator extends TileInfusionWorkbench implements ISidedInventory {

	ItemStack[] inventorySlots = new ItemStack[2];
	public double ticksExisted = 0;

	@Override
	public void updateEntity() {
		ItemStack stack = getStackInSlot(0);
		partTags = stack == null ? null : ThaumcraftCraftingManager.getObjectTags(stack);
		findSources(true);

		super.updateEntity();
		if(getStackInSlot(0) != null)
			ticksExisted++;

		if(!worldObj.isRemote) {
			if(hasAspectsAndVis()) {
				ItemStack setStack = getStackInSlot(0).copy();
				if(setStack.hasTagCompound()) {
					NBTTagCompound cmp = setStack.getTagCompound();
					cmp.removeTag("ench");
				}

				setInventorySlotContents(1, setStack);
			}
			else setInventorySlotContents(1, null);
		}
	}

	public boolean hasAspectsAndVis() {
		if(getStackInSlot(0) == null)
			return false;

		ObjectTags tags = ThaumcraftCraftingManager.getObjectTags(getStackInSlot(0));
		int value = SlotTransmutator.getTotalAspectValue(tags);
		boolean hasVis = value == 1 ? true : AuraManager.decreaseClosestAura(worldObj, xCoord, yCoord, zCoord, value * 2, false);
		if(!hasVis)
			return false;

		for(EnumTag tag : tags.getAspects()) {
			int amount = tags.getAmount(tag);
			int transAmount = foundTags.getAmount(tag);
			if(transAmount < amount * 4)
				return false;
		}

		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		inventorySlots = new ItemStack[getSizeInventory()];
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			if (var5 >= 0 && var5 < inventorySlots.length)
				inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4);
		}
	}

	@Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);

    	NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < inventorySlots.length; ++var3) {
            if (inventorySlots[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                inventorySlots[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
    }

	@Override
	public int getSizeInventory() {
		return inventorySlots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if(i >= inventorySlots.length)
			return null;
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
		return 1;
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
	public Packet getDescriptionPacket() {
		return PacketManager.generatePacket(new PacketTransmutatorSync(this));
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return i == 1;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return var1 == 1 ? new int[] { 1 } : new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return i == 1;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return canInsertItem(i, itemstack, j);
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
