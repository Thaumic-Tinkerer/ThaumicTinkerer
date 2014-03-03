package vazkii.tinkerer.common.block.tile.peripheral;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IHostedPeripheral;
import dan200.computer.api.ILuaContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.IEssentiaTransport;

/**
 * Created by Katrina on 03/03/14.
 */
public class PeripheralEssentiaTransport implements IHostedPeripheral {

    IEssentiaTransport pipe;
    public PeripheralEssentiaTransport(IEssentiaTransport input)
    {
        pipe=input;
    }

    /**
     * A method called on each hosted peripheral once per tick, on the main thread
     * over the lifetime of the turtle or block. May be used to update the state
     * of the peripheral, and may interact with IComputerAccess or ITurtleAccess
     * however it likes at this time.
     */
    @Override
    public void update() {

    }

    /**
     * A method called whenever data is read from the Turtle's NBTTag,
     * over the lifetime of the turtle. You should only use this for
     * reading data you want to stay with the peripheral.
     *
     * @param nbttagcompound The peripheral's NBTTag
     */
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

    }

    /**
     * A method called whenever data is written to the Turtle's NBTTag,
     * over the lifetime of the turtle. You should only use this for
     * writing data you want to stay with the peripheral.
     *
     * @param nbttagcompound The peripheral's NBTTag.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

    }

    /**
     * Should return a string that uniquely identifies this type of peripheral.
     * This can be queried from lua by calling peripheral.getType()
     *
     * @return A string identifying the type of peripheral.
     */
    @Override
    public String getType() {
        return "tt_AspectTransport";
    }

    /**
     * Should return an array of strings that identify the methods that this
     * peripheral exposes to Lua. This will be called once before each attachment,
     * and should not change when called multiple times.
     *
     * @return An array of strings representing method names.
     * @see #callMethod
     */
    @Override
    public String[] getMethodNames() {
        return new String[]{"isConnectable","canInputFrom","canOutputTo","getSuctionType","getSuctionAmount","getEssentiaType","getEssentiaAmount","getMinimumSuction"};
    }

    /**
     * This is called when a lua program on an attached computer calls peripheral.call() with
     * one of the methods exposed by getMethodNames().<br>
     * <br>
     * Be aware that this will be called from the ComputerCraft Lua thread, and must be thread-safe
     * when interacting with minecraft objects.
     *
     * @param computer The interface to the computer that is making the call. Remember that multiple
     *                 computers can be attached to a peripheral at once.
     * @return An array of objects, representing values you wish to return to the lua program.<br>
     * Integers, Doubles, Floats, Strings, Booleans and null be converted to their corresponding lua type.<br>
     * All other types will be converted to nil.<br>
     * You may return null to indicate no values should be returned.
     * @param    context        The context of the currently running lua thread. This can be used to wait for events
     * or otherwise yield.
     * @param    method        An integer identifying which of the methods from getMethodNames() the computer
     * wishes to call. The integer indicates the index into the getMethodNames() table
     * that corresponds to the string passed into peripheral.call()
     * @param    arguments    An array of objects, representing the arguments passed into peripheral.call().<br>
     * Lua values of type "string" will be represented by Object type String.<br>
     * Lua values of type "number" will be represented by Object type Double.<br>
     * Lua values of type "boolean" will be represented by Object type Boolean.<br>
     * Lua values of any other type will be represented by a null object.<br>
     * This array will be empty if no arguments are passed.
     * @throws Exception    If you throw any exception from this function, a lua error will be raised with the
     * same message as your exception. Use this to throw appropriate errors if the wrong
     * arguments are supplied to your method.
     * @see #getMethodNames
     */
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
public static int GetDirection(Object obj)
{
    Double num=(Double)obj;
    return (int)num.intValue();
}
    /**
     * Is called before the computer attempts to attach to the peripheral, and should return whether to allow
     * the attachment. Use this to restrict the number of computers that can attach, or to limit attachments to
     * certain world directions.<br>
     * If true is returned, attach() will be called shortly afterwards, and the computer will be able to make method calls.
     * If false is returned, attach() will not be called, and the peripheral will be invisible to the computer.
     *
     * @param side The world direction (0=bottom, 1=top, etc) that the computer lies relative to the peripheral.
     * @return Whether to allow the attachment, as a boolean.
     * @see #attach
     */
    @Override
    public boolean canAttachToSide(int side) {
        return true;
    }

    /**
     * Is called when canAttachToSide has returned true, and a computer is attaching to the peripheral.
     * This will occur when a peripheral is placed next to an active computer, when a computer is turned on next to a peripheral,
     * or when a turtle travels into a square next to a peripheral.
     * Between calls to attach() and detach(), the attached computer can make method calls on the peripheral using peripheral.call().
     * This method can be used to keep track of which computers are attached to the peripheral, or to take action when attachment
     * occurs.<br>
     * <br>
     * Be aware that this will be called from the ComputerCraft Lua thread, and must be thread-safe
     * when interacting with minecraft objects.
     *
     * @param computer The interface to the computer that is being attached. Remember that multiple
     *                 computers can be attached to a peripheral at once.
     * @see        #canAttachToSide
     * @see        #detach
     */
    @Override
    public void attach(IComputerAccess computer) {

    }

    /**
     * Is called when a computer is detaching from the peripheral.
     * This will occur when a computer shuts down, when the peripheral is removed while attached to computers,
     * or when a turtle moves away from a square attached to a peripheral.
     * This method can be used to keep track of which computers are attached to the peripheral, or to take action when detachment
     * occurs.<br>
     * <br>
     * Be aware that this will be called from the ComputerCraft Lua thread, and must be thread-safe
     * when interacting with minecraft objects.
     *
     * @param computer The interface to the computer that is being detached. Remember that multiple
     *                 computers can be attached to a peripheral at once.
     * @see        #canAttachToSide
     * @see        #detach
     */
    @Override
    public void detach(IComputerAccess computer) {

    }
}
