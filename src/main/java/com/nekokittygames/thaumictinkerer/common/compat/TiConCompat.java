package com.nekokittygames.thaumictinkerer.common.compat;

import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolHelper;


public class TiConCompat {


    public static boolean isTiConTool(ItemStack stack) {
        return stack.getItem() instanceof ToolCore;
    }

    public static boolean isRepairableTiCon(ItemStack stack) {
        if (!isTiConTool(stack))
            return false;
        return ToolHelper.getCurrentDurability(stack) != ToolHelper.getMaxDurability(stack);
    }


    public static int getDamage(ItemStack stack) {
        if (!isTiConTool(stack))
            return stack.getItemDamage();
        return ToolHelper.getMaxDurability(stack) - ToolHelper.getCurrentDurability(stack);
    }

    public static void fixDamage(ItemStack stack, int damage) {
        ToolHelper.repairTool(stack, damage);
    }
}
