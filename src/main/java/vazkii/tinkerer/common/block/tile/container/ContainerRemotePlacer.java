package vazkii.tinkerer.common.block.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.block.tile.TileRPlacer;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;

/**
 * Created by nekosune on 30/06/14.
 */
public class ContainerRemotePlacer extends ContainerPlayerInv {
    public TileRPlacer placer;

    public ContainerRemotePlacer(TileRPlacer placer, InventoryPlayer playerInv) {
        super(playerInv);

        this.placer=placer;

        addSlotToContainer(new Slot(placer, 0, 20, 30));

        initPlayerInv();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return placer.isUseableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot) inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();

            var3 = var5.copy();

            if (par2 < 1) {
                if (!mergeItemStack(var5, 1, 37, false))
                    return null;
            } else if (!mergeItemStack(var5, 0, 1, false))
                return null;

            if (var5.stackSize == 0)
                var4.putStack(null);
            else
                var4.onSlotChanged();

            if (var5.stackSize == var3.stackSize)
                return null;

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }
}
