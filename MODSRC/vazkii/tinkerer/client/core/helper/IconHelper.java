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
 * File Created @ [8 Sep 2013, 15:47:08 (GMT)]
 */
package vazkii.tinkerer.client.core.helper;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import vazkii.tinkerer.client.core.lib.LibResources;

public class IconHelper {

	private static Icon emptyTexture;
	
	public static Icon forName(IconRegister ir, String name) {
		return ir.registerIcon(LibResources.PREFIX_MOD + name);
	}

	public static Icon forNameRaw(IconRegister ir, String name) {
		return ir.registerIcon(name);
	}
	
	public static Icon emptyTexture(IconRegister ir) {
		return emptyTexture == null ? (emptyTexture = forName(ir, LibResources.EMTPY_TEXTURE)) : emptyTexture;
	}

	public static Icon forBlock(IconRegister ir, Block block) {
		return forNameRaw(ir, block.getUnlocalizedName().replaceAll("tile.", ""));
	}

	public static Icon forBlock(IconRegister ir, Block block, int i) {
		return forNameRaw(ir, block.getUnlocalizedName().replaceAll("tile.", "") + i);
	}

	public static Icon forItem(IconRegister ir, Item item) {
		return forNameRaw(ir, item.getUnlocalizedName().replaceAll("item.", ""));
	}

	public static Icon forItem(IconRegister ir, Item item, int i) {
		return forNameRaw(ir, item.getUnlocalizedName().replaceAll("item.", "") + i);
	}

}
