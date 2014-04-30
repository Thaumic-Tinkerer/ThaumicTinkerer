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
 * File Created @ [Dec 11, 2013, 9:36:45 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.peripheral;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.common.tiles.TileJarBrain;


public class PeripheralBrainInAJar implements IPeripheral {

	TileJarBrain jar;

	public PeripheralBrainInAJar(TileJarBrain jar) {
		this.jar = jar;
	}

	@Override
	public String getType() {
		return "tt_braininajar";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "getXP" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		return new Object[] { jar.xp };
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
        return this.equals((Object)other);
    }



}
