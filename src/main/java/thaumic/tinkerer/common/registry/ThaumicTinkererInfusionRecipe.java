package thaumic.tinkerer.common.registry;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.common.config.ConfigResearch;

public class ThaumicTinkererInfusionRecipe extends ThaumicTinkererRecipe {
	private final String name;
	private final String research;
	private final Object output;
	private final int instability;
	private final AspectList aspects;
	private final ItemStack input;
	private final ItemStack[] stuff;

	public ThaumicTinkererInfusionRecipe(String name, String research, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {

		this.name = name;
		this.research = research;
		this.output = output;
		this.instability = instability;
		this.aspects = aspects;
		this.input = input;
		this.stuff = stuff;
	}

	public ThaumicTinkererInfusionRecipe(String name, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
		this(name, name, output, instability, aspects, input, stuff);
	}

	@Override
	public void registerRecipe() {
		InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(research, output, instability, aspects, input, stuff);
		ConfigResearch.recipes.put(name, recipe);
	}
}
