package com.nekokittygames.thaumictinkerer.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ITickable;
import net.minecraft.util.Util;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class TileEntityExample extends TileEntityThaumicTinkerer implements ITickable {
    public static final int GROW_TIME=20*3;
    private int time;
    private boolean activated;
    private List<IBlockState> guideBlockType=new ArrayList<>();

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

    public List<IBlockState> getGuideBlockType() {
        return guideBlockType;
    }


    @Override
    public void writeExtraNBT(NBTTagCompound nbttagcompound) {
        super.writeExtraNBT(nbttagcompound);
        nbttagcompound.setInteger("TIME",time);
        nbttagcompound.setBoolean("ACTIVATED",activated);
        NBTTagList block=new NBTTagList();
        if(guideBlockType.size()!=0)
        {
            for(IBlockState state:guideBlockType)
            {
                NBTTagCompound stateCmp=new NBTTagCompound();
                NBTUtil.writeBlockState(stateCmp,state);
                block.appendTag(stateCmp);
            }
        }
        nbttagcompound.setTag("BLOCKTYPES",block);
    }

    @Override
    public void readExtraNBT(NBTTagCompound nbttagcompound) {
        super.readExtraNBT(nbttagcompound);
        time= nbttagcompound.getInteger("TIME");
        activated=nbttagcompound.getBoolean("ACTIVATED");
        if(nbttagcompound.hasKey("BLOCKTYPE"))
        {
            guideBlockType.add(NBTUtil.readBlockState(nbttagcompound.getCompoundTag("BLOCKTYPE")));
            nbttagcompound.removeTag("BLOCKTYPE");
        }
        else
        {
            NBTTagList blockList=nbttagcompound.getTagList("BLOCKTYPES", Constants.NBT.TAG_COMPOUND);
            for(int i=0;i<blockList.tagCount();i++)
            {
                guideBlockType.add(NBTUtil.readBlockState(blockList.getCompoundTagAt(i)));
            }
        }
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
