/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Dec 27, 2013, 11:05:53 PM (GMT)]
 */
package vazkii.tinkerer.common.core.handler.kami;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.item.ModItems;

public class DimensionalShardDropHandler {

	@ForgeSubscribe
    public void onEntityLivingDrops(LivingDropsEvent event) {
        if(event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
        	if(event.entityLiving instanceof EntityEnderman && event.entityLiving.dimension == ConfigHandler.endDimensionID && Math.random() <= 1D / 32D)
        		event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, new ItemStack(ModItems.kamiResource, 1, 7)));

        	if(event.entityLiving instanceof EntityPigZombie && event.entityLiving.dimension == ConfigHandler.netherDimensionID && Math.random() <= 1D / 16D)
        		event.drops.add(new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, new ItemStack(ModItems.kamiResource, 1, 6)));



        }
    }
}
