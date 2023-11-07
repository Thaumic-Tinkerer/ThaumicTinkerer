package com.nekokittygames.thaumictinkerer.common.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentFinalStrike extends Enchantment {
    public EnchantmentFinalStrike(int id) {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("finalstrike");
        this.setName("finalstrike");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return ench == Enchantments.MENDING || ench == Enchantments.UNBREAKING || ench == Enchantments.SHARPNESS || ench == Enchantments.SMITE || ench == Enchantments.BANE_OF_ARTHROPODS;
    }

    @Override
    public int getMaxLevel() {
        return 5;}

    @Override
    public int getMinEnchantability(int ench)
    {
        return 10 + (ench - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int ench)
    {
        return this.getMinEnchantability(ench) + 40;
    }
}