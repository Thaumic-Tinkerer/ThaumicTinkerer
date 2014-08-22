/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [Dec 21, 2013, 8:14:36 PM (GMT)]
 */
package thaumic.tinkerer.common.research;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import thaumic.tinkerer.common.lib.LibResearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KamiResearchItem extends TTResearchItem {

	public static List<String> Blacklist = new ArrayList<String>();

	static {
		Blacklist.add("MINILITH");
	}

	public KamiResearchItem(String par1, AspectList tags, int par3, int par4, int par5, ItemStack icon) {
		super(par1, tags, par3, par4, par5, icon);
		setConcealed();
	}

	@Override
	public ResearchItem setPages(ResearchPage... par) {
		List<String> requirements = parentsHidden == null || parentsHidden.length == 0 ? new ArrayList() : new ArrayList(Arrays.asList(parentsHidden));

		if (!isAutoUnlock())
			for (String categoryStr : ResearchCategories.researchCategories.keySet()) {
				ResearchCategoryList category = ResearchCategories.researchCategories.get(categoryStr);
				for (String tag : category.research.keySet()) {
					ResearchItem research = category.research.get(tag);

					if (research.isLost() || (research.parentsHidden == null && research.parents == null) || research.isVirtual() || research instanceof KamiResearchItem || requirements.contains(tag))
						continue;
                    if (research.getAspectTriggers() != null || research.getEntityTriggers() != null || research.getItemTriggers() != null) {
                        continue;
                    }
                    if (research.category.equals(LibResearch.CATEGORY_THAUMICTINKERER) || research.category.equals("BASICS") || research.category.equals("GOLEMANCY") || research.category.equals("ARTIFICE") || research.category.equals("ALCHEMY") || research.category.equals("THAUMATURGY")) {
                        boolean found = false;
                        for (String black : Blacklist)
                            if (tag.startsWith(black)) {
                                found = true;
                            }
                        if (tag.endsWith("KAMI"))
                            found = true;
                        if (found)
                            continue;
                        requirements.add(tag);
                    }
                }
			}

		parentsHidden = requirements.toArray(new String[requirements.size()]);

		return super.setPages(par);
	}

	@Override
	String getPrefix() {
		return super.getPrefix() + ".kami";
	}

	@Override
	boolean checkInfusion() {
		return false;
	}
}
