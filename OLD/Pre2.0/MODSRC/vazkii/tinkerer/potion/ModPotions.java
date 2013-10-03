/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [25 Apr 2013, 22:02:51 (GMT)]
 */
package vazkii.tinkerer.potion;

import net.minecraft.potion.Potion;
import vazkii.tinkerer.lib.LibPotions;

public final class ModPotions {

	public static Potion effectStopwatch;
	public static Potion effectFrozen;
	public static Potion effectPossessed;

	public static void initPotions() {
		effectStopwatch = new TTPotion(LibPotions.idStopwatch, false, 0x999900, 0, 0).setPotionName(LibPotions.DISPLAY_NAME_STOPWATCH);
		effectFrozen = new TTPotion(LibPotions.idFrozen, false, 0x8BCFFB, 1, 0).setPotionName(LibPotions.DISPLAY_NAME_FROZEN);
		effectPossessed = new TTPotion(LibPotions.idPossessed, false, 0x13001A, 2, 0).setPotionName(LibPotions.DISPLAY_NAME_POSSESSED);
	}
}
