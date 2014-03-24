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
 * File Created @ [8 Sep 2013, 19:01:20 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.transvector;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import cofh.api.energy.IEnergyHandler;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.*;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import vazkii.tinkerer.common.lib.LibFeatures;

public class TileTransvectorInterface extends TileTransvector implements ISidedInventory, IFluidHandler, IPowerReceptor, IEnergySink, IEnergyHandler, IAspectContainer, IEssentiaTransport, IPeripheral {

	private boolean addedToICEnergyNet = false;

	@Override
	public void updateEntity() {
        if(worldObj.getTotalWorldTime()%100==0)
        {
            worldObj.notifyBlockChange(xCoord,yCoord,zCoord,worldObj.getBlockId(xCoord,yCoord,zCoord));
        }
		if(!addedToICEnergyNet && !worldObj.isRemote) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToICEnergyNet = true;
		}
	}

	@Override
	public int getMaxDistance() {
		return LibFeatures.INTERFACE_DISTANCE;
	}

	@Override
	public void invalidate() {
		removeFromIC2EnergyNet();
		super.invalidate();
	}

	@Override
	public void onChunkUnload() {
		removeFromIC2EnergyNet();
	}

	private void removeFromIC2EnergyNet() {
		if(addedToICEnergyNet && !worldObj.isRemote) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			addedToICEnergyNet = false;
		}
	}

	@Override
	public void onInventoryChanged() {
		TileEntity tile = getTile();
		if(tile != null)
			tile.onInventoryChanged();
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		TileEntity tile = getTile();
		return tile instanceof IInventory ? ((IInventory) tile).isItemValidForSlot(i, itemstack) : false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		TileEntity tile = getTile();
		return tile instanceof IFluidTank ? ((IFluidHandler) tile).fill(from, resource, doFill) : 0;
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
	}

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

	public static int[] buildSlotsForLinearInventory(IInventory inv) {
		int[] slots = new int[inv.getSizeInventory()];
		for (int i = 0; i < slots.length; i++)
			slots[i] = i;

		return slots;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		TileEntity tile = getTile();
		return tile instanceof IPowerReceptor ? ((IPowerReceptor) tile).getPowerReceiver(side) : null;
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		TileEntity tile = getTile();
		if (tile instanceof IPowerReceptor)
			((IPowerReceptor) tile).doWork(workProvider);
	}

	@Override
	public World getWorld() {
		return worldObj;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		TileEntity tile = getTile();
		return tile instanceof IEnergySink ? ((IEnergySink) tile).acceptsEnergyFrom(emitter, direction) : false;
	}

	@Override
	public double demandedEnergyUnits() {
		TileEntity tile = getTile();
		return tile instanceof IEnergySink ? ((IEnergySink) tile).demandedEnergyUnits() : 0;
	}

	@Override
	public double injectEnergyUnits(ForgeDirection directionFrom, double amount) {
		TileEntity tile = getTile();
		return tile instanceof IEnergySink ? ((IEnergySink) tile).injectEnergyUnits(directionFrom, amount) : 0;
	}

	@Override
	public int getMaxSafeInput() {
		TileEntity tile = getTile();
		return tile instanceof IEnergySink ? ((IEnergySink) tile).getMaxSafeInput() : 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		TileEntity tile = getTile();
		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).receiveEnergy(from, maxReceive, simulate) : 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		TileEntity tile = getTile();
		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).extractEnergy(from, maxExtract, simulate) : 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		TileEntity tile = getTile();
		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).canInterface(from) : false;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		TileEntity tile = getTile();
		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).getEnergyStored(from) : 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		TileEntity tile = getTile();
		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).getMaxEnergyStored(from) : 0;
	}

	@Override
	public AspectList getAspects() {
		TileEntity tile = getTile();
		return tile instanceof IAspectContainer ? ((IAspectContainer) tile).getAspects() : null;
	}

	@Override
	public void setAspects(AspectList paramAspectList) {
		TileEntity tile = getTile();
		if(tile != null)
			((IAspectContainer) tile).setAspects(paramAspectList);
	}

	@Override
	public boolean doesContainerAccept(Aspect paramAspect) {
		TileEntity tile = getTile();
		return tile instanceof IAspectContainer ? ((IAspectContainer) tile).doesContainerAccept(paramAspect) : false;
	}

	@Override
	public int addToContainer(Aspect paramAspect, int paramInt) {
		TileEntity tile = getTile();
		return tile instanceof IAspectContainer ? ((IAspectContainer) tile).addToContainer(paramAspect, paramInt) : 0;
	}

	@Override
	public boolean takeFromContainer(Aspect paramAspect, int paramInt) {
		TileEntity tile = getTile();
		return tile instanceof IAspectContainer ? ((IAspectContainer) tile).takeFromContainer(paramAspect, paramInt) : false;
	}

	@Override
	public boolean takeFromContainer(AspectList paramAspectList) {
		TileEntity tile = getTile();
		return tile instanceof IAspectContainer ? ((IAspectContainer) tile).takeFromContainer(paramAspectList) : false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect paramAspect, int paramInt) {
		TileEntity tile = getTile();
		return tile instanceof IAspectContainer ? ((IAspectContainer) tile).doesContainerContainAmount(paramAspect, paramInt) : false;
	}

	@Override
	public boolean doesContainerContain(AspectList paramAspectList) {
		TileEntity tile = getTile();
		return tile instanceof IAspectContainer ? ((IAspectContainer) tile).doesContainerContain(paramAspectList) : false;
	}

	@Override
	public int containerContains(Aspect paramAspect) {
		TileEntity tile = getTile();
		return tile instanceof IAspectContainer ? ((IAspectContainer) tile).containerContains(paramAspect) : 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection paramForgeDirection) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).isConnectable(paramForgeDirection) : false;
	}

	@Override
	public boolean canInputFrom(ForgeDirection paramForgeDirection) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).canInputFrom(paramForgeDirection) : false;
	}

	@Override
	public boolean canOutputTo(ForgeDirection paramForgeDirection) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).canOutputTo(paramForgeDirection) : false;
	}

	@Override
	public void setSuction(Aspect paramAspect, int paramInt) {
		TileEntity tile = getTile();
		if(tile instanceof IEssentiaTransport)
			((IEssentiaTransport) tile).setSuction(paramAspect, paramInt);
	}

	@Override
	public int takeVis(Aspect paramAspect, int paramInt) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).takeVis(paramAspect, paramInt) : 0;
	}

	@Override
	public int getMinimumSuction() {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).getMinimumSuction() : 0;
	}

	@Override
	public boolean renderExtendedTube() {
		return false;
	}

	@Override
	public int addVis(Aspect arg0, int arg1) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).addVis(arg0, arg1) : 0;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection arg0) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).getEssentiaAmount(arg0) : 0;
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection arg0) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).getEssentiaType(arg0) : null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection arg0) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).getSuctionAmount(arg0) : 0;
	}
	
	@Override
	public Aspect getSuctionType(ForgeDirection arg0) {
		TileEntity tile = getTile();
		return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).getSuctionType(arg0) : null;
	}

	@Override
	public String getType() {
		return getTile() instanceof IPeripheral ? ((IPeripheral) getTile()).getType() : "Transvector Interface Unconnected Peripherad";
	}

	@Override
	public String[] getMethodNames() {
		return getTile() instanceof IPeripheral ? ((IPeripheral) getTile()).getMethodNames() : new String[0];
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		return getTile() instanceof IPeripheral ? ((IPeripheral) getTile()).callMethod(computer, context, method, arguments) : new Object[0];
	}


	@Override
	public void attach(IComputerAccess computer) {
		if(getTile() instanceof  IPeripheral){
			((IPeripheral) getTile()).attach(computer);
		}
	}

	@Override
	public void detach(IComputerAccess computer) {
		if(getTile() instanceof  IPeripheral){
			((IPeripheral) getTile()).detach(computer);
		}
	}

    @Override
    public boolean equals(IPeripheral other) {
        if(getTile() instanceof  IPeripheral) {
            ((IPeripheral) getTile()).equals(other);
        }
        return false;
    }
}