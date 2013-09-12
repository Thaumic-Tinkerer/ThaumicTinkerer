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
 * File Created @ [12 Sep 2013, 18:43:26 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.container.slot;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.block.tile.TileMobMagnet;
import vazkii.tinkerer.common.item.ItemSoulMould;

public class SlotMobMagnet extends Slot {

	public SlotMobMagnet(TileMobMagnet mobMagnet, int par2, int par3, int par4) {
		super(mobMagnet, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack.getItem() instanceof ItemSoulMould;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}