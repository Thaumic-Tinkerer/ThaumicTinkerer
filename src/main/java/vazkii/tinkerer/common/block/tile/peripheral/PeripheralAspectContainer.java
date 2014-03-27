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
 * File Created @ [13 Sep 2013, 00:58:21 (GMT)]
 */

package vazkii.tinkerer.common.block.tile.peripheral;


import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;
import dan200.computer.api.ILuaContext;
import net.minecraft.nbt.NBTTagCompound;
import thaumcraft.api.aspects.IAspectContainer;
import vazkii.tinkerer.common.block.tile.peripheral.implementation.IAspectContainerImplementation;

public class PeripheralAspectContainer implements IHostedPeripheral {

    IAspectContainer container;

    public PeripheralAspectContainer(IAspectContainer container) {
        this.container = container;
    }

    @Override
    public String getType() {
        return "tt_aspectContainer";
    }

    @Override
    public String[] getMethodNames() {
        return new String[]{"getAspects", "getAspectCount"};
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
        switch (method) {
            case 0: {
                return IAspectContainerImplementation.getAspects(container);
            }
            case 1: {
                String aspectName = (String) arguments[0];
                return IAspectContainerImplementation.getAspectCount(container,aspectName);
            }
        }

        return null;
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
