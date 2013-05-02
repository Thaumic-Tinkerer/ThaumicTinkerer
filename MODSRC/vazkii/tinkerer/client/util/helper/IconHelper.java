/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [24 Apr 2013, 22:58:41 (GMT)]
 */
package vazkii.tinkerer.client.util.helper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibResources;

public final class IconHelper {

	private static Icon emptyTexture;
	
	public static Icon forName(IconRegister ir, String name) {
		return ir.registerIcon(LibMisc.MOD_ID + ":" + name);
	}

	public static Icon forBlock(IconRegister ir, Block block) {
		return forName(ir, block.getUnlocalizedName().replaceAll("tile.", ""));
	}

	public static Icon forBlock(IconRegister ir, Block block, int i) {
		return forName(ir, block.getUnlocalizedName().replaceAll("tile.", "") + i);
	}
	
	public static Icon emptyTexture(IconRegister ir) {
		return emptyTexture == null ? (emptyTexture = forName(ir, LibResources.EMTPY_TEXTURE)) : emptyTexture;
	}

	public static Icon forItem(IconRegister ir, Item item) {
		return forName(ir, item.getUnlocalizedName().replaceAll("item.", ""));
	}

	public static Icon forItem(IconRegister ir, Item item, int i) {
		return forName(ir, item.getUnlocalizedName().replaceAll("item.", "") + i);
	}
}
