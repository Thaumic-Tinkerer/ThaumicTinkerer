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
 * File Created @ [Dec 21, 2013, 7:34:11 PM (GMT)]
 */
package vazkii.tinkerer.common.item.kami;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.item.ItemMod;
import vazkii.tinkerer.common.lib.LibItemNames;

import java.util.List;

public class ItemKamiResource extends ItemMod {

	final int subtypes = 8;
	IIcon[] icons;

	public ItemKamiResource() {
		super();
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < subtypes; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[subtypes];

		for (int i = 0; i < icons.length; i++)
			icons[i] = IconHelper.forNameRaw(par1IconRegister, LibItemNames.KAMI_RESOURCE_NAMES[i]);
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return icons[Math.min(subtypes - 1, par1)];
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() >= subtypes ? super.getUnlocalizedName(par1ItemStack) : "item." + LibItemNames.KAMI_RESOURCE_NAMES[par1ItemStack.getItemDamage()];
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() != 7 && par1ItemStack.getItemDamage() != 6 ? TTClientProxy.kamiRarity : super.getRarity(par1ItemStack);
	}
}
