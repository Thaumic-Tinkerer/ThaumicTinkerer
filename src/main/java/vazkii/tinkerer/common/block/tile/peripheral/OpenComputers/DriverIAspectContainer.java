package vazkii.tinkerer.common.block.tile.peripheral.OpenComputers;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverTileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.IAspectContainer;
import vazkii.tinkerer.common.block.tile.peripheral.implementation.IAspectContainerImplementation;

/**
 * Created by Katrina on 27/03/14.
 */
public class DriverIAspectContainer extends DriverTileEntity {
	@Override
	public Class<?> getTileEntityClass() {
		return IAspectContainer.class;
	}

	@Override
	public ManagedEnvironment createEnvironment(World world, int x, int y, int z) {
		return new Enviroment((IAspectContainer) world.getTileEntity(x, y, z));
	}

	public static final class Enviroment extends ManagedTileEntityEnvironment<IAspectContainer> {

		public Enviroment(IAspectContainer tileEntity) {
			super(tileEntity, "IAspectContainer");
		}

		@Callback(doc = "function():table -- returns a list of tables containing aspect and quantity")
		public Object[] getAspects(final Context context, final Arguments arguments) {
			return IAspectContainerImplementation.getAspects(this.tileEntity);
		}

		@Callback(doc = "function(aspectName:string):number -- returns the amount of aspect in the block")
		public Object[] getAspectCount(final Context context, final Arguments arguments) {
			final String aspectName = arguments.checkString(0);
			return IAspectContainerImplementation.getAspectCount(this.tileEntity, aspectName);
		}
	}
}
