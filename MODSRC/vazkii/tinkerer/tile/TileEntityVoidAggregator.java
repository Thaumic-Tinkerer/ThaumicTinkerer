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
 * File Created @ [2 Jun 2013, 21:01:53 (GMT)]
 */
package vazkii.tinkerer.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.common.Thaumcraft;
import vazkii.tinkerer.lib.LibBlockNames;

public class TileEntityVoidAggregator extends TileEntity implements IInventory {

	ItemStack[] inventorySlots = new ItemStack[2];

	public double ticksExisted;
	public int work = 0;

	@Override
	public void updateEntity() {
		++ticksExisted;

		if (worldObj.isRemote /*&& work > 0**/ && ticksExisted % 3 == 0)
				Thaumcraft.proxy.wispFX3(worldObj, xCoord + 0.3F + Math.random() * 0.4F, yCoord + 0.4F, zCoord + 0.3F + Math.random() * 0.4F, xCoord + 0.45F + Math.random() * 0.4F, yCoord + 0.5F, zCoord + 0.4F + worldObj.rand.nextFloat() * 0.2F, 0.2F, 5, true, -0.03F);

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
		return LibBlockNames.FLUX_COLLECTOR_D;
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

	/*@Override
	public Packet getDescriptionPacket() {
		return PacketManager.generatePacket(new PacketFluxCollectorSync(this));
	}*/

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

}
