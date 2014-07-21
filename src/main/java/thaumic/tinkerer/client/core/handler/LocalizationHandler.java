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
 * File Created @ [4 Sep 2013, 17:03:43 (GMT)]
 */
package thaumic.tinkerer.client.core.handler;

import cpw.mods.fml.common.registry.LanguageRegistry;
import thaumic.tinkerer.client.lib.LibResources;

public final class LocalizationHandler {

	public static void loadLocalizations() {
		for (String locale : LibResources.LANGS)
			LanguageRegistry.instance().loadLocalization(LibResources.PREFIX_LANG + locale + ".lang", locale, false);
	}

}
