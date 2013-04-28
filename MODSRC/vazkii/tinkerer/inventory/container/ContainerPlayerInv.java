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
 * File Created @ [28 Apr 2013, 17:57:44 (GMT)]
 */
package vazkii.tinkerer.inventory.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public abstract class ContainerPlayerInv extends Container {

	InventoryPlayer playerInv;

	public ContainerPlayerInv(InventoryPlayer playerInv) {
		this.playerInv = playerInv;
	}

	void initPlayerInv() {
		int ys = getInvYStart();

        for (int x = 0; x < 3; ++x)
            for (int y = 0; y < 9; ++y)
                addSlotToContainer(new Slot(playerInv, y + x * 9 + 9, 8 + y * 18, ys + x * 18));

        for (int x = 0; x < 9; ++x)
            addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, ys + 58));
	}

	private int getInvYStart() {
		return 84;
	}
}
