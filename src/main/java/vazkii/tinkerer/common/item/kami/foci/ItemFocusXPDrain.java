package vazkii.tinkerer.common.item.kami.foci;

import java.awt.Color;
import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.client.core.proxy.TTClientProxy;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.helper.ExperienceHelper;
import vazkii.tinkerer.common.item.foci.ItemModFocus;

public class ItemFocusXPDrain extends ItemModFocus {

	AspectList cost = new AspectList();
	private int lastGiven = 0;

	@Override
	public boolean isVisCostPerTick() {
		return true;
	}

	@Override
	public void onUsingFocusTick(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt) {
		if(paramEntityPlayer.worldObj.isRemote)
			return;

		ItemWandCasting wand = (ItemWandCasting) paramItemStack.getItem();
		AspectList aspects = wand.getAllVis(paramItemStack);

		Aspect aspectToAdd = null;
		int takes = 0;

		while(aspectToAdd == null && takes < 7) {
			lastGiven = lastGiven == 5 ? 0 : lastGiven + 1;

			Aspect aspect = Aspect.getPrimalAspects().get(lastGiven);

			if(aspects.getAmount(aspect) < wand.getMaxVis(paramItemStack))
				aspectToAdd = aspect;

			++takes;
		}

		if(aspectToAdd != null) {
			int xpUse = getXpUse(paramItemStack);
			if(paramEntityPlayer.experienceTotal >= xpUse) {
				ExperienceHelper.drainPlayerXP(paramEntityPlayer, xpUse);
				wand.storeVis(paramItemStack, aspectToAdd, Math.min(wand.getMaxVis(paramItemStack), wand.getVis(paramItemStack, aspectToAdd) + 500));
			}
		}
	}

	@Override
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return getFocusColor();
	}

	@Override
	public int getFocusColor() {
		EntityPlayer player = ThaumicTinkerer.proxy.getClientPlayer();
		return player == null ? 0xFFFFFF : Color.HSBtoRGB(player.ticksExisted * 2 % 360 / 360F, 1F, 1F);
	}

	int getXpUse(ItemStack stack) {
		return 30 - EnchantmentHelper.getEnchantmentLevel(Config.enchFrugal.effectId, stack) * 3;
	}

	@Override
	protected void addVisCostTooltip(AspectList cost, ItemStack stack, EntityPlayer player, List list, boolean par4) {
		list.add(" " + EnumChatFormatting.GREEN + StatCollector.translateToLocal("ttmisc.experience") +  EnumChatFormatting.WHITE + " x " +  getXpUse(stack));
	}

	@Override
	public AspectList getVisCost() {
		return cost;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return TTClientProxy.kamiRarity;
	}

	@Override
	public boolean acceptsEnchant(int paramInt) {
		return paramInt == Config.enchFrugal.effectId;
	}

	@Override
	public String getSortingHelper(ItemStack paramItemStack) {
		return "XPDRAIN";
	}

}
