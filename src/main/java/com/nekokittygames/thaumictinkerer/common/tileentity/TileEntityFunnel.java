package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.common.misc.SingleItemStackHandler;
import net.minecraft.block.BlockHopper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.common.tiles.essentia.TileJarFillable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TileEntityFunnel extends TileEntityThaumicTinkerer implements IAspectContainer, ITickable {


    private int speed = 1;

    private SingleItemStackHandler inventory = new SingleItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            sendUpdates();
        }

        public boolean isItemValidForSlot(int index, ItemStack stack) {
            return TileEntityFunnel.this.isItemValidForSlot(index, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!isItemValidForSlot(slot, stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }
    };

    public boolean isItemValidForSlot(int index, ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof IEssentiaContainerItem;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public ItemStackHandler getInventory() {
        return inventory;
    }


    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setTag("inventory", inventory.serializeNBT());
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        inventory.deserializeNBT(nbttagcompound.getCompoundTag("inventory"));
    }

    @Override
    public boolean respondsToPulses() {
        return false;
    }


    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing != EnumFacing.DOWN)
            return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
        else
            return super.hasCapability(capability, facing);
    }


    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing != EnumFacing.DOWN && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        } else {
            return super.getCapability(capability, facing);
        }
    }

    private TileEntity getHopperFacing(BlockPos pos, int getBlockMetadata) {
        EnumFacing i = BlockHopper.getFacing(getBlockMetadata);
        return world.getTileEntity(pos.offset(i));
    }


    @Override
    public void update() {
        if (this.inventory != null && this.inventory.getStackInSlot(0) != ItemStack.EMPTY && ((IEssentiaContainerItem)this.inventory.getStackInSlot(0).getItem()).getAspects(this.inventory.getStackInSlot(0)) != null && ((IEssentiaContainerItem)this.inventory.getStackInSlot(0).getItem()).getAspects(this.inventory.getStackInSlot(0)).size() > 0 && !this.world.isRemote) {
            IEssentiaContainerItem item = (IEssentiaContainerItem)this.inventory.getStackInSlot(0).getItem();
            AspectList aspectList = item.getAspects(this.inventory.getStackInSlot(0));
            if (aspectList != null && aspectList.size() == 1) {
                Aspect aspect = aspectList.getAspects()[0];
                TileEntity tile = this.world.getTileEntity(this.pos.down());
                if (tile != null && tile instanceof TileEntityHopper) {
                    TileEntity hoppered = this.getHopperFacing(tile.getPos(), tile.getBlockMetadata());
                    if (hoppered instanceof TileJarFillable) {
                        TileJarFillable jar = (TileJarFillable)hoppered;
                        AspectList JarAspects = jar.getAspects();
                        if ((jar.aspectFilter == null || jar.aspectFilter == aspect) && (JarAspects != null && JarAspects.size() == 0 || ((AspectList)Objects.requireNonNull(JarAspects)).getAspects()[0] == aspect)) {
                            int remain = jar.addToContainer(aspect, this.speed);
                            int amt = this.speed - remain;
                            item.setAspects(this.inventory.getStackInSlot(0), aspectList.remove(aspect, amt));
                        }
                    }
                }
            }
        }
    }

    @Override
    public AspectList getAspects() {
        if (inventory.getStackInSlot(0) != ItemStack.EMPTY && ((IEssentiaContainerItem) inventory.getStackInSlot(0).getItem()).getAspects(inventory.getStackInSlot(0)) != null && ((IEssentiaContainerItem) inventory.getStackInSlot(0).getItem()).getAspects(inventory.getStackInSlot(0)).size() > 0) {
            return ((IEssentiaContainerItem) inventory.getStackInSlot(0).getItem()).getAspects(inventory.getStackInSlot(0));
        } else
            return null;
    }

    @Override
    public void setAspects(AspectList aspectList) {
        // Empty
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return false;
    }

    @Override
    public int addToContainer(Aspect aspect, int i) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return 0;
    }
}
