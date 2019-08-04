package com.nekokittygames.thaumictinkerer.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public abstract class TileEntityThaumicTinkerer extends TileEntity {

    private boolean redstonePowered;

    public void sendUpdates() {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);

        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        writeExtraNBT(compound);
        return super.writeToNBT(compound);

    }

    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setBoolean("redstone", redstonePowered);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readExtraNBT(compound);
        super.readFromNBT(compound);
    }

    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        // todo: remove if in a couple versions time
        if (nbttagcompound.hasKey("redstone"))
            redstonePowered = nbttagcompound.getBoolean("redstone");
        else
            redstonePowered = false;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound cmp = super.getUpdateTag();
        writeExtraNBT(cmp);
        return cmp;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        readExtraNBT(tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
        sendUpdates();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 5, this.getUpdateTag());
    }

    public abstract boolean respondsToPulses();

    public void activateOnPulse() {
        // Empty
    }

    public boolean getRedstonePowered() {
        return redstonePowered;
    }

    public void setRedstonePowered(boolean b) {
        boolean oldRedstone = redstonePowered;
        redstonePowered = b;
        if (redstonePowered != oldRedstone)
            this.sendUpdates();
    }

    public boolean canRedstoneConnect() {
        return false;
    }
}
