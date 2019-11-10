package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.client.misc.ClientHelper;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemSpellbindingCloth extends TTItem {
    public ItemSpellbindingCloth() {
        super(LibItemNames.SPELLBINDING_CLOTH);
        setMaxDamage(TTConfig.spellbindingClothUses);
        setMaxStackSize(1);
        setNoRepair();
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        itemStack.setItemDamage(itemStack.getItemDamage()+1);
        return itemStack;
    }

    @Override
    public boolean hasContainerItem() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

}
