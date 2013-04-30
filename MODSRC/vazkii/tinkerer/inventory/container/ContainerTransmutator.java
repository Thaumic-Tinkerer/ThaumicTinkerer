/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [28 Apr 2013, 17:57:28 (GMT)]
 */
package vazkii.tinkerer.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.inventory.slot.SlotTransmutator;
import vazkii.tinkerer.inventory.slot.SlotTransmutatorOutput;
import vazkii.tinkerer.tile.TileEntityTransmutator;

public class ContainerTransmutator extends ContainerPlayerInv {

	TileEntityTransmutator transmutator;

	public ContainerTransmutator(TileEntityTransmutator transmutator, InventoryPlayer playerInv) {
		super(playerInv);

		this.transmutator = transmutator;

		addSlotToContainer(new SlotTransmutator(transmutator, 0, 146, 11));
		addSlotToContainer(new SlotTransmutatorOutput(transmutator, 1, 146, 49));

		initPlayerInv();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return transmutator.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack()) {
        	ItemStack var5 = var4.getStack();

            var3 = var5.copy();

            if (par2 < 2) {
            	if(!mergeItemStack(var5, 2, 38, false))
            		return null;
            }

            if (var5.stackSize == 0)
                var4.putStack((ItemStack)null);
            else
                var4.onSlotChanged();

            if (var5.stackSize == var3.stackSize)
                return null;

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
	}
}
