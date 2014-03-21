package vazkii.tinkerer.common.item;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.IScribeTools;
import thaumcraft.common.config.ConfigItems;

public class ItemInfusedInkwell extends ItemMod implements IScribeTools {

	public ItemInfusedInkwell() {
		super();

		setMaxDamage(800);
		maxStackSize = 1;
		canRepair = true;
		setHasSubtypes(false);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return ConfigItems.itemInkwell.getIconFromDamage(par1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

}
