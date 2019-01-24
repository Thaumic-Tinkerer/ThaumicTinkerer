package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;


public class CreativeTabThaumicTinkerer extends CreativeTabs {
    private static CreativeTabThaumicTinkerer instance;

    private CreativeTabThaumicTinkerer() {
        super(LibMisc.MOD_ID);
    }

    public static CreativeTabThaumicTinkerer getInstance() {
        if (instance == null)
            instance = new CreativeTabThaumicTinkerer();
        return instance;
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.share_book);
    }
}
