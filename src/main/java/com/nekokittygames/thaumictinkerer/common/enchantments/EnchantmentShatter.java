package com.nekokittygames.thaumictinkerer.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentShatter extends Enchantment {
    public EnchantmentShatter(int id) {
        super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("shatter");
        this.setName("shatter");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return ench != TTEnchantments.desintegrate && ench != Enchantments.EFFICIENCY && ench != this;
    }

    @Override
    public int getMinEnchantability(int level)
    {
        return 20 + 3 * (level - 1);
    }
}