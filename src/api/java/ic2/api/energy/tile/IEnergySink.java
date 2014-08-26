package ic2.api.energy.tile;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Allows a tile entity (mostly a machine) to receive energy.
 * <p/>
 * See ic2/api/energy/usage.txt for an overall description of the energy net api.
 */
public interface IEnergySink extends IEnergyAcceptor {
    /**
     * Determine how much energy the sink accepts.
     * <p/>
     * Make sure that injectEnergy() does accepts energy if demandsEnergy() returns anything > 0.
     *
     * @return max accepted input in eu
     * @note Modifying the energy net from this method is disallowed.
     */
    double getDemandedEnergy();

    /**
     * Determine the tier of this energy sink.
     * 1 = LV, 2 = MV, 3 = HV, 4 = EV etc.
     *
     * @return tier of this energy sink
     * @note Modifying the energy net from this method is disallowed.
     * @note Return Integer.MAX_VALUE to allow any voltage.
     */
    int getSinkTier();

    /**
     * Transfer energy to the sink.
     * <p/>
     * It's highly recommended to accept all energy by letting the internal buffer overflow to
     * increase the performance and accuracy of the distribution simulation.
     *
     * @param directionFrom direction from which the energy comes from
     * @param amount        energy to be transferred
     * @return Energy not consumed (leftover)
     */
    double injectEnergy(ForgeDirection directionFrom, double amount, double voltage);
}

