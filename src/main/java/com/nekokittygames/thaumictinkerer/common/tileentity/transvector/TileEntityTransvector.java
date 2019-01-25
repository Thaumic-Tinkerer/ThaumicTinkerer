package com.nekokittygames.thaumictinkerer.common.tileentity.transvector;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityCamoflage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityTransvector extends TileEntityCamoflage implements ITickable {
    private static final String TAG_X_TARGET = "xt";
    private static final String TAG_Y_TARGET = "yt";
    private static final String TAG_Z_TARGET = "zt";
    private static final String TAG_CHEATY_MODE = "cheatyMode";
    private BlockPos tilePos;

    private boolean cheaty;

    @Override
    public void readExtraNBT(NBTTagCompound compound) {
        super.readExtraNBT(compound);
        int x, y, z;
        if (compound.hasKey(TAG_X_TARGET)) {
            x = compound.getInteger(TAG_X_TARGET);
            y = compound.getInteger(TAG_Y_TARGET);
            z = compound.getInteger(TAG_Z_TARGET);
            tilePos = new BlockPos(x, y, z);
            cheaty = compound.getBoolean(TAG_CHEATY_MODE);
        }
    }

    @Override
    public void writeExtraNBT(NBTTagCompound compound) {
        super.writeExtraNBT(compound);
        if (tilePos != null) {
            compound.setInteger(TAG_X_TARGET, tilePos.getX());
            compound.setInteger(TAG_Y_TARGET, tilePos.getY());
            compound.setInteger(TAG_Z_TARGET, tilePos.getZ());
            compound.setBoolean(TAG_CHEATY_MODE, cheaty);
        }
    }

    public BlockPos getTilePos() {
        return tilePos;
    }

    public boolean setTilePos(BlockPos tilePos) {
        TileEntity tile = world.getTileEntity(tilePos);
        if (checkTile(tilePos, tile)) return false;
        this.tilePos = tilePos;
        return true;
    }

    public boolean checkTile(BlockPos tilePos, TileEntity tile) {
        return tile == null && tileRequiredAtLink() || (tilePos.distanceSq(getPos()) > getMaxDistance());
    }

    public boolean isCheaty() {
        return cheaty;
    }

    public void setCheaty(boolean cheaty) {
        this.cheaty = cheaty;
    }

    public final TileEntity getTile() {
        if (tilePos == null)
            return null;
        if (world.isAirBlock(tilePos))
            return null;
        TileEntity tile = world.getTileEntity(tilePos);
        if (checkTile(tilePos, tile)) {
            tilePos = null;
            return null;
        }
        return tile;
    }


    @Override
    public void update() {
        // Empty
    }

    public abstract int getMaxDistance();

    boolean tileRequiredAtLink() {
        return !cheaty;
    }
}
