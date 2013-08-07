/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 * 
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [28 Jul 2013, 14:17:10 (GMT)]
 */
package vazkii.tinkerer.enchantment;

import cpw.mods.fml.relauncher.ReflectionHelper;
import thaumcraft.common.aura.AuraManager;
import vazkii.tinkerer.lib.LibEnchantmentIDs;
import vazkii.tinkerer.util.helper.MiscHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class EnchantmentStoneSkin extends EnchantmentTinker {

	protected EnchantmentStoneSkin(int par1) {
		super(par1, EnumEnchantmentType.armor);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	public int calcModifierDamage(int par1, DamageSource par2DamageSource) {
		if(par2DamageSource.canHarmInCreative()) {
			return 0;
		}
		else {
			float base = (float)((6 + par1 * par1) / 3.0F) * 0.75F;

			if(MiscHelper.getClosestNode(par2DamageSource.getEntity().worldObj, MiscHelper.getClientPlayer().posX, MiscHelper.getClientPlayer().posY, MiscHelper.getClientPlayer().posY) != null) {
				if(AuraManager.decreaseClosestAura(par2DamageSource.getEntity().worldObj, MiscHelper.getClientPlayer().posX, MiscHelper.getClientPlayer().posY, MiscHelper.getClientPlayer().posY, par1)) {
					return MathHelper.floor_float(base * 2);
				}
				else {
					return MathHelper.floor_float(base);
				}
			}
			else {
				return MathHelper.floor_float(base);
			}
		}
	}
}
