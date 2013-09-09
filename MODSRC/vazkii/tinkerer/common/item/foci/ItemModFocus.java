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
 * File Created @ [9 Sep 2013, 19:22:59 (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import thaumcraft.api.wands.ItemFocusBasic;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemModFocus extends ItemFocusBasic {

	private Icon ornament;

	public ItemModFocus(int i) {
		super(i);
		setCreativeTab(ModCreativeTab.INSTANCE);
	}

	boolean hasOrnament() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icon = IconHelper.forItem(par1IconRegister, this);
		if(hasOrnament())
			ornament = IconHelper.forItem(par1IconRegister, this, "Orn");
	}

	@Override
	public Icon getOrnament() {
		return ornament;
	}

}
