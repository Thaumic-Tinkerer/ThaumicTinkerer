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
 * File Created @ [Dec 11, 2013, 9:49:46 PM (GMT)]
 *//*

package thaumic.tinkerer.common.peripheral;

import ic2.api.energy.tile.IEnergySink;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumic.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import buildcraft.api.power.IPowerReceptor;
import cofh.api.energy.IEnergyHandler;
import IComputerAccess;
import ILuaContext;

public class PeripheralTransvectorInterface extends PeripheralAspectContainer {

	TileTransvectorInterface interfase;

	public PeripheralTransvectorInterface(TileTransvectorInterface interfase) {
		super(interfase);
		this.interfase = interfase;
	}

	@Override
	public String getType() {
		return "tt_transvectorinterface";
	}

	@Override
	public String[] getMethodNames() {
		return new String[] { "getAspects", "getAspectCount", "isBound", "isInventory", "isSidedInventory", "isFluidContainer", "isMJHandler", "isEUHandler", "isRFHandler", "isAspectContainer", "isAspectTransport" };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		TileEntity tile = interfase.getTile();

		switch(method) {
			case 2 : return new Object[] { tile != null };
			case 3 : return new Object[] { tile instanceof IInventory };
			case 4 : return new Object[] { tile instanceof ISidedInventory };
			case 5 : return new Object[] { tile instanceof IFluidHandler };
			case 6 : return new Object[] { tile instanceof IPowerReceptor };
			case 7 : return new Object[] { tile instanceof IEnergySink };
			case 8 : return new Object[] { tile instanceof IEnergyHandler };
			case 9 : return new Object[] { tile instanceof IAspectContainer };
			case 10 : return new Object[] { tile instanceof IEssentiaTransport };
		}

		return super.callMethod(computer, context, method, arguments);
	}

}
*/
