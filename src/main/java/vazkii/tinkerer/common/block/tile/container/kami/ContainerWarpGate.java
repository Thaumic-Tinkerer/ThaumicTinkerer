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
 * File Created @ [Jan 10, 2014, 5:45:28 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.container.kami;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.block.tile.container.ContainerPlayerInv;
import vazkii.tinkerer.common.block.tile.container.slot.kami.SlotSkyPearl;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.item.ModItems;

public class ContainerWarpGate extends ContainerPlayerInv {

	TileWarpGate gate;

	public ContainerWarpGate(TileWarpGate gate, InventoryPlayer playerInv) {
		super(playerInv);
		this.gate = gate;

		for(int y = 0; y < 2; y++)
			for(int x = 0; x < 5; x++)
				addSlotToContainer(new SlotSkyPearl(gate, y * 5 + x, 30 + x * 25, 27 + y * 25));

		initPlayerInv();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return gate.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();

			if(par2 < 10 || var5 != null) {
				var3 = var5.copy();

				if (par2 < 10) {
					if(!mergeItemStack(var5, 10, 36, false))
						return null;
				} else if(var3.getItem() == ModItems.skyPearl&& !mergeItemStack(var5, 0, 10, false))
					return null;

				if (var5.stackSize == 0)
					var4.putStack((ItemStack)null);
				else
					var4.onSlotChanged();

				if (var5.stackSize == var3.stackSize)
					return null;

				var4.onPickupFromSlot(par1EntityPlayer, var5);
			}
		}

		return var3;
	}

}
