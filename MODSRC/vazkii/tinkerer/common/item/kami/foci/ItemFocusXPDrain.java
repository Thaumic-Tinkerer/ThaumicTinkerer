package vazkii.tinkerer.common.item.kami.foci;

import java.awt.Color;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.common.item.foci.ItemModFocus;

public class ItemFocusXPDrain extends ItemModFocus {

	AspectList cost = new AspectList();
	final int xpUse = 10;
	
	public ItemFocusXPDrain(int par1) {
		super(par1);
	}
	
	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return getFocusColor();
	}

	@Override
	public int getFocusColor() {
		EntityPlayer player = ClientHelper.clientPlayer();
		return Color.HSBtoRGB((player.ticksExisted * 2 % 360) / 360F, 1F, 1F);
	}
	
	@Override
	protected void addVisCostTooltip(AspectList cost, ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(String.format(StatCollector.translateToLocal("ttmisc.focusXPDrain.tooltip"), xpUse));
	}

	@Override
	public AspectList getVisCost() {
		return cost;
	}
	
	@Override
	public boolean acceptsEnchant(int paramInt) {
		return paramInt == Config.enchFrugal.effectId;
	}

}
