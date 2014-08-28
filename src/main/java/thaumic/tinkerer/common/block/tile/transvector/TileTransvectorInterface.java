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
package thaumic.tinkerer.common.block.tile.transvector;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumic.tinkerer.common.compat.IndustrialcraftUnloadHelper;
import thaumic.tinkerer.common.lib.LibFeatures;

@Optional.InterfaceList({@Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft"),
        @Optional.Interface(iface = "buildcraft.api.power.IPowerReceptor", modid = "BuildCraft|Energy"),
        @Optional.Interface(iface = "cofh.api.energy.IEnergyHandler", modid = "CoFHLib"),
        @Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "IC2")})

public class TileTransvectorInterface extends TileTransvector implements ISidedInventory, IEnergySink, IFluidHandler, IPowerReceptor, IEnergyHandler, IAspectContainer, IEssentiaTransport, IPeripheral {

    public boolean addedToICEnergyNet = false;

    @Override
    public void updateEntity() {
        if (worldObj.getTotalWorldTime() % 100 == 0) {
            worldObj.notifyBlockChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
        }
        if (!addedToICEnergyNet && !worldObj.isRemote && Loader.isModLoaded("IC2")) {

            IndustrialcraftUnloadHelper.addToIC2EnergyNet(this);
            addedToICEnergyNet = true;
        }
    }

    @Override
    public int getMaxDistance() {
        return LibFeatures.INTERFACE_DISTANCE;
    }

    @Override
    public void invalidate() {
        if (Loader.isModLoaded("IC2")) {
            IndustrialcraftUnloadHelper.removeFromIC2EnergyNet(this);
        }
        super.invalidate();
    }

    @Override
    public void onChunkUnload() {

        if (Loader.isModLoaded("IC2")) {
            IndustrialcraftUnloadHelper.removeFromIC2EnergyNet(this);
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        TileEntity tile = getTile();
        if (tile != null)
            tile.markDirty();
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
    public String getInventoryName() {
        TileEntity tile = getTile();
        return tile instanceof IInventory ? ((IInventory) tile).getInventoryName() : "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        TileEntity tile = getTile();
        return tile instanceof IInventory && ((IInventory) tile).hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        TileEntity tile = getTile();
        return tile instanceof IInventory ? ((IInventory) tile).getInventoryStackLimit() : 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        TileEntity tile = getTile();
        return tile instanceof IInventory && ((IInventory) tile).isUseableByPlayer(entityplayer);
    }

    @Override
    public void openInventory() {
        TileEntity tile = getTile();
        if (tile instanceof IInventory)
            ((IInventory) tile).openInventory();
    }

    @Override
    public void closeInventory() {
        TileEntity tile = getTile();
        if (tile instanceof IInventory)
            ((IInventory) tile).closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        TileEntity tile = getTile();
        return tile instanceof IInventory && ((IInventory) tile).isItemValidForSlot(i, itemstack);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        TileEntity tile = getTile();
        return tile instanceof IFluidHandler ? ((IFluidHandler) tile).fill(from, resource, doFill) : 0;
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
        return tile instanceof IFluidHandler && ((IFluidHandler) tile).canFill(from, fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        TileEntity tile = getTile();
        return tile instanceof IFluidHandler && ((IFluidHandler) tile).canDrain(from, fluid);
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
    @Optional.Method(modid = "BuildCraft|Energy")
    public PowerReceiver getPowerReceiver(ForgeDirection side) {
        TileEntity tile = getTile();
        return tile instanceof IPowerReceptor ? ((IPowerReceptor) tile).getPowerReceiver(side) : null;
    }

    @Override
    @Optional.Method(modid = "BuildCraft|Energy")
    public void doWork(PowerHandler workProvider) {
        TileEntity tile = getTile();
        if (tile instanceof IPowerReceptor)
            ((IPowerReceptor) tile).doWork(workProvider);
    }

    @Override
    public World getWorld() {
        return worldObj;
    }

    @Optional.Method(modid = "IC2")
    @Override
    public double getDemandedEnergy() {
        TileEntity tile = getTile();
        return tile instanceof IEnergySink ? ((IEnergySink) tile).getDemandedEnergy() : 0;
    }


    @Optional.Method(modid = "IC2")
    @Override
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {

        TileEntity tile = getTile();
        return tile instanceof IEnergySink ? ((IEnergySink) tile).injectEnergy(directionFrom, amount, voltage) : 0;
    }


    @Optional.Method(modid = "IC2")
    @Override
    public int getSinkTier() {
        TileEntity tile = getTile();
        return tile instanceof IEnergySink ? ((IEnergySink) tile).getSinkTier() : 0;
    }


    @Override
    @Optional.Method(modid = "CoFHLib")
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        TileEntity tile = getTile();
        return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).receiveEnergy(from, maxReceive, simulate) : 0;
    }

    @Override
    @Optional.Method(modid = "CoFHLib")
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        TileEntity tile = getTile();
        return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).extractEnergy(from, maxExtract, simulate) : 0;
    }


    @Override
    @Optional.Method(modid = "CoFHLib")
    public int getEnergyStored(ForgeDirection from) {
        TileEntity tile = getTile();
        return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).getEnergyStored(from) : 0;
    }

    @Override
    @Optional.Method(modid = "CoFHLib")
    public int getMaxEnergyStored(ForgeDirection from) {
        TileEntity tile = getTile();
        return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).getMaxEnergyStored(from) : 0;
    }

    @Override
    @Optional.Method(modid = "CoFHLib")
    public boolean canConnectEnergy(ForgeDirection from) {
        TileEntity tile = getTile();
        return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).canConnectEnergy(from) : false;
    }

    @Override
    public AspectList getAspects() {
        TileEntity tile = getTile();
        return tile instanceof IAspectContainer ? ((IAspectContainer) tile).getAspects() : null;
    }

    @Override
    public void setAspects(AspectList paramAspectList) {
        TileEntity tile = getTile();
        if (tile != null)
            ((IAspectContainer) tile).setAspects(paramAspectList);
    }

    @Override
    public boolean doesContainerAccept(Aspect paramAspect) {
        TileEntity tile = getTile();
        return tile instanceof IAspectContainer && ((IAspectContainer) tile).doesContainerAccept(paramAspect);
    }

    @Override
    public int addToContainer(Aspect paramAspect, int paramInt) {
        TileEntity tile = getTile();
        return tile instanceof IAspectContainer ? ((IAspectContainer) tile).addToContainer(paramAspect, paramInt) : 0;
    }

    @Override
    public boolean takeFromContainer(Aspect paramAspect, int paramInt) {
        TileEntity tile = getTile();
        return tile instanceof IAspectContainer && ((IAspectContainer) tile).takeFromContainer(paramAspect, paramInt);
    }

    @Override
    public boolean takeFromContainer(AspectList paramAspectList) {
        TileEntity tile = getTile();
        return tile instanceof IAspectContainer && ((IAspectContainer) tile).takeFromContainer(paramAspectList);
    }

    @Override
    public boolean doesContainerContainAmount(Aspect paramAspect, int paramInt) {
        TileEntity tile = getTile();
        return tile instanceof IAspectContainer && ((IAspectContainer) tile).doesContainerContainAmount(paramAspect, paramInt);
    }

    @Override
    public boolean doesContainerContain(AspectList paramAspectList) {
        TileEntity tile = getTile();
        return tile instanceof IAspectContainer && ((IAspectContainer) tile).doesContainerContain(paramAspectList);
    }

    @Override
    public int containerContains(Aspect paramAspect) {
        TileEntity tile = getTile();
        return tile instanceof IAspectContainer ? ((IAspectContainer) tile).containerContains(paramAspect) : 0;
    }

    @Override
    public boolean isConnectable(ForgeDirection forgeDirection) {
        TileEntity tile = getTile();
        return tile instanceof IEssentiaTransport && ((IEssentiaTransport) tile).isConnectable(forgeDirection);
    }

    @Override
    public boolean canInputFrom(ForgeDirection forgeDirection) {
        TileEntity tile = getTile();
        return tile instanceof IEssentiaTransport && ((IEssentiaTransport) tile).canInputFrom(forgeDirection);
    }

    @Override
    public boolean canOutputTo(ForgeDirection forgeDirection) {
        TileEntity tile = getTile();
        return tile instanceof IEssentiaTransport && ((IEssentiaTransport) tile).canOutputTo(forgeDirection);
    }

    @Override
    public void setSuction(Aspect paramAspect, int paramInt) {
        TileEntity tile = getTile();
        if (tile instanceof IEssentiaTransport)
            ((IEssentiaTransport) tile).setSuction(paramAspect, paramInt);
    }

    @Override
    public Aspect getSuctionType(ForgeDirection forgeDirection) {
        return null;
    }

    @Override
    public int getSuctionAmount(ForgeDirection forgeDirection) {
        return 0;
    }

    @Override
    public int takeEssentia(Aspect paramAspect, int paramInt, ForgeDirection forgeDirection) {
        TileEntity tile = getTile();
        return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).takeEssentia(paramAspect, paramInt, forgeDirection) : 0;
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
    public int addEssentia(Aspect arg0, int arg1, ForgeDirection forgeDirection) {
        TileEntity tile = getTile();
        return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).addEssentia(arg0, arg1, forgeDirection) : 0;
    }

    @Override
    public Aspect getEssentiaType(ForgeDirection forgeDirection) {

        TileEntity tile = getTile();
        return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).getEssentiaType(forgeDirection) : null;
    }

    @Override
    public int getEssentiaAmount(ForgeDirection forgeDirection) {
        TileEntity tile = getTile();
        return tile instanceof IEssentiaTransport ? ((IEssentiaTransport) tile).getEssentiaAmount(forgeDirection) : 0;
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String getType() {
        return getTile() instanceof IPeripheral ? ((IPeripheral) getTile()).getType() : "Transvector Interface Unconnected Peripherad";
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public String[] getMethodNames() {
        return getTile() instanceof IPeripheral ? ((IPeripheral) getTile()).getMethodNames() : new String[0];
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
        return getTile() instanceof IPeripheral ? ((IPeripheral) getTile()).callMethod(computer, context, method, arguments) : new Object[0];
    }

    @Override

    public void attach(IComputerAccess computer) {
        if (getTile() instanceof IPeripheral) {
            ((IPeripheral) getTile()).attach(computer);
        }
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void detach(IComputerAccess computer) {
        if (getTile() instanceof IPeripheral) {
            ((IPeripheral) getTile()).detach(computer);
        }
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public boolean equals(IPeripheral other) {
        return this.equals((Object) other);
    }


    @Optional.Method(modid = "IC2")
    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        TileEntity tile = getTile();
        return tile instanceof IEnergyAcceptor ? ((IEnergySink) tile).acceptsEnergyFrom(emitter, direction) : false;
    }
}