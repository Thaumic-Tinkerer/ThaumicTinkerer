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
 * File Created @ [28 Jul 2013, 14:17:10 (GMT)]
 */
package vazkii.tinkerer.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.util.helper.MiscHelper;

public class EnchantmentStoneSkin extends EnchantmentTinker {

	protected EnchantmentStoneSkin(int par1) {
		super(par1, EnumEnchantmentType.armor);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public int calcModifierDamage(int par1, DamageSource par2DamageSource) {
		if(par2DamageSource.canHarmInCreative()) {
			return 0;
		}
		else {
			float base = (6 + par1 * par1) / 3.0F * 0.75F;

			if(AuraManager.decreaseClosestAura(MiscHelper.getMc().renderViewEntity.worldObj, MiscHelper.getClientPlayer().posX, MiscHelper.getClientPlayer().posY, MiscHelper.getClientPlayer().posY, par1)) {
				System.out.println("used vis");
				return MathHelper.floor_float(base * 2);
			}
			else {
				System.out.println("didn't used vis");
				return MathHelper.floor_float(base);
			}
		}
	}
}
