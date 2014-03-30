package vazkii.tinkerer.common.core.handler.kami;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent;
import vazkii.tinkerer.common.item.kami.tool.ItemIchorAxeAdv;
import vazkii.tinkerer.common.item.kami.tool.ItemIchorPickAdv;
import vazkii.tinkerer.common.item.kami.tool.ItemIchorShovelAdv;
import vazkii.tinkerer.common.item.kami.tool.ItemIchorSwordAdv;

/**
 * Created by Katrina on 29/03/14.
 */
public class AwakenedIchorDropHandler {

    @ForgeSubscribe
    public void onBlockDrop(BlockEvent.HarvestDropsEvent event)
    {
        if(event.harvester!=null) {
            ItemStack itemInHand = event.harvester.getCurrentItemOrArmor(0);

            if (itemInHand != null) {
                if (itemInHand.getItem() instanceof ItemIchorAxeAdv || itemInHand.getItem() instanceof ItemIchorShovelAdv || itemInHand.getItem() instanceof ItemIchorAxeAdv
                itemInHand.getItem() instanceof ItemIchorPickAdv || itemInHand.getItem() instanceof ItemIchorSwordAdv)
                {
                    for(ItemStack stack:event.drops)
                    {
                        
                    }
                }
            }
        }
    }
}
