package vazkii.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentFinalStrike  extends EnchantmentMod {
	public EnchantmentFinalStrike(int par1) {
		super(par1, 5, EnumEnchantmentType.weapon);
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment.effectId==Enchantment.sharpness.effectId || par1Enchantment.effectId == Enchantment.smite.effectId || par1Enchantment.effectId == Enchantment.smite.effectId;
	}
}
