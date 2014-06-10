package vazkii.tinkerer.common.item.quartz;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ItemBase;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

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
	public TTResearchItem getResearchItem() {
		TTResearchItem researchItem = new TTResearchItem(LibResearch.KEY_DARK_QUARTZ, new AspectList(), -2, 2, 0, new ItemStack(this), new ResearchPage("0"), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 0), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 1), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 2), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 3), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 4), ResearchHelper.recipePage(LibResearch.KEY_DARK_QUARTZ + 5));
		researchItem.setStub().setAutoUnlock().setRound().registerResearchItem();
		return researchItem;
	}

}
