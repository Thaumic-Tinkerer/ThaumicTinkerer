/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [26 Jul 2013, 01:56:42 (GMT)]
 */
package vazkii.tinkerer.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import vazkii.tinkerer.lib.LibFeatures;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;

public class TileEntityInterface extends TileEntity implements ISidedInventory, ITankContainer, IPowerReceptor {

	public int x, y, z;

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);

		par1nbtTagCompound.setInteger("xt", x);
		par1nbtTagCompound.setInteger("yt", y);
		par1nbtTagCompound.setInteger("zt", z);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);

		x = par1nbtTagCompound.getInteger("xt");
		y = par1nbtTagCompound.getInteger("yt");
		z = par1nbtTagCompound.getInteger("zt");
	}

	private TileEntity getTile() {
		if(!worldObj.blockExists(x, y, z))
			return null;

		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);

		if(tile == null || Math.abs(x - xCoord) > LibFeatures.INTERFACE_DISTANCE || Math.abs(y - yCoord) > LibFeatures.INTERFACE_DISTANCE || Math.abs(z - zCoord) > LibFeatures.INTERFACE_DISTANCE) {
			y = -1;
			return null;
		}

		return tile;
	}

	@Override
	public int getSizeInventory() {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).getSizeInventory() : 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).getStackInSlot(i) : null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).decrStackSize(i, j) : null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).getStackInSlotOnClosing(i) : null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		TileEntity tile = getTile();
		if (tile instanceof IInventory)
			((IInventory) tile).setInventorySlotContents(i, itemstack);
	}

	@Override
	public String getInvName() {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).getInvName() : "";
	}

	@Override
	public boolean isInvNameLocalized() {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).isInvNameLocalized() : false;
	}

	@Override
	public int getInventoryStackLimit() {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).getInventoryStackLimit() : 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).isStackValidForSlot(i, itemstack) : false;
	}

	// 1.6 stuff:
	/*@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		TileEntity tile = getTile();
		return tile instanceof ILiquidTank ? ((IFluidHandler) tile).fill(from, resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		TileEntity tile = getTile();
		return tile instanceof IFluidHandler ? ((IFluidHandler) tile).drain(from, resource, doDrain) : null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		TileEntity tile = getTile();
		return tile instanceof IFluidHandler ? ((IFluidHandler) tile).drain(from, maxDrain, doDrain) : null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		TileEntity tile = getTile();
		return tile instanceof IFluidHandler ? ((IFluidHandler) tile).canFill(from, fluid) : false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		TileEntity tile = getTile();
		return tile instanceof IFluidHandler ? ((IFluidHandler) tile).canDrain(from, fluid) : false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		TileEntity tile = getTile();
		return tile instanceof IFluidHandler ? ((IFluidHandler) tile).getTankInfo(from) : null;
	}*/

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		TileEntity tile = getTile();
		return tile instanceof ISidedInventory ? ((ISidedInventory) tile).getAccessibleSlotsFromSide(var1) : tile instanceof IInventory ? buildSlotsForLinearInventory((IInventory) tile) : new int[0];
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		TileEntity tile = getTile();
		return tile instanceof ISidedInventory ? ((ISidedInventory) tile).canInsertItem(i, itemstack, j) : tile instanceof IInventory;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		TileEntity tile = getTile();
		return tile instanceof ISidedInventory ? ((ISidedInventory) tile).canExtractItem(i, itemstack, j) : tile instanceof IInventory;
	}

	private int[] buildSlotsForLinearInventory(IInventory inv) {
		int[] slots = new int[inv.getSizeInventory()];
		for (int i = 0; i < slots.length; i++)
			slots[i] = i;

		return slots;
	}

	@Override
	public void doWork() {
		TileEntity tile = getTile();
		if (tile instanceof IPowerReceptor)
			((IPowerReceptor) tile).doWork();
	}

	@Override
	public void setPowerProvider(IPowerProvider provider) {
		TileEntity tile = getTile();
		if (tile instanceof IPowerReceptor)
			((IPowerReceptor) tile).setPowerProvider(provider);
	}

	@Override
	public IPowerProvider getPowerProvider() {
		TileEntity tile = getTile();
		return tile instanceof IPowerReceptor ? ((IPowerReceptor) tile).getPowerProvider() :  null;
	}

	@Override
	public int powerRequest(ForgeDirection from) {
		TileEntity tile = getTile();
		return tile instanceof IPowerReceptor ? ((IPowerReceptor) tile).powerRequest(from) : 0;
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		TileEntity tile = getTile();
		return tile instanceof ITankContainer ? ((ITankContainer) tile).fill(from, resource, doFill) : 0;
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		TileEntity tile = getTile();
		return tile instanceof ITankContainer ? ((ITankContainer) tile).fill(tankIndex, resource, doFill) : 0;
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		TileEntity tile = getTile();
		return tile instanceof ITankContainer ? ((ITankContainer) tile).drain(from, maxDrain, doDrain) : null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		TileEntity tile = getTile();
		return tile instanceof ITankContainer ? ((ITankContainer) tile).drain(tankIndex, maxDrain, doDrain) : null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		TileEntity tile = getTile();
		return tile instanceof ITankContainer ? ((ITankContainer) tile).getTanks(direction) : null;
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		TileEntity tile = getTile();
		return tile instanceof ITankContainer ? ((ITankContainer) tile).getTank(direction, type) : null;
	}
}