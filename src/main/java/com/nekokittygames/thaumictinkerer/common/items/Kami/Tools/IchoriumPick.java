package com.nekokittygames.thaumictinkerer.common.items.Kami.Tools;


import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

import static com.nekokittygames.thaumictinkerer.common.items.ModItems.RegistrationHandler.MATERIAL_ICHORIUM;

public class IchoriumPick extends ItemPickaxe  {
    public IchoriumPick(String name) {

        super(MATERIAL_ICHORIUM);
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