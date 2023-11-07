package com.nekokittygames.thaumictinkerer.common.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentVampirsim extends Enchantment {
    public EnchantmentVampirsim(int id) {
        super(Rarity.RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("vamprisim");
        this.setName("vampirsim");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public boolean canApplyTogether(Enchantment ench) {
        return ench != Enchantments.FIRE_ASPECT && ench != Enchantments.KNOCKBACK && ench != this;
    }

    @Override
    public int getMinEnchantability(int ench)
    {
        return  20 + 15 * (ench - 1);
    }

    @Override
    public int getMaxEnchantability(int ench)
    {
        return  this.getMinEnchantability(ench) + 40;
    }
    @Override
    public int getMaxLevel() {
        return 2;
    }
}