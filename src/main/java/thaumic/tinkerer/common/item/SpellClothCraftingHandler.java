package thaumic.tinkerer.common.item;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumic.tinkerer.api.INoRemoveEnchant;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.ItemSpellCloth;
import java.util.List;

/**
 * Created by Katrina on 07/03/2015.
 */
public class SpellClothCraftingHandler {

    @SubscribeEvent
    public void ItemCrafted(PlayerEvent.ItemCraftedEvent event)
    {
        boolean foundCloth = false;
        boolean foundEnchanted = false;
        int slot=0;
        for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
            ItemStack stack = event.craftMatrix.getStackInSlot(i);
            if (stack != null) {
                Item item=ThaumicTinkerer.registry.getFirstItemFromClass(ItemSpellCloth.class);
                if (stack.isItemEnchanted() && !(stack.getItem() instanceof INoRemoveEnchant) && !foundEnchanted) {
                    foundEnchanted = true;
                    slot = i;
                }
                else if (stack.getItem() == item && !foundCloth)
                    foundCloth = true;
                else
                {
                    return;
                }
            }
        }
        if(foundCloth && foundEnchanted)
        {
            event.craftMatrix.setInventorySlotContents(slot,null);
        }
    }
}
