package vazkii.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import thaumcraft.common.lib.enchantment.EnchantmentRepair;

public class EnchantmentFinalStrike  extends EnchantmentMod {
	public EnchantmentFinalStrike(int par1) {
		super(par1, 5, EnumEnchantmentType.weapon);
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment.effectId == Enchantment.unbreaking.effectId || par1Enchantment instanceof EnchantmentRepair || par1Enchantment.effectId==Enchantment.sharpness.effectId || par1Enchantment.effectId == Enchantment.smite.effectId || par1Enchantment.effectId == Enchantment.smite.effectId;
	}
}
