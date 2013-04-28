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
 * File Created @ [28 Apr 2013, 18:04:41 (GMT)]
 */
package vazkii.tinkerer.inventory.slot;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import vazkii.tinkerer.tile.TileEntityTransmutator;

public class SlotTransmutator extends Slot {

	public SlotTransmutator(TileEntityTransmutator transmutator, int par2, int par3, int par4) {
		super(transmutator, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		ObjectTags tags = ThaumcraftCraftingManager.getObjectTags(par1ItemStack);

		return tags != null && getTotalAspectValue(tags) <= 100;
	}

	private static int getTotalAspectValue(ObjectTags tags) {
		int totalValue = 0;
		for(EnumTag tag : tags.getAspects())
			totalValue += tags.getAmount(tag);
		return totalValue;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
