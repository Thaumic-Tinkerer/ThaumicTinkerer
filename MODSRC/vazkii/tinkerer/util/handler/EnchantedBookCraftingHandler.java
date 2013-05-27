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
 * File Created @ [27 May 2013, 21:02:51 (GMT)]
 */
package vazkii.tinkerer.util.handler;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.enchantment.ModEnchantments;
import vazkii.tinkerer.item.ModItems;
import cpw.mods.fml.common.ICraftingHandler;

public class EnchantedBookCraftingHandler implements ICraftingHandler {

	private static Enchantment[] enchantsInBooks;

	public EnchantedBookCraftingHandler() {
		enchantsInBooks = new Enchantment[] {
				ModEnchantments.freezing,
				ModEnchantments.vampirism,
				ModEnchantments.soulbringer,
				ModEnchantments.ashes
		};
	}

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		if(item.itemID == ModItems.dummyEnchantbook.itemID) {
			Item.enchantedBook.func_92115_a(item, new EnchantmentData(enchantsInBooks[item.getItemDamage()], 1));
			item.itemID = Item.enchantedBook.itemID;
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) { }
}
