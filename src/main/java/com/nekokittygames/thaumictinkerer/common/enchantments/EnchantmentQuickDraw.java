package com.nekokittygames.thaumictinkerer.common.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentQuickDraw extends Enchantment {
    public EnchantmentQuickDraw(int id) {
        super(Rarity.RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("quickdraw");
        this.setName("quickdraw");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return ench != Enchantments.PUNCH;
    }

    @Override
    public int getMinEnchantability(int ench)
    {
        return 10 + 12 * (ench - 1);
    }

    @Override
    public int getMaxEnchantability(int ench)
    {
        return this.getMinEnchantability(ench) + 30;
    }
}