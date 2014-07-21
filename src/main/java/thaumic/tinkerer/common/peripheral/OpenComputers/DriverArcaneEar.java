package thaumic.tinkerer.common.peripheral.OpenComputers;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverTileEntity;
import net.minecraft.world.World;
import thaumcraft.common.tiles.TileSensor;
import thaumic.tinkerer.common.peripheral.implementation.ArcaneEarImplementation;

/**
 * Created by Katrina on 21/04/14.
 */
public class DriverArcaneEar extends DriverTileEntity {
	@Override
	public Class<?> getTileEntityClass() {
		return TileSensor.class;
	}

	@Override
	public ManagedEnvironment createEnvironment(World world, int x, int y, int z) {
		return new Enviroment((TileSensor) world.getTileEntity(x, y, z));
	}

	public static final class Enviroment extends ManagedTileEntityEnvironment<TileSensor> {

		public Enviroment(TileSensor tileEntity) {
			super(tileEntity, "ArcaneEar");
		}

		@Callback(doc = "function():number -- returns the note the ear is set to")
		public Object[] getNote(final Context context, final Arguments arguments) {
			return ArcaneEarImplementation.getNote(this.tileEntity);
		}

		@Callback(doc = "function(note:number):nil -- sets the note the ear listens for")
		public Object[] setNote(final Context context, final Arguments arguments) {
			return ArcaneEarImplementation.setNote(this.tileEntity, (byte) arguments.checkInteger(0));
		}
	}
}
