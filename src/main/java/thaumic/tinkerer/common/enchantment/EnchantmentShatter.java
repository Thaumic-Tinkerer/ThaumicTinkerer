package thaumic.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentShatter extends EnchantmentMod {
	public EnchantmentShatter(int par1) {
		super(par1, 5, EnumEnchantmentType.digger);
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment != Enchantment.efficiency && par1Enchantment != ModEnchantments.desintegrate;
	}
}
