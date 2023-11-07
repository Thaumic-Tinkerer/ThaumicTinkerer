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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

public class ItemSpellbindingCloth extends TTItem {
    public ItemSpellbindingCloth() {
        super(LibItemNames.SPELLBINDING_CLOTH);
        setMaxDamage(TTConfig.spellbindingClothUses);
        setMaxStackSize(1);
        setNoRepair();
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
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack itemStack) {
        ItemStack newStack = itemStack.copy();
        newStack.setItemDamage(itemStack.getItemDamage() + 1);
        return newStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRGBDurabilityForDisplay(ItemStack par1ItemStack) {
        return Color.HSBtoRGB(0.75F, ((float) par1ItemStack.getMaxDamage() - (float) par1ItemStack.getItemDamage()) / par1ItemStack.getMaxDamage() * 0.5F, 1F);
    }
}
