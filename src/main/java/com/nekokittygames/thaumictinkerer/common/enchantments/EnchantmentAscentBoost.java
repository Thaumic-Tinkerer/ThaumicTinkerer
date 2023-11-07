package com.nekokittygames.thaumictinkerer.common.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentAscentBoost extends Enchantment {
    public EnchantmentAscentBoost(int id) {
        super(Rarity.RARE, EnumEnchantmentType.ARMOR_LEGS, new EntityEquipmentSlot[]{EntityEquipmentSlot.LEGS});
        this.setRegistryName("ascentboost");
        this.setName("ascentboost");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinEnchantability(int level)
    {
        return 20 + 10 * (level - 1);
    }

    public int getMaxEnchantability(int level)
    {
        return this.getMinEnchantability(level) + 20;
    }
}