package thaumic.tinkerer.common.peripheral.OpenComputers;

import li.cil.oc.api.network.Arguments;
import li.cil.oc.api.network.Callback;
import li.cil.oc.api.network.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverTileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.IEssentiaTransport;

/**
 * Created by Katrina on 22/04/14.
 */
public class DriverEssentiaTransport extends DriverTileEntity {
	@Override
	public Class<?> getTileEntityClass() {
		return IEssentiaTransport.class;
	}

	@Override
	public ManagedEnvironment createEnvironment(World world, int x, int y, int z) {
		return new Enviroment((IEssentiaTransport) world.getTileEntity(x, y, z));
	}

	public static final class Enviroment extends ManagedTileEntityEnvironment<IEssentiaTransport> {

		public Enviroment(IEssentiaTransport tileEntity) {
			super(tileEntity, "essentiaTransport");
		}

		@Callback(doc = "function(direction:number):boolean -- returns is the pipe connectable from this direction")
		public Object[] isConnectable(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.isConnectable(ForgeDirection.getOrientation(arguments.checkInteger(0))) };
		}

		@Callback(doc = "function(direction:number):boolean -- can pipe input from direction")
		public Object[] canInputFrom(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.canInputFrom(ForgeDirection.getOrientation(arguments.checkInteger(0))) };
		}

		@Callback(doc = "function(direction:number):boolean -- can pipe output to direction")
		public Object[] canOutputTo(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.canOutputTo(ForgeDirection.getOrientation(arguments.checkInteger(0))) };
		}

		@Callback(doc = "function(direction:number):string -- returns which aspect suction")
		public Object[] getSuctionType(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.getSuctionType(ForgeDirection.getOrientation(arguments.checkInteger(0))).getTag() };
		}

		@Callback(doc = "function(direction:number):number -- returnd amount of suction")
		public Object[] getSuctionAmount(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.getSuctionAmount(ForgeDirection.getOrientation(arguments.checkInteger(0))) };
		}

		@Callback(doc = "function(direction:number):string -- returns which essentia in pipe")
		public Object[] getEssentiaType(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.getEssentiaType(ForgeDirection.getOrientation(arguments.checkInteger(0))).getTag() };
		}

		@Callback(doc = "function(direction:number):number -- returnd amount of essentia")
		public Object[] getEssentiaAmount(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.getEssentiaAmount(ForgeDirection.getOrientation(arguments.checkInteger(0))) };
		}

		@Callback(doc = "function():number -- returns minimum suction")
		public Object[] getMinimumSuction(final Context context, final Arguments arguments) {
			return new Object[]{ this.tileEntity.getMinimumSuction() };
		}
	}
}
