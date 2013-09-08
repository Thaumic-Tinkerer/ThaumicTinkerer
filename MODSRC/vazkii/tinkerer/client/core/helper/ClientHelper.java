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
 * File Created @ [8 Sep 2013, 18:11:25 (GMT)]
 */
package vazkii.tinkerer.client.core.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public final class ClientHelper {

	public static Minecraft minecraft() {
		return Minecraft.getMinecraft();
	}
	
	public static FontRenderer fontRenderer() {
		return minecraft().fontRenderer;
	}
	
}
