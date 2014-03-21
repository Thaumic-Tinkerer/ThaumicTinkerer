package appeng.api.implementations.tiles;

import net.minecraft.inventory.IInventory;

public interface ISegmentedInventory
{

	/**
	 * Access an internal inventory, note, not all inventories contain real items, some may be ghost items, and treating
	 * them a real inventories will result in duplication.
	 * 
	 * @param name
	 * @return
	 */
	IInventory getInventoryByName(String name);

}
