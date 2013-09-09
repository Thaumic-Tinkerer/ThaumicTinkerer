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
 * File Created @ [9 Sep 2013, 15:55:23 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.tablet;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class FakeInvPlayer extends InventoryPlayer {

	TabletFakePlayer fakePlayer;

	public FakeInvPlayer(TabletFakePlayer fakePlayer) {
		super(fakePlayer);
		this.fakePlayer = fakePlayer;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		super.setInventorySlotContents(par1, par2ItemStack);

		if(par1 == currentItem)
			fakePlayer.tablet.setInventorySlotContents(0, getStackInSlot(currentItem));
	}
}
