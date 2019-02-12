package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ThaumicTInkererCreativeTab extends CreativeTabs {
    public ThaumicTInkererCreativeTab() {
        super("thaumictinkerer");
    }

    @NotNull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Objects.requireNonNull(ModBlocks.repairer));
    }
}
