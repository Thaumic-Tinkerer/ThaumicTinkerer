package thaumic.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentPounce extends EnchantmentMod {
    public EnchantmentPounce(int par1) {
        super(par1, 5, EnumEnchantmentType.armor_legs);
    }

    @Override
    public boolean canApplyTogether(Enchantment par1Enchantment) {
        return super.canApplyTogether(par1Enchantment);
    }
}
