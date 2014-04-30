package vazkii.tinkerer.common.block.tile.peripheral;


import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.IEssentiaTransport;

/**
 * Created by Katrina on 03/03/14.
 */
public class PeripheralEssentiaTransport implements IPeripheral {

    IEssentiaTransport pipe;
    public PeripheralEssentiaTransport(IEssentiaTransport input)
    {
        pipe=input;
    }

public static int GetDirection(Object obj) {
    Double num = (Double) obj;
    return (int) num.intValue();
}






    @Override
    public String getType() {
        return "tt_aspectTransport";
    }

    @Override
    public String[] getMethodNames() {
        return new String[]{"isConnectable", "canInputFrom", "canOutputTo", "getSuctionType", "getSuctionAmount", "getEssentiaType", "getEssentiaAmount", "getMinimumSuction"};
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
        switch(method)
        {
            case 0:
                return new Object[]{pipe.isConnectable(ForgeDirection.getOrientation(GetDirection(arguments[0])))};
            case 1:
                return new Object[]{pipe.canInputFrom(ForgeDirection.getOrientation(GetDirection(arguments[0])))};
            case 2:
                return new Object[]{pipe.canOutputTo(ForgeDirection.getOrientation(GetDirection(arguments[0])))};
            case 3:
                return new Object[]{pipe.getSuctionType(ForgeDirection.getOrientation(GetDirection(arguments[0]))).getTag()};
            case 4:
                return new Object[]{pipe.getSuctionAmount(ForgeDirection.getOrientation(GetDirection(arguments[0])))};
            case 5:
                return new Object[]{pipe.getEssentiaType(ForgeDirection.getOrientation(GetDirection(arguments[0]))).getTag()};
            case 6:
                return new Object[]{pipe.getEssentiaAmount(ForgeDirection.getOrientation(GetDirection(arguments[0])))};
            case 7:
                return new Object[]{pipe.getMinimumSuction()};
        }
        return new Object[0];
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
