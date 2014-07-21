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
package thaumic.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumic.tinkerer.common.lib.LibBlockNames;

import java.util.HashMap;
import java.util.Map;

@Optional.InterfaceList({
		@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
		@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft")
})
public class TileAspectAnalyzer extends TileEntity implements IInventory, SimpleComponent, IPeripheral, IMovableTile {

	ItemStack[] inventorySlots = new ItemStack[1];

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);

		NBTTagList var2 = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		inventorySlots = new ItemStack[getSizeInventory()];
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = var2.getCompoundTagAt(var3);
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
				var4.setByte("Slot", (byte) var3);
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
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
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
		return new String[]{ "hasItem", "itemHasAspects", "getAspects", "getAspectCount" };
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch (method) {
			case 0:
				return hasItemMethod();
			case 1:
				return itemHasAspectsMethod();
			case 2:
				return getAspectsMethod();
			case 3:
				return getAspectsAmountsMethod();
		}

		return null;
	}

	public Object[] hasItemMethod() {
		ItemStack stack = getStackInSlot(0);
		return new Object[]{ stack != null };
	}

	public AspectList getAspectList() {
		ItemStack stack = getStackInSlot(0);
		AspectList aspects = null;
		if (stack != null) {

			aspects = ThaumcraftCraftingManager.getObjectTags(stack);
			aspects = ThaumcraftCraftingManager.getBonusTags(stack, aspects);
		}

		return aspects;
	}

	public Object[] itemHasAspectsMethod() {
		AspectList aspects = getAspectList();
		return new Object[]{ aspects != null && aspects.size() > 0 };
	}

	public Object[] getAspectsMethod() {
		AspectList aspects = getAspectList();
		Map<Double, String> retVals = new HashMap<Double, String>();
		if (aspects == null)
			return new Object[]{ retVals };
		double i = 1;
		for (Aspect aspect : aspects.getAspectsSorted())
			retVals.put(i++, aspect.getTag());
		return new Object[]{ retVals };
	}

	public Object[] getAspectsAmountsMethod() {
		AspectList aspects = getAspectList();
		Map<String, Double> retVals = new HashMap<String, Double>();
		if (aspects == null)
			return new Object[]{ retVals };
		for (Aspect aspect : aspects.getAspectsSorted())
			retVals.put(aspect.getTag(), (double) aspects.getAmount(aspect));
		return new Object[]{ retVals };
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public void attach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public void detach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	@Optional.Method(modid = "ComputerCraft")
	public boolean equals(IPeripheral other) {
		return this.equals((Object) other);
	}

	@Override
	public boolean prepareToMove() {
		return true;
	}

	@Override
	public void doneMoving() {

	}

	@Override
	public String getComponentName() {
		return getType();
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] greet(Context context, Arguments args) {
		return new Object[]{ String.format("Hello, %s!", args.checkString(0)) };
	}

	@Callback(doc = "function():boolean -- Whether this inventory contains an item")
	@Optional.Method(modid = "OpenComputers")
	public Object[] hasItem(Context context, Arguments args) {
		return hasItemMethod();
	}

	@Callback(doc = "function():boolean -- Whether the item contains aspects")
	@Optional.Method(modid = "OpenComputers")
	public Object[] itemHasAspects(Context context, Arguments args) {
		return itemHasAspectsMethod();
	}

	@Callback(doc = "function():table -- Returns a list of all available aspects")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getAspects(Context context, Arguments args) {
		return getAspectsMethod();
	}

	@Callback(doc = "function():table -- returns a mapping of all aspect counts")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getAspectCount(Context context, Arguments args) {
		return getAspectsAmountsMethod();
	}
}
