package vazkii.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentDispersedStrikes extends EnchantmentMod{
	public EnchantmentDispersedStrikes(int par1) {
		super(par1, 5, EnumEnchantmentType.weapon);
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment.effectId==Enchantment.knockback.effectId || par1Enchantment.effectId == Enchantment.looting.effectId;
	}
}
