package appeng.api.implementations;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Implemented on {@link Item}
 */
public interface ICraftingPatternItem
{

	/**
	 * Access Details about a pattern
	 *
	 * @param is pattern
	 * @param w crafting world
	 * @return details of pattern
	 */
	ICraftingPatternDetails getPatternForItem(ItemStack is, World w);
}
