package vazkii.tinkerer.common.compat;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.nbt.NBTTagCompound;


public class FumePeripheral implements IPeripheral {

	@Override
	public String getType() {
		return "gasRemover";
	}

	@Override
	public String[] getMethodNames() {
		return new String[]{};
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) throws Exception {
		return new Object[] {};
	}



	@Override
	public void attach(IComputerAccess computer) {

	}

	@Override
	public void detach(IComputerAccess computer) {
	}

    @Override
    public boolean equals(IPeripheral other) {
        return this.equals((Object)other);
    }


}
