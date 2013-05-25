/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [25 May 2013, 11:21:12 (GMT)]
 */
package vazkii.tinkerer.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentLost extends Enchantment {

	protected EnchantmentLost(int par1, EnumEnchantmentType par3EnumEnchantmentType) {
		super(par1, 0, par3EnumEnchantmentType);
	}

	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return !(par1Enchantment instanceof EnchantmentLost);
	}
}
