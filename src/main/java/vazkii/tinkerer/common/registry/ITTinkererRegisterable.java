package vazkii.tinkerer.common.registry;

import vazkii.tinkerer.common.research.IRegisterableResearch;

public interface ITTinkererRegisterable {

	public IRegisterableResearch getResearchItem();

	public ThaumicTinkererRecipe getRecipeItem();
}
