package com.nekokittygames.thaumictinkerer.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentDesIntegrate extends Enchantment {
    public EnchantmentDesIntegrate(int id) {
        super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("desintegrate");
        this.setName("desintegrate");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return ench != TTEnchantments.tunnel && ench != Enchantments.EFFICIENCY && ench != this;
    }

    @Override
    public int getMinEnchantability(int level)
    {
        return 25;
    }

    @Override
    public int getMaxEnchantability(int level)
    {
        return 55;
    }
}