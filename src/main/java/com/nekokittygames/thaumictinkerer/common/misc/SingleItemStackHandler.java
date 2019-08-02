package com.nekokittygames.thaumictinkerer.common.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class SingleItemStackHandler extends ItemStackHandler {


    public SingleItemStackHandler() {
    }

    public SingleItemStackHandler(int size) {
        super(size);
    }

    public SingleItemStackHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
