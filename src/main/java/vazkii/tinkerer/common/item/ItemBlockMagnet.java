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
 * File Created @ [12 Sep 2013, 18:10:44 (GMT)]
 */
package vazkii.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.registry.ITTinkererItem;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;

public class ItemBlockMagnet extends ItemBlock implements ITTinkererItem {

	public ItemBlockMagnet(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int par1) {
		return par1 == 0 ? 0 : 2;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "tile." + (par1ItemStack.getItemDamage() == 0 ? LibBlockNames.MAGNET : LibBlockNames.MOB_MAGNET);
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getItemName() {
		return LibBlockNames.MAGNET;
	}

	@Override
	public boolean shouldRegister() {
		return false;
	}

	@Override
	public boolean shouldDisplayInTab() {
		return true;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return null;
	}
}
