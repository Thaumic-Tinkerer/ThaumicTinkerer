package com.nekokittygames.thaumictinkerer.common.misc;

import net.minecraft.entity.Entity;
import thaumcraft.codechicken.lib.vec.Vector3;


public class MiscHelper {


    public static void setEntityMotionFromVector(Entity entity, Vector3 originalPosVector, float modifier) {
        Vector3 entityVector = Vector3.fromEntityCenter(entity);
        Vector3 finalVector = originalPosVector.copy().subtract(entityVector);

        if (finalVector.mag() > 1)
            finalVector.normalize();

        entity.motionX = finalVector.x * modifier;
        entity.motionY = finalVector.y * modifier;
        entity.motionZ = finalVector.z * modifier;
    }


}
