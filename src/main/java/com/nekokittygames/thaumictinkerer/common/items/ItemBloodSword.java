/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import net.minecraft.item.ItemSword;
import thaumcraft.api.ThaumcraftMaterials;

public class ItemBloodSword extends ItemSword {
    private String baseName;
    public ItemBloodSword() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM);
        baseName = "blood_sword";
        TTItem.setItemName(this, baseName);
        if (isInCreativeTab())
            setCreativeTab(ThaumicTinkerer.getTab());
    }

    private boolean isInCreativeTab() {
        return true;
    }
}
