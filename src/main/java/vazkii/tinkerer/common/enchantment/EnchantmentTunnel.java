package vazkii.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentTunnel extends EnchantmentMod {
	public EnchantmentTunnel(int par1) {
		super(par1, 5, EnumEnchantmentType.digger);
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment!=ModEnchantments.shatter && par1Enchantment!=Enchantment.efficiency;
	}
}
