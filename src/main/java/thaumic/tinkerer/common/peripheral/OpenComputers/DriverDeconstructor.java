package thaumic.tinkerer.common.peripheral.OpenComputers;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverTileEntity;
import net.minecraft.world.World;
import thaumcraft.common.tiles.TileDeconstructionTable;

/**
 * Created by Katrina on 22/04/14.
 */
public class DriverDeconstructor extends DriverTileEntity {
    @Override
    public Class<?> getTileEntityClass() {
        return TileDeconstructionTable.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, int x, int y, int z) {
        return new Enviroment((TileDeconstructionTable) world.getTileEntity(x, y, z));
    }

    public static final class Enviroment extends ManagedTileEntityEnvironment<TileDeconstructionTable> {

        public Enviroment(TileDeconstructionTable tileEntity) {
            super(tileEntity, "deconstructiontable");
        }

        @Callback(doc = "function():boolean -- returns if the table has an aspect waiting")
        public Object[] hasAspect(final Context context, final Arguments arguments) {
            return new Object[]{this.tileEntity.aspect != null};
        }

        @Callback(doc = "function():boolean -- returns if the table has an item in the slot")
        public Object[] hasItem(final Context context, final Arguments arguments) {
            return new Object[]{this.tileEntity.getStackInSlot(0) != null};
        }

        @Callback(doc = "function():string -- returns aspect in the deconstructor")
        public Object[] getAspect(final Context context, final Arguments arguments) {
            return this.tileEntity.aspect != null ? new Object[]{tileEntity.aspect.getTag()} : null;
        }

    }
}
