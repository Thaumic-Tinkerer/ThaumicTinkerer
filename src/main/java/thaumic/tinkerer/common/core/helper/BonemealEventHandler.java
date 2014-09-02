package thaumic.tinkerer.common.core.helper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import thaumic.tinkerer.common.block.BlockInfusedGrain;
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

}
