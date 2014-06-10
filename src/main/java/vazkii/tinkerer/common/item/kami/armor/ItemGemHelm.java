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
 * File Created @ [Dec 26, 2013, 12:14:11 AM (GMT)]
 */
package vazkii.tinkerer.common.item.kami.armor;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import thaumcraft.api.IGoggles;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.IRevealer;
import thaumcraft.api.research.ResearchPage;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.KamiResearchItem;
import vazkii.tinkerer.common.research.ResearchHelper;

public class ItemGemHelm extends ItemIchorclothArmorAdv implements IGoggles, IRevealer {

	public ItemGemHelm(int par2) {
		super(par2);
	}

	@Override
	public boolean showNodes(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase) {
		return true;
	}

	@Override
	public boolean showIngamePopups(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase) {
		return true;
	}

	@Override
	boolean ticks() {
		return true;
	}

	@Override
	void tickPlayer(EntityPlayer player) {
		ItemStack armor = player.getCurrentArmor(3);
		if (player.isInsideOfMaterial(Material.water) && ThaumicTinkerer.proxy.armorStatus(player) && armor.getItemDamage() == 0) {
			player.setAir(300);
			PotionEffect effect = player.getActivePotionEffect(Potion.nightVision);

			if (effect != null && effect.duration <= 202)
				effect.duration = 202;
			else player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 202, 0, true));
		}
		if (player.isInsideOfMaterial(Material.lava) && ThaumicTinkerer.proxy.armorStatus(player) && armor.getItemDamage() == 0) {
			player.setAir(300);
			PotionEffect effect = player.getActivePotionEffect(Potion.blindness);

			if (effect != null && effect.duration <= 202)
				effect.duration = 202;
			else player.addPotionEffect(new PotionEffect(Potion.blindness.id, 202, 0, true));
		}
		int food = player.getFoodStats().getFoodLevel();
		if (food > 0 && food < 18 && player.shouldHeal() && player.ticksExisted % 80 == 0)
			player.heal(1F);
	}

	@Override
	public String getItemName() {
		return LibItemNames.ICHOR_HELM_GEM;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_HELM_GEM, new AspectList().add(Aspect.WATER, 2).add(Aspect.HEAL, 1).add(Aspect.HUNGER, 1).add(Aspect.AURA, 1), 18, 3, 5, new ItemStack(this)).setParents(LibResearch.KEY_ICHORCLOTH_ARMOR)
				.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_HELM_GEM));

	}

}
