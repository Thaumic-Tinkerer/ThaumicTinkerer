package vazkii.tinkerer.common.item;

import net.minecraft.item.ItemStack;
import thaumcraft.common.items.ItemInkwell;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInfusedInkwell extends ItemInkwell {

	public ItemInfusedInkwell(int par1) {
		super(par1);
		setCreativeTab(ModCreativeTab.INSTANCE);
		setMaxDamage(800);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
		return true;
	}

}
