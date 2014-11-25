package appeng.api.implementations.tiles;

import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IStorageMonitorable;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Implemented on inventories that can share their inventories with other networks, best example, ME Interface.
 */
public interface ITileStorageMonitorable
{

	IStorageMonitorable getMonitorable(ForgeDirection side, BaseActionSource src);

}
