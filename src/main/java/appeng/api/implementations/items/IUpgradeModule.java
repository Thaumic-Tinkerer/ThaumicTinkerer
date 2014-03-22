package appeng.api.implementations.items;

import net.minecraft.item.ItemStack;
import appeng.api.config.Upgrades;

public interface IUpgradeModule
{

	/**
	 * @param itemstack
	 * @return null, or a valid upgrade type.
	 */
	Upgrades getType(ItemStack itemstack);

}
