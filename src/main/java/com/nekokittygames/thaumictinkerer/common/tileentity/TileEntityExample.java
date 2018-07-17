package com.nekokittygames.thaumictinkerer.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ITickable;

public class TileEntityExample extends TileEntityThaumicTinkerer implements ITickable {
    public static final int GROW_TIME=20*3;
    private int time;
    private boolean activated;
    private IBlockState guideBlockType;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public IBlockState getGuideBlockType() {
        return guideBlockType;
    }

    public void setGuideBlockType(IBlockState guideBlockType) {
        this.guideBlockType = guideBlockType;
    }

    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        super.writeExtraNBT(nbttagcompound);
        nbttagcompound.setInteger("TIME",time);
        nbttagcompound.setBoolean("ACTIVATED",activated);
        NBTTagCompound block=new NBTTagCompound();
        NBTUtil.writeBlockState(block,guideBlockType);
        nbttagcompound.setTag("BLOCKTYPE",block);
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        super.readExtraNBT(nbttagcompound);
        time= nbttagcompound.getInteger("TIME");
        activated=nbttagcompound.getBoolean("ACTIVATED");
        guideBlockType=NBTUtil.readBlockState(nbttagcompound.getCompoundTag("BLOCKTYPE"));
    }


    @Override
    public void update() {
        if(activated)
        {
            if(time<=GROW_TIME)
                time++;
            else
                activated=false;
        }
    }
}
