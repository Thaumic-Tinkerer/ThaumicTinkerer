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
 * File Created @ [9 Sep 2013, 16:27:10 (GMT)]
 */
package thaumic.tinkerer.common.block.tile.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public abstract class ContainerPlayerInv extends Container {

	InventoryPlayer playerInv;

	public ContainerPlayerInv(InventoryPlayer playerInv) {
		this.playerInv = playerInv;
	}

	public void initPlayerInv() {
		int ys = getInvYStart();
		int xs = getInvXStart();

		for (int x = 0; x < 3; ++x)
			for (int y = 0; y < 9; ++y)
				addSlotToContainer(new Slot(playerInv, y + x * 9 + 9, xs + y * 18, ys + x * 18));

		for (int x = 0; x < 9; ++x)
			addSlotToContainer(new Slot(playerInv, x, xs + x * 18, ys + 58));
	}

	public int getInvYStart() {
		return 84;
	}

	public int getInvXStart() {
		return 8;
	}
}
