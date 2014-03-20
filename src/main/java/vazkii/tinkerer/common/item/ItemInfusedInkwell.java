package vazkii.tinkerer.common.item;


import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import thaumcraft.common.config.ConfigItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInfusedInkwell extends ItemMod {

	public ItemInfusedInkwell() {
		super();

		setMaxDamage(800);
		maxStackSize = 1;
		canRepair = true;
		setHasSubtypes(false);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		return ConfigItems.itemInkwell.getIconFromDamage(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

}
