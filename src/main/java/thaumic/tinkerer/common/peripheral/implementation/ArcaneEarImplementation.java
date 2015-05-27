package thaumic.tinkerer.common.peripheral.implementation;

import thaumcraft.common.tiles.TileSensor;

/**
 * Created by Katrina on 21/04/14.
 */
public class ArcaneEarImplementation {

    public static Object[] getNote(TileSensor ear) {
        return new Double[]{(double) ear.note};
    }

    public static Object[] setNote(TileSensor ear, byte note) {
        ear.note = note;
        return new Object[]{};
    }
}
