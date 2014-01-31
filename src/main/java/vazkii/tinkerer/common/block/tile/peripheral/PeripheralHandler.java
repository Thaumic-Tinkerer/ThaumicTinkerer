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
import thaumcraft.common.tiles.TileArcaneBore;
import thaumcraft.common.tiles.TileDeconstructionTable;
import thaumcraft.common.tiles.TileJarBrain;
import thaumcraft.common.tiles.TileSensor;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import dan200.computer.api.IHostedPeripheral;
import dan200.computer.api.IPeripheralHandler;

public final class PeripheralHandler implements IPeripheralHandler {

	@Override
	public IHostedPeripheral getPeripheral(TileEntity tile) {
		if(tile instanceof TileTransvectorInterface)
			return new PeripheralTransvectorInterface((TileTransvectorInterface) tile);

		if(tile instanceof IAspectContainer)
			return new PeripheralAspectContainer((IAspectContainer) tile);

		if(tile instanceof TileDeconstructionTable)
			return new PeripheralDeconstructor((TileDeconstructionTable) tile);

		if(tile instanceof TileJarBrain)
			return new PeripheralBrainInAJar((TileJarBrain) tile);

		if(tile instanceof TileSensor)
			return new PeripheralArcaneEar((TileSensor) tile);

		if(tile instanceof TileArcaneBore)
			return new PeripheralArcaneBore((TileArcaneBore) tile);

		return null;
	}

}
