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
 * File Created @ [9 Sep 2013, 16:26:46 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;

public class ContainerAnimationTablet extends ContainerPlayerInv {

	public TileAnimationTablet tablet;

	public ContainerAnimationTablet(TileAnimationTablet tablet, InventoryPlayer playerInv) {
		super(playerInv);

		this.tablet = tablet;

		addSlotToContainer(new Slot(tablet, 0, 80, 15));

		initPlayerInv();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return tablet.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack()) {
        	ItemStack var5 = var4.getStack();

            var3 = var5.copy();

            if (par2 < 1) {
            	if(!mergeItemStack(var5, 1, 37, false))
            		return null;
            } else if(!mergeItemStack(var5, 0, 1, false))
            	return null;

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