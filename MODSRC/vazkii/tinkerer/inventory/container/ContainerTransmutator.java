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
 * File Created @ [28 Apr 2013, 17:57:28 (GMT)]
 */
package vazkii.tinkerer.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import vazkii.tinkerer.inventory.slot.SlotPureOutput;
import vazkii.tinkerer.inventory.slot.SlotTransmutator;
import vazkii.tinkerer.tile.TileEntityTransmutator;

public class ContainerTransmutator extends ContainerPlayerInv {

	TileEntityTransmutator transmutator;

	public ContainerTransmutator(TileEntityTransmutator transmutator, InventoryPlayer playerInv) {
		super(playerInv);

		this.transmutator = transmutator;

		addSlotToContainer(new SlotTransmutator(transmutator, 0, 146, 11));
		addSlotToContainer(new SlotPureOutput(transmutator, 1, 146, 49));

		initPlayerInv();
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return transmutator.isUseableByPlayer(entityplayer);
	}

}
