package thaumic.tinkerer.common.peripheral.OpenComputers;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverTileEntity;
import net.minecraft.world.World;
import thaumcraft.common.tiles.TileJarBrain;

/**
 * Created by Katrina on 22/04/14.
 */
public class DriverBrainInAJar extends DriverTileEntity {
    @Override
    public Class<?> getTileEntityClass() {
        return TileJarBrain.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, int x, int y, int z) {
        return new Enviroment((TileJarBrain) world.getTileEntity(x, y, z));
    }

    public static final class Enviroment extends ManagedTileEntityEnvironment<TileJarBrain> {

        public Enviroment(TileJarBrain tileEntity) {
            super(tileEntity, "brainjar");
        }

        @Callback(doc = "function():number -- returns the amount of XP in this jar")
        public Object[] getXP(final Context context, final Arguments arguments) {
            return new Object[]{this.tileEntity.xp};
        }

    }
}
