package appeng.api.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Gives easy access to different part of the various, items/blocks/materials in AE.
 */
public interface AEItemDefinition
{

	/**
	 * @return the {@link net.minecraft.block.Block} Implementation if applicable
	 */
	Block block();

	/**
	 * @return the {@link net.minecraft.item.Item} Implementation if applicable
	 */
	Item item();

	/**
	 * @return the {@link net.minecraft.tileentity.TileEntity} Class if applicable.
	 */
	Class<? extends TileEntity> entity();

	/**
	 * @return an {@link net.minecraft.item.ItemStack} with specified quantity of this item.
	 */
	ItemStack stack(int stackSize);

	/**
	 * Compare {@link net.minecraft.item.ItemStack} with this {@link appeng.api.util.AEItemDefinition}
	 * 
	 * @param comparableItem
	 * @return true if the item stack is a matching item.
	 */
	boolean sameAs(ItemStack comparableItem);

}
