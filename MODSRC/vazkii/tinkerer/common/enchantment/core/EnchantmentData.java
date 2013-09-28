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
 * File Created @ [14 Sep 2013, 15:08:23 (GMT)]
 */
package vazkii.tinkerer.common.enchantment.core;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;

public class EnchantmentData {

	public final ResourceLocation texture;
	public final AspectList aspects;
	public final boolean vanilla;

	public EnchantmentData(String texture, boolean vanilla, AspectList aspects) {
		this.texture = new ResourceLocation(texture);
		this.vanilla = vanilla;
		this.aspects = aspects;
	}

}
