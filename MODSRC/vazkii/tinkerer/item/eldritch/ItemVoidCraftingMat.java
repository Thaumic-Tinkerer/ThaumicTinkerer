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
 * File Created @ [1 Jun 2013, 17:55:27 (GMT)]
 */
package vazkii.tinkerer.item.eldritch;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import vazkii.tinkerer.client.util.helper.IconHelper;
import vazkii.tinkerer.item.ItemMod;
import vazkii.tinkerer.lib.LibItemNames;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemVoidCraftingMat extends ItemMod {

	Icon[] icons;

	public ItemVoidCraftingMat(int par1) {
		super(par1);
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < LibItemNames.VOID_CRAFTING_MATERIAL_N.length; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		icons = new Icon[LibItemNames.VOID_CRAFTING_MATERIAL_N.length];
		for(int i = 0; i < icons.length; i++)
			icons[i] = IconHelper.forName(par1IconRegister, LibItemNames.VOID_CRAFTING_MATERIAL_N[i]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return par1 >= icons.length ? icons[0] : icons[par1];
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() >= LibItemNames.VOID_CRAFTING_MATERIAL_D.length ? "Unnamed" : LibItemNames.VOID_CRAFTING_MATERIAL_D[par1ItemStack.getItemDamage()];
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() >= LibItemNames.VOID_CRAFTING_MATERIAL_N.length ? LibItemNames.VOID_CRAFTING_MATERIAL : LibItemNames.VOID_CRAFTING_MATERIAL_N[par1ItemStack.getItemDamage()];
	}

}
