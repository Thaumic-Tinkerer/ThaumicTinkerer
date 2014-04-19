package vazkii.tinkerer.common.potion;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Random;

/**
 * Created by pixlepix on 4/19/14.
 */
public class PotionEffectHandler {

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent e){
        Random rand = new Random();
        if(e.entity instanceof EntityPlayer){
            EntityPlayer p=(EntityPlayer) e.entity;
            if(p.isPotionActive(ModPotions.potionAir) && e.source.getEntity()!=null){
                for(Object target:p.worldObj.getEntitiesWithinAABBExcludingEntity(p, p.boundingBox.expand(20, 20, 20))){
                    if(target instanceof EntityLiving){
                        ((EntityLiving) target).setVelocity((rand.nextDouble()*2)-.5, rand.nextDouble(), (rand.nextDouble()*2)-.5);
                    }
                }
            }
        }
    }


}
