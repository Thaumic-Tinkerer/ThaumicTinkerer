package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ThaumicTinkererMainCreativeTab extends CreativeTabs {
    public ThaumicTinkererMainCreativeTab() {
        super("thaumictinkererMain");
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Objects.requireNonNull(ModBlocks.repairer));
    }
}
