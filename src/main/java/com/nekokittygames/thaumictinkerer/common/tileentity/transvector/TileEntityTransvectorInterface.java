package com.nekokittygames.thaumictinkerer.common.tileentity.transvector;


import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import thaumcraft.api.aspects.*;

import javax.annotation.Nullable;

public class TileEntityTransvectorInterface extends TileEntityTransvector implements IAspectSource, IEssentiaTransport {
    @Override
    public int getMaxDistance() {
        return TTConfig.transvectorInterfaceDistance*TTConfig.transvectorInterfaceDistance;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        TileEntity tile=getTile();
        if(tile!=null)
            return tile.hasCapability(capability,facing);
        return super.hasCapability(capability, facing);
    }


    @Override
    public void markDirty() {
        TileEntity tile=getTile();
        if(tile!=null)
            tile.markDirty();

        super.markDirty();
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        TileEntity tile=getTile();
        if(tile!=null)
            return tile.getCapability(capability,facing);
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean isBlocked() {
        TileEntity tile=getTile();
        if(tile instanceof IAspectSource)
            return ((IAspectSource)tile).isBlocked();
        return false;
    }

    @Override
    public AspectList getAspects() {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            return ((IAspectContainer )tile).getAspects();
        return null;
    }

    @Override
    public void setAspects(AspectList aspectList) {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            ((IAspectContainer )tile).setAspects(aspectList);
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            return ((IAspectContainer)tile).doesContainerAccept(aspect);
        return false;
    }

    @Override
    public int addToContainer(Aspect aspect, int i) {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            return ((IAspectContainer )tile).addToContainer(aspect,i);
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int i) {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            return ((IAspectContainer )tile).takeFromContainer(aspect, i);
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            return ((IAspectContainer )tile).takeFromContainer(aspectList);
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            return ((IAspectContainer )tile).doesContainerContainAmount(aspect, i);
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            return ((IAspectContainer )tile).doesContainerContain(aspectList);
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        TileEntity tile=getTile();
        if(tile instanceof IAspectContainer)
            return ((IAspectContainer )tile).containerContains(aspect);
        return 0;
    }

    @Override
    public boolean isConnectable(EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).isConnectable(enumFacing);
        return false;
    }

    @Override
    public boolean canInputFrom(EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).canInputFrom(enumFacing);
        return false;
    }

    @Override
    public boolean canOutputTo(EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).canOutputTo(enumFacing);
        return false;
    }

    @Override
    public void setSuction(Aspect aspect, int i) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            ((IEssentiaTransport)tile).setSuction(aspect,i);
    }

    @Override
    public Aspect getSuctionType(EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).getSuctionType(enumFacing);
        return null;
    }

    @Override
    public int getSuctionAmount(EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).getSuctionAmount(enumFacing);
        return 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int i, EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).takeEssentia(aspect, i, enumFacing);
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int i, EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).addEssentia(aspect, i, enumFacing);
        return 0;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).getEssentiaType(enumFacing);
        return null;
    }

    @Override
    public int getEssentiaAmount(EnumFacing enumFacing) {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).getEssentiaAmount(enumFacing);
        return 0;
    }

    @Override
    public int getMinimumSuction() {
        TileEntity tile=getTile();
        if(tile instanceof IEssentiaTransport)
            return ((IEssentiaTransport)tile).getMinimumSuction();
        return 0;
    }
}
