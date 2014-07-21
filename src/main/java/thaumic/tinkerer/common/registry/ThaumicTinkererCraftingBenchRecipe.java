package thaumic.tinkerer.common.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import thaumcraft.common.config.ConfigResearch;

import java.util.List;

public class ThaumicTinkererCraftingBenchRecipe extends ThaumicTinkererRecipe {

	private final String name;
	private final ItemStack output;
	private final Object[] stuff;

	public ThaumicTinkererCraftingBenchRecipe(String name, ItemStack output, Object... stuff) {

		this.name = name;
		this.output = output;
		this.stuff = stuff;
	}

	@Override
	public void registerRecipe() {

		GameRegistry.addRecipe(output, stuff);
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		if (name != null && name.length() != 0)
			ConfigResearch.recipes.put(name, recipeList.get(recipeList.size() - 1));
	}
}
