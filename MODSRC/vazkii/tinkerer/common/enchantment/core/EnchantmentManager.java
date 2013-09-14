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
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public final class EnchantmentManager {

	public static final Map<Integer, Map<Integer, EnchantmentData>> enchantmentData = new HashMap();
	
	public static final Multimap<Integer, IEnchantmentRule> rules = ArrayListMultimap.create();
	
	public static void initEnchantmentData() {
		// TODO Add Data
		
		
		registerCompatibilityRules();
		registerExtraRules();
	}
	
	private static void registerCompatibilityRules() {
		
	}
	
	private static void registerExtraRules() {
		
	}
	
}
