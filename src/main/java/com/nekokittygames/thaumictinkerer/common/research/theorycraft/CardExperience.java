/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.research.theorycraft;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.api.research.theorycraft.ResearchTableData;
import thaumcraft.api.research.theorycraft.TheorycraftCard;

public class CardExperience extends TheorycraftCard {
    @Override
    public int getInspirationCost() {
        return 1;
    }

    @Override
    public String getLocalizedName() {
        return ThaumicTinkerer.proxy.localize("thaumictinkerer.card.experience.name");
    }

    @Override
    public String getResearchCategory() {
        return "THAUMIC_TINKERER";
    }

    @Override
    public String getLocalizedText() {
        return ThaumicTinkerer.proxy.localize("thaumictinkerer.card.experience.text");
    }

    @Override
    public boolean activate(EntityPlayer entityPlayer, ResearchTableData data) {
        if (data.table != null && entityPlayer.experienceLevel>=4) {
            entityPlayer.addExperienceLevel(-4);
            data.addTotal(this.getResearchCategory(), 25);
            return true;
        } else {
            return false;
        }
    }
}
