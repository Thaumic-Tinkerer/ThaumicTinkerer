/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import thaumcraft.api.items.ItemsTC;

public class Materials {
    public static Item.ToolMaterial BLOOD_MATERIAL;

    static {
        BLOOD_MATERIAL=EnumHelper.addToolMaterial("BLOOD", 3, 500, 7.0F, 10F, 22).setRepairItem(new ItemStack(ItemsTC.ingots));
    }
}
