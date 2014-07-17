package vazkii.tinkerer.common.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThaumicTinkererRecipeMulti extends ThaumicTinkererRecipe {

	private List<ThaumicTinkererRecipe> recipes;

	public ThaumicTinkererRecipeMulti(ThaumicTinkererRecipe... recipes) {
		this.recipes = Arrays.asList(recipes);
	}

	public ThaumicTinkererRecipeMulti() {
		this.recipes = new ArrayList<ThaumicTinkererRecipe>();
	}

	public void addRecipe(ThaumicTinkererRecipe recipe) {
		recipes.add(recipe);
	}

	@Override
	public void registerRecipe() {
		for (ThaumicTinkererRecipe recipe : recipes) {
			recipe.registerRecipe();
		}
	}
}
