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
 * File Created @ [Jan 10, 2014, 5:51:20 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.container.slot.kami;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.item.kami.ItemSkyPearl;

public class SlotSkyPearl extends Slot {

	public SlotSkyPearl(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack.getItem() == ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemSkyPearl.class);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}
