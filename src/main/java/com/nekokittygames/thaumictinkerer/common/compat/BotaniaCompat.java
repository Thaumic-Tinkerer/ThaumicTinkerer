package com.nekokittygames.thaumictinkerer.common.compat;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibOreDict;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

public class BotaniaCompat {
    @GameRegistry.ObjectHolder("botania:quartzTypeDark")
    private static @Nullable
    Block botaniaQuartz;

    public static void addOreDict() {
        if(botaniaQuartz!=null) {
            OreDictionary.registerOre(LibOreDict.BLACK_QUARTZ_BLOCK, new ItemStack(botaniaQuartz, 1, 0));
            ThaumicTinkerer.logger.info("Hi Botania, we have unfinished business you and I");
        }
        else
            ThaumicTinkerer.logger.info("Botania not found, Sad now.");
    }
}
