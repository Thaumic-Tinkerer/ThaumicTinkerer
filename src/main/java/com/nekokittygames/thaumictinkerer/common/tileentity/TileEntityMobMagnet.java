package com.nekokittygames.thaumictinkerer.common.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

import java.util.function.Predicate;

public class TileEntityMobMagnet extends TileEntityMagnet{
    @Override
    protected <T extends Entity> Predicate selectedEntities() {
        return o->o instanceof EntityLiving;
    }

    @Override
    protected boolean filterEntity(Entity entity) {
        return !(entity instanceof EntityPlayer);
    }
}
