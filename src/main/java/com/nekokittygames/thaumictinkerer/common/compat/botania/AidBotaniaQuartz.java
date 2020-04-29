/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.compat.botania;

import com.nekokittygames.thaumictinkerer.common.research.theorycraft.CardExperience;
import net.minecraft.block.Block;
import thaumcraft.api.research.theorycraft.ITheorycraftAid;
import thaumcraft.api.research.theorycraft.TheorycraftCard;

public class AidBotaniaQuartz implements ITheorycraftAid {
    Block curBlock;
    public AidBotaniaQuartz(Block researchBlock) {
        curBlock=researchBlock;
    }

    @Override
    public Object getAidObject() {
        return curBlock;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<TheorycraftCard>[] getCards() {
        return new Class[]{ CardExperience.class};
    }
}
