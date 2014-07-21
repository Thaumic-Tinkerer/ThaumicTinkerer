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
 * File Created @ [Dec 11, 2013, 10:42:23 PM (GMT)]
 */
package thaumic.tinkerer.common.block.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.common.container.SlotLimitedHasAspects;
import thaumic.tinkerer.common.block.tile.TileAspectAnalyzer;

public class ContainerAspectAnalyzer extends ContainerPlayerInv {

	public TileAspectAnalyzer analyzer;
	Slot slot;

	public ContainerAspectAnalyzer(TileAspectAnalyzer analyzer, InventoryPlayer playerInv) {
		super(playerInv);

		this.analyzer = analyzer;

		addSlotToContainer(slot = new SlotLimitedHasAspects(analyzer, 0, 20, 30));

		initPlayerInv();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return analyzer.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();

			if (par2 == 0 || var5 != null && slot.isItemValid(var5)) {
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
		}

		return var3;
	}

}
