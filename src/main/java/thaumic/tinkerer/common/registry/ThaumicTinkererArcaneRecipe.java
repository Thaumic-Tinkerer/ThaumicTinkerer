package thaumic.tinkerer.common.registry;

import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.common.config.ConfigResearch;

public class ThaumicTinkererArcaneRecipe extends ThaumicTinkererRecipe {

	public String name;
	public String research;
	public ItemStack output;
	public AspectList aspects;
	private final Object[] stuff;
	public Object[] recipies;

	public ThaumicTinkererArcaneRecipe(String name, String research, ItemStack output, AspectList aspects, Object... stuff) {
		this.name = name;
		this.research = research;
		this.output = output;
		this.aspects = aspects;
		this.stuff = stuff;
	}

	@Override
	public void registerRecipe() {
		ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(research, output, aspects, stuff);
		ConfigResearch.recipes.put(name, recipe);
	}
}
