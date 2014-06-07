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
 * File Created @ [26 Oct 2013, 12:04:52 (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.ThaumicTinkerer;

import java.util.HashMap;
import java.util.Map;

public class ItemFocusHeal extends ItemModFocus {

	private static final AspectList visUsage = new AspectList().add(Aspect.EARTH, 45).add(Aspect.WATER, 45);

	public static Map<String, Integer> playerHealData = new HashMap();

	public ItemFocusHeal() {
		super();
	}

	@Override
	public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int time) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();
		if (!wand.consumeAllVis(stack, p, visUsage, false, false) || !p.shouldHeal())
			return;

		int potency = EnchantmentHelper.getEnchantmentLevel(Config.enchPotency.effectId, wand.getFocusItem(stack));

		if (!playerHealData.containsKey(p.getGameProfile().getName()))
			playerHealData.put(p.getGameProfile().getName(), 0);

		int progress = playerHealData.get(p.getGameProfile().getName()) + 1;
		playerHealData.put(p.getGameProfile().getName(), progress);

		ThaumicTinkerer.tcProxy.sparkle((float) p.posX + p.worldObj.rand.nextFloat() - 0.5F, (float) p.posY + p.worldObj.rand.nextFloat(), (float) p.posZ + p.worldObj.rand.nextFloat() - 0.5F, 0);

		if (progress >= 30 - potency * 10 / 3) {
			playerHealData.put(p.getGameProfile().getName(), 0);

			wand.consumeAllVis(stack, p, visUsage, true, false);
			p.heal(1);
			p.worldObj.playSoundAtEntity(p, "thaumcraft:wand", 0.5F, 1F);
		}
	}

	@Override
	public void onPlayerStoppedUsingFocus(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer, int paramInt) {
		playerHealData.put(paramEntityPlayer.getGameProfile().getName(), 0);
	}

	@Override
	protected boolean hasDepth() {
		return true;
	}

	@Override
	public boolean isVisCostPerTick() {
		return false;
	}

	@Override
	public boolean isUseItem() {
		return true;
	}

	@Override
	public String getSortingHelper(ItemStack paramItemStack) {
		return "HEAL";
	}

	@Override
	public int getFocusColor() {
		return 0xFD00D6;
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}

}
