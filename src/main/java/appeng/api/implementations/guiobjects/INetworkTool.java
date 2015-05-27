package appeng.api.implementations.guiobjects;

import appeng.api.networking.IGridHost;
import net.minecraft.inventory.IInventory;

/**
 * Obtained via {@link IGuiItem} getGuiObject
 */
public interface INetworkTool extends IInventory, IGuiItemObject
{

	IGridHost getGridHost(); // null for most purposes.

}
