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
 * File Created @ [12 Sep 2013, 17:10:55 (GMT)]
 */
package thaumic.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import cpw.mods.fml.common.Optional;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.helper.MiscHelper;

import java.util.List;

@Optional.InterfaceList({
        @Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
        @Optional.Interface(iface = "dan200.computercraft.api.peripheral.IPeripheral", modid = "ComputerCraft")
})
public class TileMagnet extends TileEntity implements IPeripheral, IMovableTile, SimpleComponent {

    @Override
    public void updateEntity() {
        int redstone = 0;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            redstone = Math.max(redstone, worldObj.getIndirectPowerLevelTo(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.ordinal()));

        if (redstone > 0) {
            double x1 = xCoord + 0.5;
            double y1 = yCoord + 0.5;
            double z1 = zCoord + 0.5;

            boolean blue = (getBlockMetadata() & 1) == 0;
            int speedMod = blue ? 1 : -1;
            double range = redstone / 2;

            AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x1 - range, yCoord, z1 - range, x1 + range, y1 + range, z1 + range);
            List<Entity> entities = worldObj.selectEntitiesWithinAABB(Entity.class, boundingBox, getEntitySelector());

            for (Entity entity : entities) {
                double x2 = entity.posX;
                double y2 = entity.posY;
                double z2 = entity.posZ;

                float distanceSqrd = blue ? (float) ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2)) : 1.1F;

                if (distanceSqrd > 1) {
                    MiscHelper.setEntityMotionFromVector(entity, new Vector3(x1, y1, z1), speedMod * 0.25F);
                    ThaumicTinkerer.tcProxy.sparkle((float) x2, (float) y2, (float) z2, blue ? 2 : 4);
                }
            }
        }
    }

    IEntitySelector getEntitySelector() {
        return new IEntitySelector() {

            @Override
            public boolean isEntityApplicable(Entity entity) {
                return entity instanceof EntityItem;
            }

        };
    }

    @Override
    public String getType() {
        return "tt_magnet";
    }

    @Override
    public String[] getMethodNames() {
        return new String[]{"isPulling", "setPulling", "getSignal"};
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) {
        switch (method) {
            case 0:
                return new Object[]{(getBlockMetadata() & 1) == 0};
            case 1:
                return setPullingImplementation((Boolean) arguments[0]);
            case 2:
                return gotSignalImplementation();
        }

        return null;
    }

    private Object[] gotSignalImplementation() {
        int redstone = 0;
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            redstone = Math.max(redstone, worldObj.getIndirectPowerLevelTo(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir.ordinal()));

        return new Object[]{redstone};
    }

    private Object[] setPullingImplementation(boolean argument) {
        int meta = (getBlockMetadata() & 2) + (argument ? 0 : 1);

        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 1 | 2);
        return null;
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void attach(IComputerAccess computer) {
        // NO-OP
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public void detach(IComputerAccess computer) {
        // NO-OP
    }

    @Override
    @Optional.Method(modid = "ComputerCraft")
    public boolean equals(IPeripheral other) {
        return this.equals((Object) other);
    }

    @Override
    public boolean prepareToMove() {
        return true;
    }

    @Override
    public void doneMoving() {

    }

    @Callback(doc = "function():boolean -- Returns Whether magnet is pushing or pulling")
    @Optional.Method(modid = "OpenComputers")
    public Object[] isPulling(Context context, Arguments args) throws Exception {
        return new Object[]{(getBlockMetadata() & 1) == 0};
    }

    @Callback(doc = "function(boolean):nil -- Sets Whether magnet is pushing or pulling")
    @Optional.Method(modid = "OpenComputers")
    public Object[] setPulling(Context context, Arguments args) throws Exception {
        setPullingImplementation(args.checkBoolean(0));
        return new Object[]{};
    }

    @Callback(doc = "function():boolean -- Sets Whether magnet is pushing or pulling")
    @Optional.Method(modid = "OpenComputers")
    public Object[] getSignal(Context context, Arguments args) throws Exception {
        return gotSignalImplementation();
    }

    @Override
    public String getComponentName() {
        return this.getType();
    }
}
