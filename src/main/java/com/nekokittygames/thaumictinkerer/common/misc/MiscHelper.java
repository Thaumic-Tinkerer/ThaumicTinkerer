package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.client.audio.MovingSoundMinecartRiding;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.codechicken.lib.vec.Vector3;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class MiscHelper {

    @SubscribeEvent
    public static void registerItems(PlaySoundEvent event)
    {

        if(event.getSound() instanceof MovingSoundMinecartRiding) {
            MovingSoundMinecartRiding sound = (MovingSoundMinecartRiding) event.getSound();
            ThaumicTinkerer.logger.info("Sound " + event.getSound().toString() + " Playing  at "+sound.getYPosF());
        }
    }
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
