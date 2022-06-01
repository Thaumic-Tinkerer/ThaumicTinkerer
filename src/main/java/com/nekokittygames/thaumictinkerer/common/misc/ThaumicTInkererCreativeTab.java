package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ThaumicTInkererCreativeTab extends CreativeTabs {
    public ThaumicTInkererCreativeTab() {
        super("thaumictinkerer");
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Objects.requireNonNull(ModBlocks.repairer));
    }
}
