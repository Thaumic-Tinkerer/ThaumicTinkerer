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
 * File Created @ [13 Sep 2013, 00:56:19 (GMT)]
 */
package vazkii.tinkerer.common.block.tile.peripheral;

import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.common.tiles.TileDeconstructionTable;
import dan200.computer.api.IHostedPeripheral;
import dan200.computer.api.IPeripheralHandler;

public final class PeripheralHandler implements IPeripheralHandler {

	@Override
	public IHostedPeripheral getPeripheral(TileEntity tile) {
		if(tile instanceof IAspectContainer)
			return new PeripheralAspectContainer((IAspectContainer) tile);

		if(tile instanceof TileDeconstructionTable)
			return new PeripheralDeconstructor((TileDeconstructionTable) tile);

		return null;
	}

}
