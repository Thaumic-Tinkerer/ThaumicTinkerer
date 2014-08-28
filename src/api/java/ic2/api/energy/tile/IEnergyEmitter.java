package ic2.api.energy.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * For internal/multi-block usage only.
 *
 * @see ic2.api.energy.tile.IEnergySource
 * @see ic2.api.energy.tile.IEnergyConductor
 * <p/>
 * See ic2/api/energy/usage.txt for an overall description of the energy net api.
 */
public interface IEnergyEmitter extends IEnergyTile {
    /**
     * Determine if this emitter can emit energy to an adjacent receiver.
     * <p/>
     * The TileEntity in the receiver parameter is what was originally added to the energy net,
     * which may be normal in-world TileEntity, a delegate or an IMetaDelegate.
     *
     * @param receiver  receiver, may also be null or an IMetaDelegate
     * @param direction direction the receiver is from the emitter
     * @return Whether energy should be emitted
     */
    boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction);
}

