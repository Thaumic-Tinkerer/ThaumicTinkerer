package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.nekokittygames.thaumictinkerer.common.items.ItemSoulMould;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class TileEntityMobMagnet extends TileEntityMagnet {

    private ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            sendUpdates();
        }

        public boolean isItemValidForSlot(int index, ItemStack stack) {
            return TileEntityMobMagnet.this.isItemValidForSlot(index, stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!isItemValidForSlot(slot, stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }
    };
    private boolean pullAdults = true;

    public boolean isPullAdults() {
        return pullAdults;
    }

    public void setPullAdults(boolean pullAdults) {
        this.pullAdults = pullAdults;
        sendUpdates();
    }

    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return stack.getItem() instanceof ItemSoulMould;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        super.writeExtraNBT(nbttagcompound);
        nbttagcompound.setTag("inventory", inventory.serializeNBT());
        nbttagcompound.setBoolean("adults", pullAdults);
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        super.readExtraNBT(nbttagcompound);
        if (nbttagcompound.hasKey("inventory")) {
            inventory.deserializeNBT(nbttagcompound.getCompoundTag("inventory"));
            pullAdults = nbttagcompound.getBoolean("adults");
        }
    }

    @Override
    public boolean respondsToPulses() {
        return false;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }


    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        } else {
            return super.getCapability(capability, facing);
        }
    }

    @Override
    protected <T extends Entity> Predicate selectedEntities() {
        return o -> o instanceof EntityLiving && filterEntity((Entity) o);
    }

    @Override
    protected boolean filterEntity(Entity entity) {
        EntityLiving entityLiving = (EntityLiving) entity;
        boolean agePull = false;
        if (entityLiving instanceof EntityAgeable) {
            agePull = isPullAdults() != ((EntityAgeable) entity).isChild();
        } else
            agePull = true;
        boolean typePull = true;
        if (this.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
            String selectedEntity = ItemSoulMould.getEntityName(this.getInventory().getStackInSlot(0));
            String targetEntity = EntityList.getEntityString(entity);
            if (selectedEntity != null && !(selectedEntity.equalsIgnoreCase(targetEntity)))
                typePull = false;
        }
        return agePull && typePull;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        if (oldState.getBlock() == newSate.getBlock())
            return false;
        else
            return super.shouldRefresh(world, pos, oldState, newSate);
    }
}
