package thaumic.tinkerer.common.core.handler.kami;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import thaumic.tinkerer.common.item.kami.tool.ItemIchorPickAdv;

/**
 * Created by Katrina on 31/03/14.
 */
public class KamiDimensionHandler {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
            if (event.entityPlayer.worldObj.getBlock(event.x, event.y, event.z) == Blocks.bedrock) {
                if (stack != null && stack.getItem() instanceof ItemIchorPickAdv) {
                    stack.getItem().onBlockStartBreak(stack, event.x, event.y, event.z, event.entityPlayer);

                }
            }
        }
    }
}
