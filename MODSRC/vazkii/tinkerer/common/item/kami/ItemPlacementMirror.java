package vazkii.tinkerer.common.item.kami;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import vazkii.tinkerer.client.core.helper.IconHelper;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.item.ItemMod;

public class ItemPlacementMirror extends ItemMod {

	Icon[] icons = new Icon[2];
	
	public ItemPlacementMirror(int par1) {
		super(par1);
		setMaxStackSize(1);
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		icons[0] = IconHelper.forItem(par1IconRegister, this, 0);
		icons[1] = IconHelper.forItem(par1IconRegister, this, 1);
	}
	
	@Override
	public Icon getIconFromDamageForRenderPass(int par1, int par2) {
		return icons[par2 & 1];
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

}
