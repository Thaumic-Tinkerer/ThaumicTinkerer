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
 * File Created @ [4 Sep 2013, 16:57:55 (GMT)]
 */
package vazkii.tinkerer.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import vazkii.tinkerer.common.lib.LibEnchantIDs;
import vazkii.tinkerer.common.lib.LibEnchantNames;

public final class ModEnchantments {

	public static Enchantment ascentBoost;
	public static Enchantment slowFall;
	public static Enchantment autoSmelt;
	public static Enchantment desintegrate;
	public static Enchantment quickDraw;
	public static Enchantment vampirism;

	public static void initEnchantments() {
		ascentBoost = new EnchantmentAscentBoost(LibEnchantIDs.idAscentBoost).setName(LibEnchantNames.ASCENT_BOOST);
		slowFall = new EnchantmentSlowFall(LibEnchantIDs.idSlowFall).setName(LibEnchantNames.SLOW_FALL);
		autoSmelt = new EnchantmentAutoSmelt(LibEnchantIDs.idAutoSmelt).setName(LibEnchantNames.AUTO_SMELT);
		desintegrate = new EnchantmentDesintegrate(LibEnchantIDs.idDesintegrate).setName(LibEnchantNames.DESINTEGRATE);
		quickDraw = new EnchantmentQuickDraw(LibEnchantIDs.idQuickDraw).setName(LibEnchantNames.QUICK_DRAW);
		vampirism = new EnchantmentVampirism(LibEnchantIDs.idVampirism).setName(LibEnchantNames.VAMPIRISM);

		MinecraftForge.EVENT_BUS.register(new ModEnchantmentHandler());
	}

}
