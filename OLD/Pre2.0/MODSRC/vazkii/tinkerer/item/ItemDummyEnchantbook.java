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
 * File Created @ [27 May 2013, 21:05:58 (GMT)]
 */
package vazkii.tinkerer.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import thaumcraft.client.gui.GuiInfusionWorkbench;
import thaumcraft.client.gui.GuiResearchRecipe;
import vazkii.tinkerer.util.helper.MiscHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDummyEnchantbook extends ItemEnchantedBook {

	public ItemDummyEnchantbook(int par1) {
		super(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(MiscHelper.getMc().currentScreen != null && MiscHelper.getMc().currentScreen instanceof GuiResearchRecipe)
			return;

		if(MiscHelper.getMc().currentScreen != null && !(MiscHelper.getMc().currentScreen instanceof GuiInfusionWorkbench)) {
			par3List.add("A dummy book for enchanting. It's an internal item");
			par3List.add("and is of no use to the player.");
		} else par3List.add("This book will be enchanted upon creation.");
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.uncommon;
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return Item.enchantedBook.getItemDisplayName(par1ItemStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return Item.enchantedBook.getIconFromDamage(par1);
	}
}
