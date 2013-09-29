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
 * File Created @ [14 Sep 2013, 15:05:23 (GMT)]
 */
package vazkii.tinkerer.common.enchantment.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import thaumcraft.common.lib.research.ResearchManager;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.core.helper.MiscHelper;
import vazkii.tinkerer.common.enchantment.ModEnchantments;
import vazkii.tinkerer.common.enchantment.core.rule.BasicCompatibilityRule;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public final class EnchantmentManager {

	public static final Map<Integer, Map<Integer, EnchantmentData>> enchantmentData = new HashMap();

	public static final Multimap<Integer, IEnchantmentRule> rules = ArrayListMultimap.create();

	public static void initEnchantmentData() {
		registerExponentialCostData(Enchantment.protection, LibResources.ENCHANT_PROTECTION, true, new AspectList().add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 7));
		registerExponentialCostData(Enchantment.fireProtection, LibResources.ENCHANT_FIRE_PROTECTION, true, new AspectList().add(Aspect.FIRE, 10).add(Aspect.ENTROPY, 3).add(Aspect.WATER, 4));
		registerExponentialCostData(Enchantment.featherFalling, LibResources.ENCHANT_FEATHER_FALLING, true, new AspectList().add(Aspect.AIR, 16).add(Aspect.ORDER, 5));
		registerExponentialCostData(Enchantment.blastProtection, LibResources.ENCHANT_BLAST_PROTECTION, true, new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 8));
		registerExponentialCostData(Enchantment.projectileProtection, LibResources.ENCHANT_PROJECTILE_PROTECTION, true, new AspectList().add(Aspect.AIR, 10).add(Aspect.ENTROPY, 7));
		registerExponentialCostData(Enchantment.respiration, LibResources.ENCHANT_RESPIRATION, true, new AspectList().add(Aspect.WATER, 10).add(Aspect.AIR, 8).add(Aspect.ORDER, 5));
		registerExponentialCostData(Enchantment.aquaAffinity, LibResources.ENCHANT_AQUA_AFFINITY, true, new AspectList().add(Aspect.WATER, 25).add(Aspect.ORDER, 20).add(Aspect.EARTH, 5));
		registerExponentialCostData(Enchantment.thorns, LibResources.ENCHANT_THORNS, true, new AspectList().add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 12));
		registerExponentialCostData(Enchantment.sharpness, LibResources.ENCHANT_SHARPNESS, true, new AspectList().add(Aspect.ORDER, 10));
		registerExponentialCostData(Enchantment.smite, LibResources.ENCHANT_SMITE, true, new AspectList().add(Aspect.ORDER, 5).add(Aspect.AIR, 5));
		registerExponentialCostData(Enchantment.baneOfArthropods, LibResources.ENCHANT_BANE_OF_ARTHROPODS, true, new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5));
		registerExponentialCostData(Enchantment.knockback, LibResources.ENCHANT_KNOCKBACK, true, new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.AIR, 10));
		registerExponentialCostData(Enchantment.fireAspect, LibResources.ENCHANT_FIRE_ASPECT, true, new AspectList().add(Aspect.FIRE, 15).add(Aspect.EARTH, 4));
		registerExponentialCostData(Enchantment.looting, LibResources.ENCHANT_LOOTING, true, new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15));
		registerExponentialCostData(Enchantment.efficiency, LibResources.ENCHANT_EFFICIENCY, true, new AspectList().add(Aspect.ENTROPY, 12).add(Aspect.EARTH, 4));
		registerExponentialCostData(Enchantment.silkTouch, LibResources.ENCHANT_SILK_TOUCH, true, new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 10));
		registerExponentialCostData(Enchantment.unbreaking, LibResources.ENCHANT_UNBREAKING, true, new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 8).add(Aspect.EARTH, 8));
		registerExponentialCostData(Enchantment.fortune, LibResources.ENCHANT_FORTUNE, true, new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15));
		registerExponentialCostData(Enchantment.power, LibResources.ENCHANT_POWER, true, new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 10));
		registerExponentialCostData(Enchantment.punch, LibResources.ENCHANT_PUNCH, true, new AspectList().add(Aspect.AIR, 4).add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 5));
		registerExponentialCostData(Enchantment.flame, LibResources.ENCHANT_FLAME, true, new AspectList().add(Aspect.ENTROPY, 5).add(Aspect.FIRE, 20).add(Aspect.EARTH, 5));
		registerExponentialCostData(Enchantment.infinity, LibResources.ENCHANT_INFINITY, true, new AspectList().add(Aspect.ENTROPY, 40).add(Aspect.ORDER, 40).add(Aspect.EARTH, 10));
		registerExponentialCostData(Config.enchPotency, LibResources.ENCHANT_POTENCY, true, new AspectList().add(Aspect.ORDER, 15));
		registerExponentialCostData(Config.enchFrugal, LibResources.ENCHANT_FRUGAL, true, new AspectList().add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 10));
		registerExponentialCostData(Config.enchWandFortune, LibResources.ENCHANT_TREASURE, true, new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.EARTH, 10).add(Aspect.ORDER, 15).add(Aspect.ENTROPY, 15));
		registerExponentialCostData(Config.enchHaste, LibResources.ENCHANT_HASTE, true, new AspectList().add(Aspect.AIR, 10).add(Aspect.ENTROPY, 5).add(Aspect.EARTH, 5));
		registerExponentialCostData(Config.enchRepair, LibResources.ENCHANT_REPAIR, true, new AspectList().add(Aspect.WATER, 20).add(Aspect.FIRE, 20).add(Aspect.EARTH, 20).add(Aspect.AIR, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 5));

		registerExponentialCostData(ModEnchantments.ascentBoost, LibResources.ENCHANT_ASCENT_BOOST, false, new AspectList().add(Aspect.ENTROPY, 8).add(Aspect.AIR, 10));
		registerExponentialCostData(ModEnchantments.slowFall, LibResources.ENCHANT_SLOW_FALL, false, new AspectList().add(Aspect.ORDER, 8).add(Aspect.AIR, 10));

		
		registerCompatibilityRules();
		registerExtraRules();
	}

	public static boolean canApply(ItemStack stack, Enchantment enchant, List<Integer> currentEnchants) {
		if(!enchant.canApply(stack) || !enchant.type.canEnchantItem(stack.getItem()) || currentEnchants.contains(enchant.effectId))
			return false;

		for(IEnchantmentRule rule : rules.get(enchant.effectId))
			if(!rule.canApplyAlongside(currentEnchants))
				return false;

		return true;
	}
	
	public static boolean canEnchantmentBeUsed(String player, Enchantment enchant) {
		if(!enchantmentData.containsKey(enchant))
			return false;
		
		EnchantmentData data = enchantmentData.get(enchant).get(0);
		return data.research.isEmpty() || ResearchManager.isResearchComplete(player, data.research);
	}
	
	private static void registerExponentialCostData(Enchantment enchantment, String texture, boolean vanilla, AspectList level1Aspects) {
		registerExponentialCostData(enchantment, texture, vanilla, level1Aspects, "");
	}
		
	private static void registerExponentialCostData(Enchantment enchantment, String texture, boolean vanilla, AspectList level1Aspects, String research) {
		for(double i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
			EnchantmentData data = new EnchantmentData(texture, vanilla, MiscHelper.multiplyAspectList(level1Aspects, i * (1D + i * 0.2)), research);
			registerData(enchantment.effectId, (int) i, data);
		}
	}

	private static void registerData(int enchantment, int level, EnchantmentData data) {
		if(!enchantmentData.containsKey(enchantment))
			enchantmentData.put(enchantment, new HashMap());

		enchantmentData.get(enchantment).put(level, data);
	}

	private static void registerCompatibilityRules() {
		for(Enchantment ench : Enchantment.enchantmentsList) {
			if(ench != null)
				for(Enchantment ench1 : Enchantment.enchantmentsList) {
					if(ench1 == null || ench == ench1)
						continue;

					if(!ench.canApplyTogether(ench1) || !ench1.canApplyTogether(ench))
						rules.put(ench.effectId, new BasicCompatibilityRule(ench1));
				}
		}

	}

	private static void registerExtraRules() {

	}
}
