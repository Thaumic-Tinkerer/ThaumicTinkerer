package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.mojang.authlib.GameProfile;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.misc.FakePlayerUtils;
import com.nekokittygames.thaumictinkerer.common.misc.ThaumicFakePlayer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import thaumcraft.common.blocks.essentia.BlockJarItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.UUID;
import java.util.function.Consumer;

public class TileEntityAnimationTablet extends TileEntityThaumicTinkerer implements ITickable, Consumer<ItemStack> {

    private boolean rightClick;
    private int progress;
    private EnumFacing facing;
    private boolean active;
    private int ticksExisted;
    private WeakReference<ThaumicFakePlayer> player;




    public int getTicksExisted() {
        return ticksExisted;
    }

    public void setTicksExisted(int ticksExisted) {
        this.ticksExisted = ticksExisted;
    }

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
        sendUpdates();
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



    @Override
    public void update() {

        if(!world.isRemote) {
            if (player == null) {
                MinecraftServer worldServer = FMLCommonHandler.instance().getMinecraftServerInstance();
                player = new WeakReference<ThaumicFakePlayer>(FakePlayerUtils.get(worldServer.getWorld(this.world.provider.getDimension()), new GameProfile(LibMisc.MOD_UUID, LibMisc.MOD_F_NAME)));
            }
        }
        ticksExisted++;
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
                MinecraftServer worldServer= FMLCommonHandler.instance().getMinecraftServerInstance();

                BlockPos targetPos=this.GetBlockTarget();
                Block targetBlock=world.getBlockState(targetPos).getBlock();

                if(!rightClick)
                {
                    FakePlayerUtils.setupFakePlayerForUse(getPlayer(), this.pos, facing, this.inventory.getStackInSlot(0).copy(), false);
                    ItemStack result = this.inventory.getStackInSlot(0);
                    result = FakePlayerUtils.leftClickInDirection(getPlayer(), this.world, this.pos, facing, world.getBlockState(pos));
                    FakePlayerUtils.cleanupFakePlayerFromUse(getPlayer(), result, this.inventory.getStackInSlot(0), this);

                    //world.sendBlockBreakProgress(fakePlayer.getEntityId(),);

                }
                else
                {
                    FakePlayerUtils.setupFakePlayerForUse(getPlayer(), this.pos, facing, this.inventory.getStackInSlot(0).copy(), false);
                    ItemStack result = this.inventory.getStackInSlot(0);
                    result = FakePlayerUtils.rightClickInDirection(getPlayer(), this.world, this.pos, facing, world.getBlockState(pos));
                    FakePlayerUtils.cleanupFakePlayerFromUse(getPlayer(), result, this.inventory.getStackInSlot(0), this);
                }


            }
        }
    }


    ThaumicFakePlayer getPlayer() {
        return player.get();
    }
    public BlockPos GetBlockTarget()
    {
        BlockPos newPos=this.getPos().offset(facing);
        if(isRightClick() && world.isAirBlock(newPos))
            newPos=newPos.offset(EnumFacing.DOWN);
        return newPos;
    }

    @Override
    public void accept(ItemStack itemStack) {
        this.inventory.setStackInSlot(0,itemStack);
    }
}
