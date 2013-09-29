/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [29 Sep 2013, 15:16:03 (GMT)]
 */
package vazkii.tinkerer.common.enchantment;

import thaumcraft.common.config.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentAutoSmelt extends EnchantmentMod {

	protected EnchantmentAutoSmelt(int par1) {
		super(par1, 1, EnumEnchantmentType.digger);
	}
	
	@Override
	public boolean canApplyTogether(Enchantment par1Enchantment) {
		return par1Enchantment.effectId == Enchantment.unbreaking.effectId || par1Enchantment.effectId == Config.enchRepair.effectId;
	}

}
