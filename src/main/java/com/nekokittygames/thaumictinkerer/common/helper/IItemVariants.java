package com.nekokittygames.thaumictinkerer.common.helper;

import net.minecraft.item.ItemStack;

public interface IItemVariants {
    String GetVariant(ItemStack stack);
    String[] GetVariants();
}
