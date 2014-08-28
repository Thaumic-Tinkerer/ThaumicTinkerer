package thaumic.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.kami.armor.ItemGemBoots;
import thaumic.tinkerer.common.registry.TTRegistry;

public class EnchantmentShockwave extends EnchantmentMod {
	public EnchantmentShockwave(int par1) {
		super(par1, 5, EnumEnchantmentType.armor_feet);
	}

    @Override
    public boolean canApply(ItemStack p_92089_1_) {

        return (p_92089_1_.getItem() == ThaumicTinkerer.registry.getFirstItemFromClass(ItemGemBoots.class))?false:super.canApply(p_92089_1_);

    }

    @Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment.effectId != Enchantment.featherFalling.effectId && par1Enchantment.effectId != ModEnchantments.slowFall.effectId && super.canApplyTogether(par1Enchantment);
	}
}
