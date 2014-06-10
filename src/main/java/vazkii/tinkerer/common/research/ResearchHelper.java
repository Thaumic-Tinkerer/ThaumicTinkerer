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
 * File Created @ [4 Sep 2013, 17:02:29 (GMT)]
 */
package vazkii.tinkerer.common.research;

import cpw.mods.fml.common.Loader;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionEnchantmentRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigResearch;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.lib.LibResearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ResearchHelper {

	public static ResearchItem kamiResearch;

	public static void initResearch() {
		registerResearchPages();

		ResearchItem research;

		research = new TTResearchItem(LibResearch.KEY_INTERFACE, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.ORDER, 4), -4, 2, 1, new ItemStack(ModBlocks.interfase)).setParents(LibResearch.KEY_DARK_QUARTZ);
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_INTERFACE), new ResearchPage("1"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_INTERFACE + "1"), new ResearchPage("2"));

		research = new TTResearchItem(LibResearch.KEY_GAS_REMOVER, new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.LIGHT, 2), -2, -7, 0, new ItemStack(ModItems.gasRemover)).setRound();
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_GAS_REMOVER)).setParents(LibResearch.KEY_GASEOUS_SHADOW);

		research = new TTResearchItem(LibResearch.KEY_SPELL_CLOTH, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.CLOTH, 1), 3, 2, 2, new ItemStack(ModItems.spellCloth)).setParentsHidden("ENCHFABRIC");
		research.setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_SPELL_CLOTH));

		research = new TTResearchItem(LibResearch.KEY_ANIMATION_TABLET, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.METAL, 1).add(Aspect.MOTION, 1).add(Aspect.ENERGY, 1), -8, 2, 4, new ItemStack(ModBlocks.animationTablet)).setParents(LibResearch.KEY_MAGNETS);
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ANIMATION_TABLET));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_FLIGHT, new AspectList().add(Aspect.MOTION, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR, 2), -3, -4, 2, new ItemStack(ModItems.focusFlight)).setParents(LibResearch.KEY_FOCUS_SMELT).setConcealed();
		research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_FLIGHT));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_DISLOCATION, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.MAGIC, 1).add(Aspect.EXCHANGE, 1), -5, -5, 2, new ItemStack(ModItems.focusDislocation)).setSecondary().setParents(LibResearch.KEY_FOCUS_FLIGHT).setConcealed();
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_DISLOCATION));

		research = new TTResearchItem(LibResearch.KEY_CLEANSING_TALISMAN, new AspectList().add(Aspect.HEAL, 2).add(Aspect.ORDER, 1).add(Aspect.POISON, 1), -3, 4, 3, new ItemStack(ModItems.cleansingTalisman)).setSecondary().setParents(LibResearch.KEY_DARK_QUARTZ);
		research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_CLEANSING_TALISMAN));

		research = new TTResearchItem(LibResearch.KEY_BRIGHT_NITOR, new AspectList().add(Aspect.LIGHT, 2).add(Aspect.FIRE, 1).add(Aspect.ENERGY, 1).add(Aspect.AIR, 1), 1, -5, 2, new ItemStack(ModItems.brightNitor)).setParents(LibResearch.KEY_GASEOUS_LIGHT).setConcealed();
		research.setPages(new ResearchPage("0"), ResearchHelper.crucibleRecipePage(LibResearch.KEY_BRIGHT_NITOR)).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_FOCUS_TELEKINESIS, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.MAGIC, 1).add(Aspect.MOTION, 1), -4, -6, 2, new ItemStack(ModItems.focusTelekinesis)).setParents(LibResearch.KEY_FOCUS_FLIGHT).setConcealed();
		research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_TELEKINESIS)).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_MAGNETS, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.MOTION, 1).add(Aspect.SENSES, 1), -6, 3, 3, new ItemStack(ModBlocks.magnet)).setParents(LibResearch.KEY_INTERFACE).setConcealed();
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_MAGNET), ResearchHelper.arcaneRecipePage(LibResearch.KEY_MOB_MAGNET), ResearchHelper.crucibleRecipePage(LibResearch.KEY_MAGNETS));

		research = new TTResearchItem(LibResearch.KEY_ENCHANTER, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.AURA, 1).add(Aspect.ELDRITCH, 1).add(Aspect.DARKNESS, 1).add(Aspect.MIND, 1), 5, 4, 5, new ItemStack(ModBlocks.enchanter)).setParents(LibResearch.KEY_SPELL_CLOTH);
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), new ResearchPage("2"), ResearchHelper.infusionPage(LibResearch.KEY_ENCHANTER));

		research = new TTResearchItem(LibResearch.KEY_XP_TALISMAN, new AspectList().add(Aspect.GREED, 1).add(Aspect.MAGIC, 1).add(Aspect.MAN, 1), 4, -1, 2, new ItemStack(ModItems.xpTalisman, 1, 1)).setParents("JARBRAIN", LibResearch.KEY_SPELL_CLOTH).setConcealed();
		research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_XP_TALISMAN)).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_FUNNEL, new AspectList().add(Aspect.TOOL, 1).add(Aspect.TRAVEL, 2), 0, -7, 1, new ItemStack(ModBlocks.funnel)).setParentsHidden("DISTILESSENTIA").setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed();
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_FUNNEL)).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_ASCENT_BOOST, new AspectList().add(Aspect.AIR, 1).add(Aspect.MOTION, 1).add(Aspect.MAGIC, 2), 6, 2, 2, new ResourceLocation(LibResources.ENCHANT_ASCENT_BOOST)).setParents(LibResearch.KEY_ENCHANTER);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_SLOW_FALL, new AspectList().add(Aspect.AIR, 1).add(Aspect.MOTION, 1).add(Aspect.MAGIC, 2), 7, 3, 2, new ResourceLocation(LibResources.ENCHANT_SLOW_FALL)).setParents(LibResearch.KEY_ENCHANTER);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_AUTO_SMELT, new AspectList().add(Aspect.FIRE, 1).add(Aspect.ENTROPY, 1).add(Aspect.MAGIC, 2), 7, 5, 2, new ResourceLocation(LibResources.ENCHANT_AUTO_SMELT)).setParents(LibResearch.KEY_ENCHANTER);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_DESINTEGRATE, new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.VOID, 1).add(Aspect.MAGIC, 2), 6, 6, 2, new ResourceLocation(LibResources.ENCHANT_DESINTEGRATE)).setParents(LibResearch.KEY_ENCHANTER);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_QUICK_DRAW, new AspectList().add(Aspect.SENSES, 1).add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 2), 4, 6, 2, new ResourceLocation(LibResources.ENCHANT_QUICK_DRAW)).setParents(LibResearch.KEY_ENCHANTER);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_VAMPIRISM, new AspectList().add(Aspect.HUNGER, 1).add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 2), 3, 5, 2, new ResourceLocation(LibResources.ENCHANT_VAMPIRISM)).setParents(LibResearch.KEY_ENCHANTER);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_FOCUSED_STRIKE, new AspectList().add(Aspect.ORDER, 1).add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 2), 2, 7, 2, new ResourceLocation(LibResources.ENCHANT_FOCUSED_STRIKE)).setParents(LibResearch.KEY_ENCHANT_VAMPIRISM);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_DISPERSED_STRIKE, new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 2), 1, 6, 2, new ResourceLocation(LibResources.ENCHANT_DISPERSED_STRIKE)).setParents(LibResearch.KEY_ENCHANT_VAMPIRISM);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_FINAL_STRIKE, new AspectList().add(Aspect.ENTROPY, 3).add(Aspect.ORDER, 3).add(Aspect.WEAPON, 3).add(Aspect.MAGIC, 2), 0, 8, 2, new ResourceLocation(LibResources.ENCHANT_FINAL_STRIKE)).setParents(LibResearch.KEY_ENCHANT_FOCUSED_STRIKE, LibResearch.KEY_ENCHANT_DISPERSED_STRIKE);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_POUNCE, new AspectList().add(Aspect.AIR, 3).add(Aspect.ORDER, 3).add(Aspect.ARMOR, 3).add(Aspect.MAGIC, 2), 7, 0, 2, new ResourceLocation(LibResources.ENCHANT_POUNCE)).setParents(LibResearch.KEY_ENCHANT_ASCENT_BOOST);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_SHATTER, new AspectList().add(Aspect.EARTH, 3).add(Aspect.ENTROPY, 3).add(Aspect.TOOL, 1).add(Aspect.MAGIC, 2), 5, 8, 2, new ResourceLocation(LibResources.ENCHANT_SHATTER)).setParents(LibResearch.KEY_ENCHANT_DESINTEGRATE);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_SHOCKWAVE, new AspectList().add(Aspect.AIR, 3).add(Aspect.ENTROPY, 3).add(Aspect.ARMOR, 1).add(Aspect.MAGIC, 2), 9, 2, 2, new ResourceLocation(LibResources.ENCHANT_SHOCKWAVE)).setParents(LibResearch.KEY_ENCHANT_SLOW_FALL);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_TUNNEL, new AspectList().add(Aspect.EARTH, 3).add(Aspect.ORDER, 3).add(Aspect.TOOL, 1).add(Aspect.MAGIC, 2), 9, 6, 2, new ResourceLocation(LibResources.ENCHANT_TUNNEL)).setParents(LibResearch.KEY_ENCHANT_AUTO_SMELT);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_VALIANCE, new AspectList().add(Aspect.WEAPON, 3).add(Aspect.HEAL, 3).add(Aspect.MAGIC, 2), 1, 4, 2, new ResourceLocation(LibResources.ENCHANT_VALIANCE)).setParents(LibResearch.KEY_ENCHANT_VAMPIRISM);
		research.setPages(new ResearchPage("0")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_FOCUS_SMELT, new AspectList().add(Aspect.FIRE, 2).add(Aspect.ENERGY, 1).add(Aspect.MAGIC, 1), -2, -2, 2, new ItemStack(ModItems.focusSmelt)).setParents("FOCUSEXCAVATION").setConcealed();
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_FOCUS_SMELT));

		if (Config.allowMirrors) {
			research = new TTResearchItem(LibResearch.KEY_FOCUS_HEAL, new AspectList().add(Aspect.HEAL, 2).add(Aspect.SOUL, 1).add(Aspect.MAGIC, 1), -6, -4, 2, new ItemStack(ModItems.focusHeal)).setParents(LibResearch.KEY_FOCUS_DEFLECT).setConcealed();
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_HEAL)).setSecondary();
			research = new TTResearchItem(LibResearch.KEY_FOCUS_ENDER_CHEST, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.VOID, 1).add(Aspect.MAGIC, 1), -6, -2, 2, new ItemStack(ModItems.focusEnderChest)).setParents(LibResearch.KEY_FOCUS_DEFLECT).setConcealed();
			if (Loader.isModLoaded("EnderStorage")) {
				research.setPages(new ResearchPage("ES"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_FOCUS_ENDER_CHEST));
			} else {
				research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_FOCUS_ENDER_CHEST));
			}

			research = new TTResearchItem(LibResearch.KEY_FOCUS_DEFLECT, new AspectList().add(Aspect.MOTION, 2).add(Aspect.AIR, 1).add(Aspect.ORDER, 1).add(Aspect.DEATH, 1), -4, -3, 3, new ItemStack(ModItems.focusDeflect)).setConcealed().setParents(LibResearch.KEY_FOCUS_SMELT);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_DEFLECT)).setSecondary();

			research = new TTResearchItem(LibResearch.KEY_DISLOCATOR, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.MECHANISM, 1).add(Aspect.ELDRITCH, 1), -6, 1, 3, new ItemStack(ModBlocks.dislocator)).setConcealed().setParents(LibResearch.KEY_INTERFACE).setParentsHidden("MIRROR");
			research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_DISLOCATOR)).setSecondary();
		}

		research = new TTResearchItem(LibResearch.KEY_BLOOD_SWORD, new AspectList().add(Aspect.HUNGER, 2).add(Aspect.WEAPON, 1).add(Aspect.FLESH, 1).add(Aspect.SOUL, 1), -4, 6, 3, new ItemStack(ModItems.bloodSword)).setParents(LibResearch.KEY_CLEANSING_TALISMAN);
		research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_BLOOD_SWORD), new ResearchPage("1")).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_SUMMON, new AspectList().add(Aspect.WEAPON, 1).add(Aspect.BEAST, 3).add(Aspect.MAGIC, 3), -5, 8, 3, new ItemStack(ModBlocks.spawner)).setParents(LibResearch.KEY_BLOOD_SWORD);
		//research.setPages(new ResearchPage("0") ,ResearchHelper.recipePage(LibResearch.KEY_SUMMON+"0"),ResearchHelper.recipePage(LibResearch.KEY_SUMMON+"1"), ResearchHelper.infusionPage(LibResearch.KEY_SUMMON), new ResearchPage("1"));
		List<ResearchPage> list = new ArrayList<ResearchPage>();
		list.add(new ResearchPage("0"));
		list.add(ResearchHelper.arcaneRecipePage(LibResearch.KEY_SUMMON + "0"));
		list.add(ResearchHelper.recipePage(LibResearch.KEY_SUMMON + "1"));
		list.add(ResearchHelper.infusionPage(LibResearch.KEY_SUMMON));
		list.add(new ResearchPage("1"));
		//for(EnumMobAspect aspect:EnumMobAspect.values()) {
		//    list.add(aspect.GetRecepiePage());
		//}
		ResearchPage[] pages = new ResearchPage[list.size()];
		int i = 0;
		for (ResearchPage page : list)
			pages[i++] = page;
		research.setPages(pages);
		research = new TTResearchItem(LibResearch.KEY_REVEALING_HELM, new AspectList().add(Aspect.AURA, 2).add(Aspect.ARMOR, 1), 0, 0, 1, new ItemStack(ModItems.revealingHelm)).setParents("GOGGLES").setParentsHidden("THAUMIUM");
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_REVEALING_HELM));

		research = new TTResearchItem(LibResearch.KEY_REPAIRER, new AspectList().add(Aspect.TOOL, 2).add(Aspect.CRAFT, 1).add(Aspect.ORDER, 1).add(Aspect.MAGIC, 1), -1, -9, 3, new ItemStack(ModBlocks.repairer)).setConcealed().setParents(LibResearch.KEY_FUNNEL).setParentsHidden("THAUMIUM", "ENCHFABRIC");
		research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_REPAIRER));

		research = new TTResearchItem(LibResearch.KEY_PLATFORM, new AspectList().add(Aspect.SENSES, 2).add(Aspect.TREE, 1).add(Aspect.MOTION, 1), -2, 6, 3, new ItemStack(ModBlocks.platform)).setConcealed().setParents(LibResearch.KEY_CLEANSING_TALISMAN);
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_PLATFORM)).setSecondary();

		research = new TTResearchItem(LibResearch.KEY_MOBILIZER, new AspectList().add(Aspect.MOTION, 2).add(Aspect.ORDER, 2), -7, 5, 3, new ItemStack(ModBlocks.mobilizer)).setParents(LibResearch.KEY_MAGNETS);
		research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_MOBILIZER), ResearchHelper.arcaneRecipePage(LibResearch.KEY_RELAY)).setSecondary();

		// Peripheral documentation research
		if (Loader.isModLoaded("ComputerCraft")) {
			research = new TTResearchItem(LibResearch.KEY_PERIPHERALS, new AspectList(), -1, 0, 0, new ItemStack(Items.redstone)).setAutoUnlock().setRound();
			research.setPages(new ResearchPage("0"));

			research = new TTResearchItem(LibResearch.KEY_ASPECT_ANALYZER, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.SENSES, 1).add(Aspect.MIND, 1), 0, 1, 2, new ItemStack(ModBlocks.aspectAnalyzer)).setParents(LibResearch.KEY_PERIPHERALS).setParentsHidden("GOGGLES", "THAUMIUM").setConcealed().setRound();
			research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ASPECT_ANALYZER));
			research = new TTResearchItem(LibResearch.KEY_GOLEMCONNECTOR, new AspectList().add(Aspect.ORDER, 1).add(Aspect.TRAVEL, 2).add(Aspect.TOOL, 1), 1, 0, 0, new ItemStack(ModBlocks.golemConnector)).setParents(LibResearch.KEY_PERIPHERALS).setParentsHidden("GOLEMBELL").setConcealed().setRound();
			research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_GOLEMCONNECTOR), new ResearchPage("1"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_INTERFACE + "1"), new ResearchPage("2"), new ResearchPage("3"));
		}

		if (ConfigHandler.enableKami) {
			research = new KamiResearchItem(LibResearch.KEY_DIMENSION_SHARDS, new AspectList(), 7, 8, 0, new ItemStack(ModItems.kamiResource, 1, 7)).setStub().setAutoUnlock().setRound();
			research.setPages(new ResearchPage("0"));

			kamiResearch = new KamiResearchItem(LibResearch.KEY_ICHOR, new AspectList().add(Aspect.MAN, 1).add(Aspect.LIGHT, 2).add(Aspect.SOUL, 1).add(Aspect.TAINT, 1), 9, 8, 5, new ItemStack(ModItems.kamiResource, 1, 0));
			kamiResearch.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_CLOTH, new AspectList().add(Aspect.CLOTH, 2).add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1).add(Aspect.SENSES, 1), 11, 7, 5, new ItemStack(ModItems.kamiResource, 1, 1)).setConcealed().setParents(LibResearch.KEY_ICHOR);
			research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHOR_CLOTH));

			research = new KamiResearchItem(LibResearch.KEY_ICHORIUM, new AspectList().add(Aspect.METAL, 2).add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1).add(Aspect.TOOL, 1), 11, 9, 5, new ItemStack(ModItems.kamiResource, 1, 2)).setConcealed().setParents(LibResearch.KEY_ICHOR).setParentsHidden(LibResearch.KEY_ICHOR_CLOTH);
			research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHORIUM));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_CAP, new AspectList().add(Aspect.TOOL, 2).add(Aspect.METAL, 1).add(Aspect.LIGHT, 1).add(Aspect.MAGIC, 1), 11, 11, 5, new ItemStack(ModItems.kamiResource, 1, 4)).setConcealed().setParents(LibResearch.KEY_ICHORIUM);
			research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHOR_CAP));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_ROD, new AspectList().add(Aspect.TOOL, 2).add(Aspect.CLOTH, 1).add(Aspect.LIGHT, 1).add(Aspect.MAGIC, 1), 14, 2, 5, new ItemStack(ModItems.kamiResource, 1, 5)).setConcealed().setParents(LibResearch.KEY_ICHOR_CLOTH).setParentsHidden(LibResearch.KEY_ICHOR_CAP);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_ROD));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_ARMOR, new AspectList().add(Aspect.ARMOR, 2).add(Aspect.CLOTH, 1).add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1), 17, 5, 5, new ItemStack(ModItems.ichorChest)).setConcealed().setParents(LibResearch.KEY_ICHOR_CLOTH);
			research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_HELM), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_CHEST), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_LEGS), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_BOOTS));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_HELM_GEM, new AspectList().add(Aspect.WATER, 2).add(Aspect.HEAL, 1).add(Aspect.HUNGER, 1).add(Aspect.AURA, 1), 18, 3, 5, new ItemStack(ModItems.ichorHelmGem)).setParents(LibResearch.KEY_ICHORCLOTH_ARMOR);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_HELM_GEM));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_CHEST_GEM, new AspectList().add(Aspect.AIR, 2).add(Aspect.MOTION, 1).add(Aspect.FLIGHT, 1).add(Aspect.ELDRITCH, 1), 17, 7, 5, new ItemStack(ModItems.ichorChestGem)).setParents(LibResearch.KEY_ICHORCLOTH_ARMOR);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_CHEST_GEM));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_LEGS_GEM, new AspectList().add(Aspect.FIRE, 2).add(Aspect.HEAL, 1).add(Aspect.GREED, 1).add(Aspect.ENERGY, 1), 17, 9, 5, new ItemStack(ModItems.ichorLegsGem)).setParents(LibResearch.KEY_ICHORCLOTH_ARMOR);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_LEGS_GEM), new ResearchPage("1"));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM, new AspectList().add(Aspect.EARTH, 2).add(Aspect.TRAVEL, 1).add(Aspect.MINE, 1).add(Aspect.PLANT, 1), 15, 10, 5, new ItemStack(ModItems.ichorBootsGem)).setParents(LibResearch.KEY_ICHORCLOTH_ARMOR);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM));

			research = new KamiResearchItem(LibResearch.KEY_CAT_AMULET, new AspectList().add(Aspect.MIND, 2).add(Aspect.ORDER, 1).add(Aspect.DARKNESS, 1).add(Aspect.DEATH, 1), 13, 10, 5, new ItemStack(ModItems.catAmulet)).setParents(LibResearch.KEY_ICHORIUM);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_CAT_AMULET));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_TOOLS, new AspectList().add(Aspect.TOOL, 2).add(Aspect.WEAPON, 1).add(Aspect.METAL, 1).add(Aspect.CRAFT, 1), 13, 12, 5, new ItemStack(ModItems.ichorPick)).setConcealed().setParents(LibResearch.KEY_ICHORIUM).setParentsHidden(LibResearch.KEY_ICHORCLOTH_ROD);
			research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHOR_PICK), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHOR_SHOVEL), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHOR_AXE), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHOR_SWORD));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_PICK_GEM, new AspectList().add(Aspect.FIRE, 2).add(Aspect.TOOL, 1).add(Aspect.MINE, 1).add(Aspect.EARTH, 1), 13, 15, 5, new ItemStack(ModItems.ichorPickGem)).setParents(LibResearch.KEY_ICHOR_TOOLS);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR_PICK_GEM), new ResearchPage("1"));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_SHOVEL_GEM, new AspectList().add(Aspect.EARTH, 2).add(Aspect.TOOL, 1).add(Aspect.MINE, 1).add(Aspect.EARTH, 1), 15, 15, 5, new ItemStack(ModItems.ichorShovelGem)).setParents(LibResearch.KEY_ICHOR_TOOLS);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR_SHOVEL_GEM));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_AXE_GEM, new AspectList().add(Aspect.WATER, 2).add(Aspect.TOOL, 1).add(Aspect.TREE, 1).add(Aspect.CROP, 1), 16, 14, 5, new ItemStack(ModItems.ichorAxeGem)).setParents(LibResearch.KEY_ICHOR_TOOLS);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR_AXE_GEM));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_SWORD_GEM, new AspectList().add(Aspect.AIR, 2).add(Aspect.WEAPON, 1).add(Aspect.SOUL, 1).add(Aspect.HUNGER, 1), 16, 12, 5, new ItemStack(ModItems.ichorSwordGem)).setParents(LibResearch.KEY_ICHOR_TOOLS);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR_SWORD_GEM), new ResearchPage("1"));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_POUCH, new AspectList().add(Aspect.VOID, 2).add(Aspect.CLOTH, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MAN, 1), 13, 6, 5, new ItemStack(ModItems.ichorPouch)).setParents(LibResearch.KEY_ICHOR_CLOTH);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR_POUCH));

			research = new KamiResearchItem(LibResearch.KEY_BLOCK_TALISMAN, new AspectList().add(Aspect.VOID, 2).add(Aspect.DARKNESS, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MAGIC, 1), 14, 17, 5, new ItemStack(ModItems.blockTalisman)).setParents(LibResearch.KEY_ICHOR_PICK_GEM, LibResearch.KEY_ICHOR_SHOVEL_GEM);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_BLOCK_TALISMAN));

			research = new KamiResearchItem(LibResearch.KEY_PLACEMENT_MIRROR, new AspectList().add(Aspect.CRAFT, 2).add(Aspect.CRYSTAL, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MIND, 1), 17, 16, 5, new ItemStack(ModItems.placementMirror)).setParents(LibResearch.KEY_BLOCK_TALISMAN);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_PLACEMENT_MIRROR));

			research = new KamiResearchItem(LibResearch.KEY_FOCUS_SHADOWBEAM, new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.MAGIC, 1).add(Aspect.ELDRITCH, 1).add(Aspect.TAINT, 1), 14, 4, 5, new ItemStack(ModItems.focusShadowbeam)).setParents(LibResearch.KEY_ICHORCLOTH_ROD);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_SHADOWBEAM));

			research = new KamiResearchItem(LibResearch.KEY_FOCUS_XP_DRAIN, new AspectList().add(Aspect.MIND, 2).add(Aspect.MAGIC, 1).add(Aspect.AURA, 1).add(Aspect.MAN, 1), 12, 3, 5, new ItemStack(ModItems.focusXPDrain)).setParents(LibResearch.KEY_ICHORCLOTH_ROD);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_XP_DRAIN));

			research = new KamiResearchItem(LibResearch.KEY_PROTOCLAY, new AspectList().add(Aspect.TOOL, 2).add(Aspect.MINE, 1).add(Aspect.MAN, 1).add(Aspect.MECHANISM, 1), 12, 17, 5, new ItemStack(ModItems.protoclay)).setParents(LibResearch.KEY_ICHOR_PICK_GEM).setParentsHidden(LibResearch.KEY_ICHOR_SHOVEL_GEM);
			research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_PROTOCLAY));

			if (Config.allowMirrors) {
				research = new KamiResearchItem(LibResearch.KEY_WARP_GATE, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.ELDRITCH, 1).add(Aspect.FLIGHT, 1).add(Aspect.MECHANISM, 1), 19, 6, 5, new ItemStack(ModBlocks.warpGate)).setParents(LibResearch.KEY_ICHORCLOTH_CHEST_GEM).setParentsHidden(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM);
				research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_WARP_GATE), new ResearchPage("1"), ResearchHelper.infusionPage(LibResearch.KEY_SKY_PEARL));

				research = new KamiResearchItem(LibResearch.KEY_FOCUS_RECALL, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.ELDRITCH, 1).add(Aspect.FLIGHT, 1).add(Aspect.MAGIC, 1), 20, 8, 5, new ItemStack(ModItems.focusRecall)).setParents(LibResearch.KEY_WARP_GATE).setParentsHidden(LibResearch.KEY_ICHORCLOTH_ROD);
				research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_RECALL));
			}
		}
	}

	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");
		ResearchCategories.registerCategory(LibResearch.CATEGORY_THAUMICTINKERER, new ResourceLocation(LibResources.MISC_R_ENCHANTING), background);
	}

	public static ResearchPage recipePage(String name) {
		return new ResearchPage((IRecipe) ConfigResearch.recipes.get(name));
	}

	public static ResearchPage arcaneRecipePage(String name) {
		return new ResearchPage((IArcaneRecipe) ConfigResearch.recipes.get(name));
	}

	public static ResearchPage infusionPage(String name) {
		return new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get(name));
	}

	public static ResearchPage enchantPage(String name) {
		return new ResearchPage((InfusionEnchantmentRecipe) ConfigResearch.recipes.get(name));
	}

	public static ResearchPage crucibleRecipePage(String name) {
		return new ResearchPage((CrucibleRecipe) ConfigResearch.recipes.get(name));
	}

	public static ResearchPage LeviationaryHelp() {
		return new ResearchPage(Arrays.asList((new AspectList()), 5, 1, 1, Arrays.asList(new ItemStack(ModBlocks.mobilizerRelay), new ItemStack(ConfigBlocks.blockHole, 1, 15), new ItemStack(ModBlocks.mobilizer), new ItemStack(ConfigBlocks.blockHole, 1, 15), new ItemStack(ModBlocks.mobilizerRelay))));

	}
}
