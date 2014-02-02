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
 * File Created @ [Dec 11, 2013, 9:41:49 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.peripheral;

import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.common.tiles.TileSensor;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;
import dan200.computer.api.ILuaContext;

public class PeripheralArcaneEar implements IHostedPeripheral {

	TileSensor ear;

	public PeripheralArcaneEar(TileSensor ear) {
		this.ear = ear;
	}

	@Override
	public String getType() {
		return "tt_arcaneear";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "getNote", "setNote" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		if(method == 0) {
			return new Object[]{ ear.note };
		} else {
			ear.note = (byte) ((Double) arguments[0]).doubleValue();

			return null;
		}
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
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
	public void update() {
		// NO-OP
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		// NO-OP
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		// NO-OP
	}

}
