package com.nekokittygames.Thaumic.Tinkerer.common.research;

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTConfig;
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibResearch;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

/**
 * Created by Katrina on 22/05/2015.
 */
public class TTResearchItem extends ResearchItem {

    public int warp = 0;


    public TTResearchItem(String key) {
        super(key, LibResearch.CATEGORY_THAUMICTINKERER());
    }

    public TTResearchItem(String key, AspectList tags, int col, int row, int complex, ItemStack icon, ResearchPage... pages) {
        super(key, LibResearch.CATEGORY_THAUMICTINKERER(), tags, col, row, complex, icon);
        setPages(pages);
    }

    public TTResearchItem(String key, AspectList tags, int col, int row, int complex, ResourceLocation icon, ResearchPage... pages) {
        super(key, LibResearch.CATEGORY_THAUMICTINKERER(), tags, col, row, complex, icon);
        setPages(pages);
    }

    public TTResearchItem setWarp(int warp) {
        this.warp = warp;
        return this;
    }

    @Override
    public ResearchItem setPages(ResearchPage... par)
    {

        for(ResearchPage page:par) {
            if (page.type == ResearchPage.PageType.TEXT)
            page.text = "ttresearch.page." + key + "." + page.text;
            if (checkInfusion() && page.type == ResearchPage.PageType.INFUSION_CRAFTING) {
                if (parentsHidden == null || parentsHidden.length == 0)
                    parentsHidden = new String[]{"INFUSION"};
                else {
                    String[] newParents = new String[parentsHidden.length + 1];
                    newParents[0] = "INFUSION";
                    //noinspection ManualArrayCopy
                    for (int i = 0; i < parentsHidden.length; i++)
                        newParents[i + 1] = parentsHidden[i];
                    parentsHidden = newParents;
                }
            }
        }
        return super.setPages(par);

    }

    public Boolean checkInfusion()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getName() {
        return LibResearch.lang().translate("name." + key);
    }

    public String getPrefix()
    {
        return "prefix";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getText()
    {

        return (TTConfig.General$.MODULE$.useTooltipIndicators()? LibResearch.lang().translate(getPrefix()):"")+LibResearch.lang().translate("lore." + key);

    }


    @Override
    public ResearchItem registerResearchItem() {
        if (warp != 0) {
            ThaumcraftApi.addWarpToResearch(key, warp);
        }
        return super.registerResearchItem();
    }


}
