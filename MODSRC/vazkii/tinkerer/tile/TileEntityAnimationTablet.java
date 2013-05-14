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
 * File Created @ [13 May 2013, 19:36:07 (GMT)]
 */
package vazkii.tinkerer.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketAnimationTabletSync;

public class TileEntityAnimationTablet extends TileEntity implements IInventory {

	private static final String TAG_LEFT_CLICK = "leftClick";
	private static final String TAG_REDSTONE = "redstone";

	ItemStack[] inventorySlots = new ItemStack[1];
	public double ticksExisted = 0;

	public boolean leftClick = true;
	public boolean redstone = false;

	@Override
	public void updateEntity() {
		ticksExisted++;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		leftClick = par1NBTTagCompound.getBoolean(TAG_LEFT_CLICK);
		redstone = par1NBTTagCompound.getBoolean(TAG_REDSTONE);

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

        par1NBTTagCompound.setBoolean(TAG_LEFT_CLICK, leftClick);
        par1NBTTagCompound.setBoolean(TAG_REDSTONE, redstone);

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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
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
		return PacketManager.generatePacket(new PacketAnimationTabletSync(this));
	}

}
