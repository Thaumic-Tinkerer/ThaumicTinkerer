package com.nekokittygames.thaumictinkerer.common.misc;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterBedrock extends Teleporter {
    public TeleporterBedrock(WorldServer w) {
        super(w);
    }

    @Override
    public void removeStalePortalLocations(long par1) {
        super.removeStalePortalLocations(par1);
    }

    @Override
    public boolean makePortal(Entity par1Entity) {
        return true;
    }

    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
        return true;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {

    }
}