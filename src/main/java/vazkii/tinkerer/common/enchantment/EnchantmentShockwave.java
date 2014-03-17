package vazkii.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentShockwave extends EnchantmentMod {
	public EnchantmentShockwave(int par1) {
		super(par1, 5, EnumEnchantmentType.armor_feet);
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return super.canApplyTogether(par1Enchantment);
	}
}
