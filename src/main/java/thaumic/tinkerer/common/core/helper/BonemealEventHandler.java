package thaumic.tinkerer.common.core.helper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.IGrowable;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.blocks.BlockCustomPlant;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.equipment.ItemElementalHoe;
import thaumic.tinkerer.common.block.BlockInfusedGrain;
import thaumic.tinkerer.common.block.tile.TileInfusedGrain;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

/**
 * Created by pixlepix on 8/28/14.
 * <p/>
 * Prevents bonemeal being used on TT crops
 */
public class BonemealEventHandler {

    @SubscribeEvent
    public void onBonemeal(BonemealEvent event) {
        if (event.world.getBlock(event.x, event.y, event.z) instanceof BlockInfusedGrain) {
            if (!ConfigHandler.cropsAllowBonemeal) {
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.world.isRemote) {
            if (event.world.getBlock(event.x, event.y, event.z) instanceof BlockInfusedGrain) {
                if (event.entityPlayer != null && event.entityPlayer.getCurrentEquippedItem() != null) {
                    if (event.entityPlayer.getCurrentEquippedItem().getItem() == ConfigItems.itemHoeElemental) {
                        IGrowable igrowable = (IGrowable) event.world.getBlock(event.x, event.y, event.z);

                        if (igrowable.func_149851_a(event.world, event.x, event.y, event.z, event.world.isRemote)) {
                            if (!event.world.isRemote) {
                                if (igrowable.func_149852_a(event.world, event.world.rand, event.x, event.y, event.z)) {
                                    igrowable.func_149853_b(event.world, event.world.rand, event.x, event.y, event.z);
                                }
                            }

                        }
                        event.entityPlayer.getCurrentEquippedItem().damageItem(25, event.entityPlayer);
                        Thaumcraft.proxy.blockSparkle(event.world, event.x, event.y, event.z, 0, 2);
                    }
                }
            }
        }
    }

}
