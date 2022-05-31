package com.nekokittygames.thaumictinkerer.common.helper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper {
    public static boolean oreDictCheck(IBlockState state, int oreDictNum)
    {
        ItemStack stack=new ItemStack(state.getBlock(),1,state.getBlock().getMetaFromState(state));
        if(stack.isEmpty())
            return false;
        int[] nums= OreDictionary.getOreIDs(stack);
        for(int i:nums) {
            if (i == oreDictNum)
                return true;
        }
        return false;

    }

    public static boolean oreDictCheck(IBlockState state, String oreDict) {
        return oreDictCheck(state,OreDictionary.getOreID(oreDict));
    }
}
