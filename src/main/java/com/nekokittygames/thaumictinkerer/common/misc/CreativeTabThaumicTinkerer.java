package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.ItemsTC;


public class CreativeTabThaumicTinkerer extends CreativeTabs {
    private static CreativeTabThaumicTinkerer instance;
    public static CreativeTabThaumicTinkerer getInstance()
    {
        if(instance==null)
            instance=new CreativeTabThaumicTinkerer();
        return instance;
    }

    private CreativeTabThaumicTinkerer() {
        super(LibMisc.MOD_ID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.share_book);
    }
}
