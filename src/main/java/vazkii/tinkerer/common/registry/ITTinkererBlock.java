package vazkii.tinkerer.common.registry;

import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;

/**
 * Created by localmacaccount on 6/11/14.
 */
public interface ITTinkererBlock {

	public ArrayList<Object> getSpecialParameters();

	public String getBlockName();

	public boolean shouldRegister();

	public boolean shouldDisplayInTab();

	public IRegisterableResearch getResearchItem();

	public ThaumicTinkererRecipe getRecipeItem();

}
