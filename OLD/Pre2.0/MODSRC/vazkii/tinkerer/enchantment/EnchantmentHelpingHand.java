/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 *
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [5 Aug 2013, 20:26:49 (GMT)]
 */
package vazkii.tinkerer.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentHelpingHand extends EnchantmentTinker {

		protected EnchantmentHelpingHand(int par1) {
			super(par1, EnumEnchantmentType.armor);
		}

		@Override
		public int getMaxLevel() {
			return 1;
		}
}
