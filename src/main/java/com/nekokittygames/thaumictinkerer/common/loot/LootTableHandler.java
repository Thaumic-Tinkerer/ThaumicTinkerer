package com.nekokittygames.thaumictinkerer.common.loot;

import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class LootTableHandler {

    @SubscribeEvent
    public static void onEntityLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer) {
            if (event.getEntityLiving() instanceof EntityEnderman && event.getEntityLiving().dimension == DimensionType.THE_END.getId() && Math.random() <= 1D / TTConfig.EndShardDropRate)
                event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(ModItems.kamiresource, 1, 0)));

            if (event.getEntityLiving() instanceof EntityPigZombie && event.getEntityLiving().dimension == DimensionType.NETHER.getId() && Math.random() <= 1D / TTConfig.NetherShardDropRate)
                event.getDrops().add(new EntityItem(event.getEntityLiving().world, event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, new ItemStack(ModItems.kamiresource, 1, 1)));
        }
    }
}