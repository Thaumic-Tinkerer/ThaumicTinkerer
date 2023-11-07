package com.nekokittygames.thaumictinkerer.common.items.Kami.Tools;


import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import static com.nekokittygames.thaumictinkerer.common.items.ModItems.RegistrationHandler.MATERIAL_ICHORIUM;

public class IchoriumAxe extends ItemAxe {
    public IchoriumAxe(String name) {

        super(MATERIAL_ICHORIUM,10.0F,-3.0F);
        setTranslationKey(name);
        setRegistryName(name);
        setCreativeTab(ThaumicTinkerer.getTab());

        
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    
}