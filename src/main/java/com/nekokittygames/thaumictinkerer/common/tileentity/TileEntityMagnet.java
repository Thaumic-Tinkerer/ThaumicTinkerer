package com.nekokittygames.thaumictinkerer.common.tileentity;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;


public class TileEntityMagnet extends TileEntityThaumicTinkerer {

    protected void test()
    {
        world.<Entity>getEntitiesWithinAABB(Entity.class,new AxisAlignedBB(0,0,0,1,1,1), selectedEntities()::test);
    }
    protected<T extends Entity> java.util.function.Predicate selectedEntities()
    {
        return  o -> o instanceof EntityItem;
    }
}
