package thaumic.tinkerer.common.registry;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.common.config.ConfigResearch;

public class ThaumicTinkererCrucibleRecipe extends ThaumicTinkererRecipe {

	private final String name;
	private final ItemStack output;
	private final ItemStack input;
	private final AspectList aspects;

	public ThaumicTinkererCrucibleRecipe(String name, ItemStack output, ItemStack input, AspectList aspects) {

		this.name = name;
		this.output = output;
		this.input = input;
		this.aspects = aspects;
	}

	@Override
	public void registerRecipe() {
		CrucibleRecipe recipe = ThaumcraftApi.addCrucibleRecipe(name, output, input, aspects);
		ConfigResearch.recipes.put(name, recipe);
	}
}
