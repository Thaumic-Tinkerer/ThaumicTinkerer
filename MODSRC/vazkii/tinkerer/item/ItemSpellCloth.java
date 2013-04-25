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
 * File Created @ [25 Apr 2013, 14:28:21 (GMT)]
 */
package vazkii.tinkerer.item;

import java.awt.Color;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import vazkii.tinkerer.lib.LibFeatures;

public class ItemSpellCloth extends ItemMod {

	public ItemSpellCloth(int par1) {
		super(par1);
		setMaxDamage(LibFeatures.SPELL_CLOTH_USES);
		setMaxStackSize(1);

		CraftingManager.getInstance().getRecipeList().add(new SpellClothRecipe(this));
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return Color.HSBtoRGB(0.75F, ((float) par1ItemStack.getMaxDamage() - (float) par1ItemStack.getItemDamage()) / par1ItemStack.getMaxDamage() * 0.5F, 1F);
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public ItemStack getContainerItemStack(ItemStack itemStack) {
		ItemStack copyStack = itemStack.copy();
		copyStack.setItemDamage(copyStack.getItemDamage() + 1);
		if(copyStack.getItemDamage() >= copyStack.getMaxDamage())
			return null;

		return copyStack;
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public boolean hasEffect(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() == 0;
	}

}
