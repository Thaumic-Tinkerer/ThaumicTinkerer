package vazkii.tinkerer.common.item.kami;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.item.ItemMod;

public class ItemPlacementMirror extends ItemMod {

	public ItemPlacementMirror(int par1) {
		super(par1);
		setMaxStackSize(1);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

}
