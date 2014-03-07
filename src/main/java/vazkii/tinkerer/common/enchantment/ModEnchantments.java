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
	public static Enchantment dispersedStrikes;
	public static Enchantment filtration;
	public static Enchantment finalStrike;
	public static Enchantment focusedStrike;
	public static Enchantment imbued;
	public static Enchantment pounce;
	public static Enchantment resolute;
	public static Enchantment shatter;
	public static Enchantment shockwave;
	public static Enchantment tunnel;
	public static Enchantment valiance;

	public static void initEnchantments() {
		ascentBoost = new EnchantmentAscentBoost(LibEnchantIDs.idAscentBoost).setName(LibEnchantNames.ASCENT_BOOST);
		slowFall = new EnchantmentSlowFall(LibEnchantIDs.idSlowFall).setName(LibEnchantNames.SLOW_FALL);
		autoSmelt = new EnchantmentAutoSmelt(LibEnchantIDs.idAutoSmelt).setName(LibEnchantNames.AUTO_SMELT);
		desintegrate = new EnchantmentDesintegrate(LibEnchantIDs.idDesintegrate).setName(LibEnchantNames.DESINTEGRATE);
		quickDraw = new EnchantmentQuickDraw(LibEnchantIDs.idQuickDraw).setName(LibEnchantNames.QUICK_DRAW);
		vampirism = new EnchantmentVampirism(LibEnchantIDs.idVampirism).setName(LibEnchantNames.VAMPIRISM);

		dispersedStrikes = new EnchantmentDispersedStrikes(LibEnchantIDs.dispersedStrikes).setName(LibEnchantNames.dispersedStrikes);

		filtration = new EnchantmentFiltration(LibEnchantIDs.filtration).setName(LibEnchantNames.filtration);

		finalStrike = new EnchantmentFinalStrike(LibEnchantIDs.finalStrike).setName(LibEnchantNames.finalStrike);

		focusedStrike = new EnchantmentFocusedStrikes(LibEnchantIDs.focusedStrike).setName(LibEnchantNames.focusedStrike);

		imbued = new EnchantmentImbued(LibEnchantIDs.imbued).setName(LibEnchantNames.imbued);

		pounce = new EnchantmentPounce(LibEnchantIDs.pounce).setName(LibEnchantNames.pounce);

		resolute = new EnchantmentResolute(LibEnchantIDs.resolute).setName(LibEnchantNames.resolute);

		shatter = new EnchantmentShatter(LibEnchantIDs.shatter).setName(LibEnchantNames.shatter);

		shockwave = new EnchantmentShockwave(LibEnchantIDs.shockwave).setName(LibEnchantNames.shockwave);

		tunnel = new EnchantmentTunnel(LibEnchantIDs.tunnel).setName(LibEnchantNames.tunnel);

		valiance = new EnchantmentValiance(LibEnchantIDs.valiance).setName(LibEnchantNames.valiance);

		vampirism = new EnchantmentVampirism(LibEnchantIDs.idVampirism).setName(LibEnchantNames.VAMPIRISM);
		

		MinecraftForge.EVENT_BUS.register(new ModEnchantmentHandler());
	}

}
