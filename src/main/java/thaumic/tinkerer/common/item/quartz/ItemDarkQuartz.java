package thaumic.tinkerer.common.item.quartz;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ItemBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipeMulti;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

public class ItemDarkQuartz extends ItemBase {
	@Override
	public String getItemName() {
		return LibItemNames.DARK_QUARTZ;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		IRegisterableResearch researchItem = (IRegisterableResearch) new TTResearchItem(LibResearch.KEY_DARK_QUARTZ, new AspectList(), -2, 2, 0, new ItemStack(this), new ResearchPage("0"), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 0), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 1), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 2), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 3), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 4), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 5))
				.setStub().setAutoUnlock().setRound().registerResearchItem();
		return researchItem;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererRecipeMulti(
				new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 0, new ItemStack(this, 8),
						"QQQ", "QCQ", "QQQ",
						'Q', Items.quartz,
						'C', Items.coal),
				new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 0, new ItemStack(this, 8),
						"QQQ", "QCQ", "QQQ",
						'Q', Items.quartz,
						'C', new ItemStack(Items.coal, 1, 1))
		);
	}

}
