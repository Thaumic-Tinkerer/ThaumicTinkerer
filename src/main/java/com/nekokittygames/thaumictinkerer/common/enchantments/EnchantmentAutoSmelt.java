package com.nekokittygames.thaumictinkerer.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentAutoSmelt extends Enchantment {
    public EnchantmentAutoSmelt(int id) {
        super(Rarity.UNCOMMON, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
        this.setRegistryName("autosmelt");
        this.setName("autosmelt");

        TTEnchantments.ENCHANTNENTS.add(this);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return ench == Enchantments.UNBREAKING && ench == Enchantments.MENDING;
    }

    @Override
    public int getMinEnchantability(int level) {
        return 25;
    }

    @Override
    public int getMaxEnchantability(int level)
    {
        return 50;
    }
}