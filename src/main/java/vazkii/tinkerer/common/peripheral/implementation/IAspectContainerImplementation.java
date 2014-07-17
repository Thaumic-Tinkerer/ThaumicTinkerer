package vazkii.tinkerer.common.peripheral.implementation;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.common.tiles.TileJarFillable;

import java.util.HashMap;

/**
 * Created by Katrina on 27/03/14.
 */
public class IAspectContainerImplementation {
	public static Object[] getAspects(IAspectContainer container) {
		HashMap<Double, String> returnStuff = new HashMap<Double, String>();
		double i = 1;
		if (container instanceof TileJarFillable && ((TileJarFillable) container).aspectFilter != null) {
			returnStuff.put(i++, ((TileJarFillable) container).aspectFilter.getTag());
			return new Object[]{ returnStuff };
		}
		if (container.getAspects() == null || container.getAspects().size() == 0)
			return new Object[]{ returnStuff };

		for (Aspect aspect : container.getAspects().getAspectsSorted())
			returnStuff.put(i++, aspect.getTag());

		return new Object[]{ returnStuff };
	}

	public static Object[] getAspectCount(IAspectContainer container, String aspectName) {

		Aspect aspect = Aspect.getAspect(aspectName);

		if (container.getAspects() == null)
			return new Object[]{ 0 };

		return new Object[]{ container.getAspects().getAmount(aspect) };
	}
}
