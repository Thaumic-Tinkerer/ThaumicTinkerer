package ic2.api.energy;


/**
 * Provides access to the energy network.
 * <p/>
 * The old EnergyNet methods missing in IEnergyNet have been migrated to events (load, unload) or
 * removed (tiles no longer emit energy actively, the energy net implementation requests it).
 * <p/>
 * See ic2/api/energy/usage.txt for an overall description of the energy net api.
 */
public final class EnergyNet {
    /**
     * Instance of the global EnergyNet class.
     */
    public static IEnergyNet instance;
}

