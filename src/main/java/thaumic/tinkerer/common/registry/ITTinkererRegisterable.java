package thaumic.tinkerer.common.registry;

import thaumic.tinkerer.common.research.IRegisterableResearch;

public interface ITTinkererRegisterable {

	public IRegisterableResearch getResearchItem();

	public ThaumicTinkererRecipe getRecipeItem();
}
