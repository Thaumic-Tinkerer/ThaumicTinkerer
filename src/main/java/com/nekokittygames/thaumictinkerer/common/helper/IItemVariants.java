package com.nekokittygames.thaumictinkerer.common.helper;

import net.minecraft.item.ItemStack;

import java.util.Set;

public interface IItemVariants {
    String GetVariant(ItemStack stack);
    Set<String> GetVariants();
}
