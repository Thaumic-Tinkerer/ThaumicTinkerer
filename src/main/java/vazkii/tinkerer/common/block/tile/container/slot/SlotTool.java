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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.block.tile.TileEnchanter;

public class SlotTool extends Slot {

	TileEnchanter enchanter;

	public SlotTool(TileEnchanter enchanter, int par2, int par3, int par4) {
		super(enchanter, par2, par3, par4);
		this.enchanter = enchanter;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return !enchanter.working && par1ItemStack.getItem() != Items.book && par1ItemStack.getItem().isItemTool(par1ItemStack);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
		return !enchanter.working;
	}

}
