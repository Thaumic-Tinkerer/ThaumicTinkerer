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
 * File Created @ [13 Sep 2013, 01:10:52 (GMT)]
 */

package vazkii.tinkerer.common.block.tile.peripheral;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import thaumcraft.common.tiles.TileDeconstructionTable;

public class PeripheralDeconstructor implements IPeripheral {

	TileDeconstructionTable deconstructor;

	public PeripheralDeconstructor(TileDeconstructionTable deconstructor) {
		this.deconstructor = deconstructor;
	}

	@Override
	public String getType() {
		return "tt_deconstructor";
	}

	@Override
	public String[] getMethodNames() {
		return new String[]{ "hasAspect", "hasItem", "getAspect" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch (method) {
			case 0:
				return new Object[]{ deconstructor.aspect != null };
			case 1:
				return new Object[]{ deconstructor.getStackInSlot(0) != null };
			case 2:
				return deconstructor.aspect == null ? null : new Object[]{ deconstructor.aspect.getTag() };
		}

		return null;
	}

	@Override
	public void attach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	public void detach(IComputerAccess computer) {
		// NO-OP
	}

	@Override
	public boolean equals(IPeripheral other) {
		return this.equals((Object) other);
	}

}