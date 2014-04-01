package vazkii.tinkerer.common.core.handler.kami;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import vazkii.tinkerer.common.item.kami.tool.ItemIchorPickAdv;
/**
 * Created by Katrina on 31/03/14.
 */
public class KamiDimensionHandler {

    @ForgeSubscribe
    public void onInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
            if (event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z) == Block.bedrock.blockID) {
                if (stack.getItem() instanceof ItemIchorPickAdv) {
                    ((ItemIchorPickAdv) stack.getItem()).onBlockStartBreak(stack, event.x, event.y, event.z, event.entityPlayer);
                }
            }
        }
    }
}