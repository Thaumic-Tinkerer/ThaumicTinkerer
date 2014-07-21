package thaumic.tinkerer.common.item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.block.fire.*;
import thaumic.tinkerer.common.registry.ITTinkererBlock;
import thaumic.tinkerer.common.registry.ITTinkererItem;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;

import java.util.ArrayList;

public class ItemBlockFire extends ItemBlock implements ITTinkererItem {

	public ItemBlockFire(Block block) {
		super(block);
	}

	@Override
	public int getMetadata(int par1) {
		return 0;
	}

	@Override
	public ArrayList<Object> getSpecialParameters() {
		return null;
	}

	@Override
	public String getItemName() {
		return ((ITTinkererBlock) field_150939_a).getBlockName();
	}

	@Override
	public boolean shouldRegister() {
		return true;
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

	private IIcon icon;

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		String s = "";
		if (field_150939_a instanceof BlockFireAir) {
			s = "aer";
		}
		if (field_150939_a instanceof BlockFireEarth) {
			s = "terra";
		}
		if (field_150939_a instanceof BlockFireWater) {
			s = "aqua";
		}
		if (field_150939_a instanceof BlockFireIgnis) {
			s = "ignis";
		}
		if (field_150939_a instanceof BlockFireOrder) {
			s = "ordo";
		}
		if (field_150939_a instanceof BlockFireChaos) {
			s = "perditio";
		}
		s += "Fire";
		icon = IconHelper.forName(par1IconRegister, s);
	}
}
