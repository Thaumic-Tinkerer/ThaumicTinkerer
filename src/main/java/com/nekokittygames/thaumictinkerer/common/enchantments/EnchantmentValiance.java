package com.nekokittygames.thaumictinkerer.common.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentValiance extends Enchantment {
    public EnchantmentValiance(int id) {
        super(Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("valiance");
        this.setName("valiance");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return ench == Enchantments.MENDING || ench == Enchantments.UNBREAKING || ench == Enchantments.SHARPNESS || ench == Enchantments.SMITE || ench == Enchantments.BANE_OF_ARTHROPODS;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    public int getMinEnchantability(int level)
    {
        return 10 + (level - 1) * 11;
    }

    public int getMaxEnchantability(int level)
    {
        return this.getMinEnchantability(level) + 40;
    }
}