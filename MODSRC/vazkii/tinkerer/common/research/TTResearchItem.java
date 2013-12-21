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
 * File Created @ [8 Sep 2013, 16:53:14 (GMT)]
 */
package vazkii.tinkerer.common.research;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ResearchPage.PageType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TTResearchItem extends ResearchItem {

	public TTResearchItem(String par1, String par2) {
		super(par1, par2);
	}

	public TTResearchItem(String par1, String par2, AspectList tags, int par3, int par4, int par5, ItemStack icon) {
		super(par1, par2, tags, par3, par4, par5, icon);
	}

	public TTResearchItem(String par1, String par2, AspectList tags, int par3, int par4, int par5, ResourceLocation icon) {
		super(par1, par2, tags, par3, par4, par5, icon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName() {
		return StatCollector.translateToLocal("ttresearch.name." + key);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getText() {
		return StatCollector.translateToLocal("ttresearch.prefix") + StatCollector.translateToLocal("ttresearch.lore." + key);
	}

	@Override
	public ResearchItem setPages(ResearchPage... par) {
		for(ResearchPage page : par) {
			if(page.type == PageType.TEXT)
				page.text = "ttresearch.page." + key + "." + page.text;

			if(checkInfusion() && page.type == PageType.INFUSION_CRAFTING) {
				if(parentsHidden == null || parentsHidden.length == 0)
					parentsHidden = new String[] { "INFUSION" };
				else {
					String[] newParents = new String[parentsHidden.length + 1];
					newParents[0] = "INFUSION";
					for(int i = 0; i < parentsHidden.length; i++)
						newParents[i + 1] = parentsHidden[i];
					parentsHidden = newParents;
				}
			}
		}


		return super.setPages(par);
	}
	
	boolean checkInfusion() {
		return true;
	}
}
