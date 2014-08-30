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
package thaumic.tinkerer.common.item.kami.armor;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import thaumcraft.api.IGoggles;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.IRevealer;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.item.ItemCleansingTalisman;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

public class ItemGemHelm extends ItemIchorclothArmorAdv implements IGoggles,
		IRevealer {

	public ItemGemHelm() {
		super(0);
	}

	@Override
	public boolean showNodes(ItemStack paramItemStack,
			EntityLivingBase paramEntityLivingBase) {
		return true;
	}

	@Override
	public boolean showIngamePopups(ItemStack paramItemStack,
			EntityLivingBase paramEntityLivingBase) {
		return true;
	}

	@Override
	boolean ticks() {
		return true;
	}

	@Override
	void tickPlayer(EntityPlayer player) {
		ItemStack armor = player.getCurrentArmor(3);
		if (player.isInsideOfMaterial(Material.water)
				&& ThaumicTinkerer.proxy.armorStatus(player)
				&& armor.getItemDamage() == 0) {
			player.setAir(300);
			PotionEffect effect = player
					.getActivePotionEffect(Potion.nightVision);

			if (effect != null && effect.duration <= 202)
				effect.duration = 202;
			else
				player.addPotionEffect(new PotionEffect(Potion.nightVision.id,
						202, 0, true));
		}
		if (player.isInsideOfMaterial(Material.lava)
				&& ThaumicTinkerer.proxy.armorStatus(player)
				&& armor.getItemDamage() == 0) {
			player.setAir(300);
			PotionEffect effect = player
					.getActivePotionEffect(Potion.blindness);

			if (effect != null && effect.duration <= 202)
				effect.duration = 202;
			else
				player.addPotionEffect(new PotionEffect(Potion.blindness.id,
						202, 0, true));
		}
		int food = player.getFoodStats().getFoodLevel();
		if (food > 0 && food < 18 && player.shouldHeal()
				&& player.ticksExisted % 80 == 0)
			player.heal(1F);
	}

	@Override
	public String getItemName() {
		return LibItemNames.ICHOR_HELM_GEM;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return (IRegisterableResearch) new KamiResearchItem(
				LibResearch.KEY_ICHORCLOTH_HELM_GEM, new AspectList()
						.add(Aspect.WATER, 2).add(Aspect.HEAL, 1)
						.add(Aspect.HUNGER, 1).add(Aspect.AURA, 1), 18, 3, 5,
				new ItemStack(this)).setParents(
				LibResearch.KEY_ICHORCLOTH_ARMOR).setPages(
				new ResearchPage("0"),
				ResearchHelper
						.infusionPage(LibResearch.KEY_ICHORCLOTH_HELM_GEM));

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererInfusionRecipe(
				LibResearch.KEY_ICHORCLOTH_HELM_GEM, new ItemStack(this), 13,
				new AspectList().add(Aspect.WATER, 50).add(Aspect.ARMOR, 32)
						.add(Aspect.HUNGER, 32).add(Aspect.AURA, 32)
						.add(Aspect.LIGHT, 64).add(Aspect.FLESH, 16)
						.add(Aspect.MIND, 16), new ItemStack(ThaumicTinkerer.registry.getItemFromClassAndName(ItemIchorclothArmor.class, LibItemNames.ICHOR_HELM)),
				new ItemStack(Items.diamond, 1),
				new ItemStack(ThaumicTinkerer.registry
						.getFirstItemFromClass(ItemKamiResource.class)),
				new ItemStack(ThaumicTinkerer.registry
						.getFirstItemFromClass(ItemKamiResource.class)),
                new ItemStack(ConfigItems.itemThaumonomicon), new ItemStack(
						ConfigItems.itemFocusPrimal), new ItemStack(
						Items.golden_helmet), new ItemStack(Items.potionitem,
						1, 8198), new ItemStack(ConfigItems.itemGoggles),
                new ItemStack(Items.ghast_tear),
                new ItemStack(Items.fish), new ItemStack(Items.cake),
				new ItemStack(Items.ender_eye));
	}

}
