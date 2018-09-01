package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.mojang.authlib.GameProfile;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thaumcraft.common.blocks.essentia.BlockJarItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class TileEntityAnimationTablet extends TileEntityThaumicTinkerer implements ITickable {

    private boolean rightClick;
    private int progress;
    private EnumFacing facing;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    public void setRightClick(boolean rightClick) {
        this.rightClick = rightClick;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }




    private ItemStackHandler inventory= new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            super.onContentsChanged(slot);
            sendUpdates();
        }

        public boolean isItemValidForSlot(int index, ItemStack stack)
        {
            return TileEntityAnimationTablet.this.isItemValidForSlot(index,stack);
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if(!isItemValidForSlot(slot,stack))
                return stack;
            return super.insertItem(slot, stack, simulate);
        }
    };

    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }


    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setTag("inventory",inventory.serializeNBT());
        nbttagcompound.setBoolean("rightClick",rightClick);
        nbttagcompound.setInteger("progress",progress);
        nbttagcompound.setInteger("facing",facing.getIndex());
        nbttagcompound.setBoolean("active",active);
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        inventory.deserializeNBT(nbttagcompound.getCompoundTag("inventory"));
        rightClick=nbttagcompound.getBoolean("rightClick");
        progress=nbttagcompound.getInteger("progress");
        facing=EnumFacing.values()[nbttagcompound.getInteger("facing")];
        active=nbttagcompound.getBoolean("active");
    }

    @Override
    public boolean respondsToPulses() {
        return false;
    }

    @Override
    public boolean canRedstoneConnect() {
        return true;
    }

    @Override
    public void activateOnPulse() {

    }


    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(facing!=EnumFacing.DOWN)
            return capability== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||super.hasCapability(capability, facing);
        else
            return super.hasCapability(capability, facing);
    }


    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(facing!=EnumFacing.DOWN && capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T)inventory;
        }
        else
        {
            return super.getCapability(capability,facing);
        }
    }

    private void prepareFakePlayer(FakePlayer fPlayer)
    {
        fPlayer.inventory.dropAllItems();
        fPlayer.inventory.setInventorySlotContents(0,inventory.getStackInSlot(0).copy());
        fPlayer.inventory.currentItem=0;
        fPlayer.setActiveHand(EnumHand.MAIN_HAND);
    }

    private void afterFakePlayer(FakePlayer fPlayer)
    {
        this.inventory.setStackInSlot(0, fPlayer.inventory.getStackInSlot(0).copy());

        fPlayer.inventory.dropAllItems();
    }

    @Override
    public void update() {
        if(getRedstonePowered() && progress<=0)
        {
            progress=200;
            active=true;
        }
        if(progress>0)
        {

            progress--;
            sendUpdates();
        }

        if(active && progress<=0)
        {
            //active=false;
            if(!world.isRemote)
            {
                FakePlayer fakePlayer= FakePlayerFactory.get(FMLServerHandler.instance().getServer().getWorld(this.world.provider.getDimension()),new GameProfile(LibMisc.MOD_UUID,LibMisc.MOD_F_NAME));
                prepareFakePlayer(fakePlayer);
                if(!rightClick)
                {
                    //world.sendBlockBreakProgress(fakePlayer.getEntityId(),);
                }

            }
        }
    }
}
