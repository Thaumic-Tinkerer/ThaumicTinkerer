/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 *
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [12 Jul 2013, 21:37:48 (GMT)]
 */
package vazkii.tinkerer.inventory.slot;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.item.ItemSoulMould;
import vazkii.tinkerer.tile.TileEntityMobMagnet;

public class SlotMobMagnet extends Slot {

	public SlotMobMagnet(TileEntityMobMagnet mobMagnet, int par2, int par3, int par4) {
		super(mobMagnet, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		if(par1ItemStack.getItem() instanceof ItemSoulMould) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
