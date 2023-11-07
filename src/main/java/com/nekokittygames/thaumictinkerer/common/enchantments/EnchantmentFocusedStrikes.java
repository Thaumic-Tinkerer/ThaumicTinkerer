package com.nekokittygames.thaumictinkerer.common.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentFocusedStrikes extends Enchantment {
    public EnchantmentFocusedStrikes(int id) {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("focusedstrikes");
        this.setName("focusedstrikes");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return ench == Enchantments.MENDING || ench == Enchantments.UNBREAKING && ench == Enchantments.KNOCKBACK || ench == Enchantments.LOOTING;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMinEnchantability(int ench)
    {
        return 15 + (ench - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int ench)
    {
        return this.getMinEnchantability(ench) + 40;
    }
}