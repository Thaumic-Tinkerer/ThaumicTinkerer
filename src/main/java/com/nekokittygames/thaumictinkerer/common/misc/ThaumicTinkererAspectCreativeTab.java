/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ThaumicTinkererAspectCreativeTab extends CreativeTabs {
    public ThaumicTinkererAspectCreativeTab() {
        super("thaumictinkererAspect");
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Objects.requireNonNull(ModItems.mob_aspect));
    }
}
