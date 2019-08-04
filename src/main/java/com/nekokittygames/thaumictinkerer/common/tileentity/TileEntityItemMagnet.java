package com.nekokittygames.thaumictinkerer.common.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class TileEntityItemMagnet extends TileEntityMagnet {


    @Override
    public <T extends Entity> Predicate selectedEntities() {
        return o -> o instanceof EntityItem && filterEntity((Entity) o);
    }

    protected boolean filterEntity(Entity entity) {
        if (entity.getEntityData().hasKey("PreventRemoteMovement"))
            return !entity.getEntityData().getBoolean("PreventRemoteMovement");
        return true;
    }

    @Override
    protected boolean isItemValidForSlot(int index, ItemStack itemstack) {
        return true;
    }

    @Override
    public boolean respondsToPulses() {
        return false;
    }
}
