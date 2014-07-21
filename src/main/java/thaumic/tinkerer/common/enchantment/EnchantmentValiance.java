package thaumic.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import thaumcraft.api.ThaumcraftApi;

public class EnchantmentValiance extends EnchantmentMod {
	public EnchantmentValiance(int par1) {
		super(par1, 5, EnumEnchantmentType.weapon);
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment.effectId == Enchantment.unbreaking.effectId || par1Enchantment.effectId == ThaumcraftApi.enchantRepair || par1Enchantment.effectId == Enchantment.sharpness.effectId || par1Enchantment.effectId == Enchantment.smite.effectId || par1Enchantment.effectId == Enchantment.smite.effectId;
	}
}
