package com.nekokittygames.thaumictinkerer.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public abstract class TileEntityThaumicTinkerer extends TileEntity {

    public void sendUpdates()
    {
        world.markBlockRangeForRenderUpdate(pos, pos);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
        markDirty();
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        writeExtraNBT(compound);
        return super.writeToNBT(compound);

    }
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        readExtraNBT(compound);
        super.readFromNBT(compound);
    }

    public void readExtraNBT(NBTTagCompound nbttagcompound) {
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound cmp=super.getUpdateTag();
        writeExtraNBT(cmp);
        return cmp;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        readExtraNBT(tag);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 5, this.getUpdateTag());
    }

}
