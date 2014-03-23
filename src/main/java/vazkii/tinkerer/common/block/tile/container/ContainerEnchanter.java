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
 * File Created @ [14 Sep 2013, 18:10:21 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.block.tile.TileEnchanter;
import vazkii.tinkerer.common.block.tile.container.slot.SlotTool;
import vazkii.tinkerer.common.block.tile.container.slot.SlotWand;

public class ContainerEnchanter extends ContainerPlayerInv {

	TileEnchanter enchanter;

	public ContainerEnchanter(TileEnchanter enchanter, InventoryPlayer playerInv) {
		super(playerInv);
		this.enchanter = enchanter;

		addSlotToContainer(new SlotTool(enchanter, 0, 6, 6));
		addSlotToContainer(new SlotWand(enchanter, 1, 6, 31));

		initPlayerInv();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack var3 = null;
        Slot var4 = (Slot)inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack()) {
        	ItemStack var5 = var4.getStack();

        	if(var5 != null && var5.getItem() == Items.book)
        		return null;

        	boolean wand = ((Slot) inventorySlots.get(1)).isItemValid(var5);

            var3 = var5.copy();

            if (par2 < 2) {
            	if(!mergeItemStack(var5, 2, 38, false))
            		return null;
            } else if(wand ? !mergeItemStack(var5, 1, 2, false) : var5.getItem().isItemTool(var5) && !mergeItemStack(var5, 0, 1, false))
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

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return enchanter.isUseableByPlayer(entityplayer);
	}

}
