/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 *
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [6 Jul 2013, 15:13:39 (GMT)]
 */
package vazkii.tinkerer.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.inventory.slot.SlotMobMagnet;
import vazkii.tinkerer.item.ItemSoulMould;
import vazkii.tinkerer.tile.TileEntityMobMagnet;

public class ContainerMobMagnet extends ContainerPlayerInv {

	public TileEntityMobMagnet mobMagnet;

	public ContainerMobMagnet(TileEntityMobMagnet mobMagnet, InventoryPlayer playerInv) {
		super(playerInv);

		this.mobMagnet = mobMagnet;

		addSlotToContainer(new SlotMobMagnet(mobMagnet, 0, 53, 37));

		initPlayerInv();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return mobMagnet.isUseableByPlayer(entityplayer);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot)inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();

			if(var5.getItem() instanceof ItemSoulMould) {
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
		}

		return var3;
	}
}
