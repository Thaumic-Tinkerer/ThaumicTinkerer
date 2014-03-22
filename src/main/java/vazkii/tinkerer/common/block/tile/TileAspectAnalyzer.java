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
 * File Created @ [Dec 11, 2013, 10:33:26 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import vazkii.tinkerer.common.lib.LibBlockNames;

import java.util.ArrayList;
import java.util.List;

public class TileAspectAnalyzer extends TileEntity implements IInventory, IPeripheral, IMovableTile {

	ItemStack[] inventorySlots = new ItemStack[1];

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_INT_ARRAY);
		inventorySlots = new ItemStack[getSizeInventory()];
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.getCompoundTagAt(var3);
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
		return inventorySlots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (inventorySlots[i] != null) {
			ItemStack stackAt;

			if (inventorySlots[i].stackSize <= j) {
				stackAt = inventorySlots[i];
				inventorySlots[i] = null;
				return stackAt;
			} else {
				stackAt = inventorySlots[i].splitStack(j);

				if (inventorySlots[i].stackSize == 0)
					inventorySlots[i] = null;

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
    public String getInventoryName() {
        return LibBlockNames.ASPECT_ANALYZER;
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
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
	}

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public String getType() {
		return "tt_aspectanalyzer";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "hasItem", "itemHasAspects", "getAspects", "getAspectCount" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		ItemStack stack = getStackInSlot(0);
		AspectList aspects = null;
		if(stack != null) {
			aspects = ThaumcraftCraftingManager.getObjectTags(stack);
			aspects = ThaumcraftCraftingManager.getBonusTags(stack, aspects);
		}

		switch(method) {
			case 0 : return new Object[] { stack != null };
			case 1 : return new Object[] { aspects != null && aspects.size() > 0 };
			case 2 : {
				List returnStuff = new ArrayList();
				if(aspects == null)
					return new String[0];

				for(Aspect aspect : aspects.getAspectsSorted())
					returnStuff.add(aspect.getTag());

				return returnStuff.toArray();
			}
			case 3 : {
				String aspectName = (String) arguments[0];
				Aspect aspect = Aspect.getAspect(aspectName);

				if(aspects == null)
					return new Object[] { 0 };

				return new Object[] { aspects.getAmount(aspect) };
			}
 		}

		return null;
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	public void detach(IComputerAccess computer) {
		// NO-OP
	}
	@Override
	public boolean prepareToMove() {
		return true;
	}

	@Override
	public void doneMoving() {

	}

}
