package vazkii.tinkerer.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.IScribeTools;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.registry.ItemBase;
import vazkii.tinkerer.common.research.IRegisterableResearch;

public class ItemInfusedInkwell extends ItemBase implements IScribeTools {

	public ItemInfusedInkwell() {
		super();

		setMaxDamage(800);
		maxStackSize = 1;
		canRepair = true;
		setHasSubtypes(false);

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
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return ConfigItems.itemInkwell.getIconFromDamage(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

	@Override
	public String getItemName() {
		return LibItemNames.INFUSED_INKWELL;
	}
}
