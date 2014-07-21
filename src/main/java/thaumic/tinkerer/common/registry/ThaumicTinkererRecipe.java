package thaumic.tinkerer.common.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumic.tinkerer.common.core.handler.ConfigHandler;

public abstract class ThaumicTinkererRecipe {

	public abstract void registerRecipe();

	public static Object oreDictOrStack(ItemStack stack, String oreDict) {
		return OreDictionary.getOres(oreDict).isEmpty() && ConfigHandler.useOreDictMetal ? stack : oreDict;
	}

}
