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
 * File Created @ [14 Sep 2013, 18:35:37 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.container.slot;

import vazkii.tinkerer.common.item.ItemSoulMould;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotTool extends Slot {

	public SlotTool(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack.itemID != Item.book.itemID && par1ItemStack.getItem().isItemTool(par1ItemStack);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}
