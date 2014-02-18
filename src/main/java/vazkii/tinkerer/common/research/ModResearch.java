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
import net.minecraft.item.Item;
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

import java.util.Arrays;

public final class ModResearch {

	public static void initResearch() {
		registerResearchPages();

		ResearchItem research;

		research = new TTResearchItem(LibResearch.KEY_DARK_QUARTZ, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList(), -2, 2, 0, new ItemStack(ModItems.darkQuartz)).setStub().setAutoUnlock().setRound().registerResearchItem();
		research.setPages(new ResearchPage("0"), recipePage(LibResearch.KEY_DARK_QUARTZ + 0), recipePage(LibResearch.KEY_DARK_QUARTZ + 1), recipePage(LibResearch.KEY_DARK_QUARTZ + 2), recipePage(LibResearch.KEY_DARK_QUARTZ + 3), recipePage(LibResearch.KEY_DARK_QUARTZ + 4), recipePage(LibResearch.KEY_DARK_QUARTZ + 5));

		research = new TTResearchItem(LibResearch.KEY_SHARE_TOME, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList(), 0, 0, 0, new ItemStack(ModItems.shareBook)).setStub().setAutoUnlock().setRound().registerResearchItem();
		if(ConfigHandler.enableSurvivalShareTome)
			research.setPages(new ResearchPage("0"), recipePage(LibResearch.KEY_SHARE_TOME));
		else research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_INTERFACE, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.ORDER, 4), -4, 2, 1, new ItemStack(ModBlocks.interfase)).setParents(LibResearch.KEY_DARK_QUARTZ).registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_INTERFACE), new ResearchPage("1"), arcaneRecipePage(LibResearch.KEY_CONNECTOR), new ResearchPage("2"));

		research = new TTResearchItem(LibResearch.KEY_GASEOUS_LIGHT, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.LIGHT, 2).add(Aspect.AIR, 1), 0, -3, 1, new ItemStack(ModItems.gaseousLight)).setParents("NITOR").registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_GASEOUS_LIGHT));

		research = new TTResearchItem(LibResearch.KEY_GASEOUS_SHADOW, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.AIR, 1).add(Aspect.MOTION, 4), -1, -5, 2, new ItemStack(ModItems.gaseousShadow)).setParents(LibResearch.KEY_GASEOUS_LIGHT).setSiblings(LibResearch.KEY_GAS_REMOVER).registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_GASEOUS_SHADOW));

		research = new TTResearchItem(LibResearch.KEY_GAS_REMOVER, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList(), -2, -7, 0, new ItemStack(ModItems.gasRemover)).setRound().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_GAS_REMOVER));

		research = new TTResearchItem(LibResearch.KEY_SPELL_CLOTH, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.CLOTH, 1), 3, 2, 2, new ItemStack(ModItems.spellCloth)).setParentsHidden("ENCHFABRIC").registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_SPELL_CLOTH));

		research = new TTResearchItem(LibResearch.KEY_ANIMATION_TABLET, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.METAL, 1).add(Aspect.MOTION, 1).add(Aspect.ENERGY, 1), -8, 2, 4, new ItemStack(ModBlocks.animationTablet)).setParents(LibResearch.KEY_MAGNETS).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ANIMATION_TABLET));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_FLIGHT, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MOTION, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR, 2), -3, -4, 2, new ItemStack(ModItems.focusFlight)).setParents(LibResearch.KEY_FOCUS_SMELT).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_FLIGHT));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_DISLOCATION, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.MAGIC, 1).add(Aspect.EXCHANGE, 1), -5, -5, 2, new ItemStack(ModItems.focusDislocation)).setParents(LibResearch.KEY_FOCUS_FLIGHT).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), infusionPage(LibResearch.KEY_FOCUS_DISLOCATION));

		research = new TTResearchItem(LibResearch.KEY_CLEANSING_TALISMAN, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.HEAL, 2).add(Aspect.ORDER, 1).add(Aspect.POISON, 1), -3, 4, 3, new ItemStack(ModItems.cleansingTalisman)).setParents(LibResearch.KEY_DARK_QUARTZ).registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_CLEANSING_TALISMAN));

		research = new TTResearchItem(LibResearch.KEY_BRIGHT_NITOR, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.LIGHT, 2).add(Aspect.FIRE, 1).add(Aspect.ENERGY, 1).add(Aspect.AIR, 1), 1, -5, 2, new ItemStack(ModItems.brightNitor)).setParents(LibResearch.KEY_GASEOUS_LIGHT).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_BRIGHT_NITOR));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_TELEKINESIS, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.MAGIC, 1).add(Aspect.MOTION, 1), -4, -6, 2, new ItemStack(ModItems.focusTelekinesis)).setParents(LibResearch.KEY_FOCUS_FLIGHT).registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_TELEKINESIS));

		research = new TTResearchItem(LibResearch.KEY_MAGNETS, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.MOTION, 1).add(Aspect.SENSES, 1), -6, 3, 3, new ItemStack(ModBlocks.magnet)).setParents(LibResearch.KEY_INTERFACE).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), arcaneRecipePage(LibResearch.KEY_MAGNET), arcaneRecipePage(LibResearch.KEY_MOB_MAGNET), cruciblePage(LibResearch.KEY_MAGNETS));

		research = new TTResearchItem(LibResearch.KEY_ENCHANTER, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.AURA, 1).add(Aspect.ELDRITCH, 1).add(Aspect.DARKNESS, 1).add(Aspect.MIND, 1), 5, 4, 5, new ItemStack(ModBlocks.enchanter)).setParents(LibResearch.KEY_SPELL_CLOTH).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), new ResearchPage("2"), infusionPage(LibResearch.KEY_ENCHANTER));

		research = new TTResearchItem(LibResearch.KEY_XP_TALISMAN, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.GREED, 1).add(Aspect.MAGIC, 1).add(Aspect.MAN, 1), 4, -1, 2, new ItemStack(ModItems.xpTalisman, 1, 1)).setParents("JARBRAIN", LibResearch.KEY_SPELL_CLOTH).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_XP_TALISMAN));

		research = new TTResearchItem(LibResearch.KEY_FUNNEL, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TOOL, 1).add(Aspect.TRAVEL, 2), 0, -7, 1, new ItemStack(ModBlocks.funnel)).setParentsHidden("DISTILESSENTIA").setParents(LibResearch.KEY_BRIGHT_NITOR).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_FUNNEL));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_ASCENT_BOOST, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.AIR, 1).add(Aspect.MOTION, 1).add(Aspect.MAGIC, 2), 6, 2, 2, new ResourceLocation(LibResources.ENCHANT_ASCENT_BOOST)).setParents(LibResearch.KEY_ENCHANTER).registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_SLOW_FALL, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.AIR, 1).add(Aspect.MOTION, 1).add(Aspect.MAGIC, 2), 7, 3, 2, new ResourceLocation(LibResources.ENCHANT_SLOW_FALL)).setParents(LibResearch.KEY_ENCHANTER).registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_AUTO_SMELT, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.FIRE, 1).add(Aspect.ENTROPY, 1).add(Aspect.MAGIC, 2), 7, 5, 2, new ResourceLocation(LibResources.ENCHANT_AUTO_SMELT)).setParents(LibResearch.KEY_ENCHANTER).registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_DESINTEGRATE, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.VOID, 1).add(Aspect.MAGIC, 2), 6, 6, 2, new ResourceLocation(LibResources.ENCHANT_DESINTEGRATE)).setParents(LibResearch.KEY_ENCHANTER).registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_QUICK_DRAW, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.SENSES, 1).add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 2), 4, 6, 2, new ResourceLocation(LibResources.ENCHANT_QUICK_DRAW)).setParents(LibResearch.KEY_ENCHANTER).registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_VAMPIRISM, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.HUNGER, 1).add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 2), 3, 5, 2, new ResourceLocation(LibResources.ENCHANT_VAMPIRISM)).setParents(LibResearch.KEY_ENCHANTER).registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_SMELT, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.FIRE, 2).add(Aspect.ENERGY, 1).add(Aspect.MAGIC, 1), -2, -2, 2, new ItemStack(ModItems.focusSmelt)).setParents("FOCUSEXCAVATION").setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_FOCUS_SMELT));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_HEAL, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.HEAL, 2).add(Aspect.SOUL, 1).add(Aspect.MAGIC, 1), -6, -4, 2, new ItemStack(ModItems.focusHeal)).setParents(LibResearch.KEY_FOCUS_DEFLECT).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_HEAL));

		if(Config.allowMirrors) {
			research = new TTResearchItem(LibResearch.KEY_FOCUS_ENDER_CHEST, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.VOID, 1).add(Aspect.MAGIC, 1), -6, -2, 2, new ItemStack(ModItems.focusEnderChest)).setParents(LibResearch.KEY_FOCUS_DEFLECT).setConcealed().registerResearchItem();
			if(Loader.isModLoaded("EnderStorage"))
			{
				research.setPages(new ResearchPage("ES"), arcaneRecipePage(LibResearch.KEY_FOCUS_ENDER_CHEST));
			}
			else
			{
				research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_FOCUS_ENDER_CHEST));
			}

			research = new TTResearchItem(LibResearch.KEY_FOCUS_DEFLECT, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MOTION, 2).add(Aspect.AIR, 1).add(Aspect.ORDER, 1).add(Aspect.DEATH, 1), -4, -3, 3, new ItemStack(ModItems.focusDeflect)).setConcealed().setParents(LibResearch.KEY_FOCUS_SMELT).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_DEFLECT));

			research = new TTResearchItem(LibResearch.KEY_DISLOCATOR, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.MECHANISM, 1).add(Aspect.ELDRITCH, 1), -6, 1, 3, new ItemStack(ModBlocks.dislocator)).setConcealed().setParents(LibResearch.KEY_INTERFACE).setParentsHidden("MIRROR").registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_DISLOCATOR));
		}

		research = new TTResearchItem(LibResearch.KEY_BLOOD_SWORD, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.HUNGER, 2).add(Aspect.WEAPON, 1).add(Aspect.FLESH, 1).add(Aspect.SOUL, 1), -4, 6, 3, new ItemStack(ModItems.bloodSword)).setParents(LibResearch.KEY_CLEANSING_TALISMAN).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_BLOOD_SWORD)).setParentsHidden(LibResearch.KEY_FOCUS_HEAL, LibResearch.KEY_CLEANSING_TALISMAN).setParents("INFUSION");

		//research = new TTResearchItem(LibResearch.KEY_REVEALING_HELM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.AURA, 2).add(Aspect.ARMOR, 1), 0, 0, 1, new ItemStack(ModItems.revealingHelm)).setParents("GOGGLES").setParentsHidden("THAUMIUM").registerResearchItem();
		//research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_REVEALING_HELM));

		research = new TTResearchItem(LibResearch.KEY_REPAIRER, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TOOL, 2).add(Aspect.CRAFT, 1).add(Aspect.ORDER, 1).add(Aspect.MAGIC, 1), -1, -9, 3, new ItemStack(ModBlocks.repairer)).setConcealed().setParents(LibResearch.KEY_FUNNEL).setParentsHidden("THAUMIUM", "ENCHFABRIC").registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_REPAIRER));

		research = new TTResearchItem(LibResearch.KEY_PLATFORM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.SENSES, 2).add(Aspect.TREE, 1).add(Aspect.MOTION, 1), -2, 6, 3, new ItemStack(ModBlocks.platform)).setConcealed().setParents(LibResearch.KEY_CLEANSING_TALISMAN).registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_PLATFORM));

		research = new TTResearchItem(LibResearch.KEY_MOBILIZER, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MOTION, 2).add(Aspect.ORDER, 2), -7, 5, 3, new ItemStack(ModBlocks.mobilizer)).setParents(LibResearch.KEY_MAGNETS).registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_MOBILIZER),arcaneRecipePage(LibResearch.KEY_RELAY));

		// Peripheral documentation research
		if(Loader.isModLoaded("ComputerCraft")) {
			research = new TTResearchItem(LibResearch.KEY_PERIPHERALS, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList(), -1, 0, 0, new ItemStack(Item.redstone)).setAutoUnlock().setRound().registerResearchItem();
			research.setPages(new ResearchPage("0"));

			research = new TTResearchItem(LibResearch.KEY_ASPECT_ANALYZER, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.SENSES, 1).add(Aspect.MIND, 1), 0, 1, 2, new ItemStack(ModBlocks.aspectAnalyzer)).setParents(LibResearch.KEY_PERIPHERALS).setParentsHidden("GOGGLES", "THAUMIUM").setConcealed().setRound().registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ASPECT_ANALYZER));
			research=new TTResearchItem(LibResearch.KEY_GOLEMCONNECTOR, LibResearch.CATEGORY_THAUMICTINKERER,new AspectList().add(Aspect.ORDER, 1).add(Aspect.TRAVEL, 2).add(Aspect.TOOL, 1), 1, 0 ,0,new ItemStack(ModBlocks.golemConnector)).setParents(LibResearch.KEY_PERIPHERALS).setParentsHidden("GOLEMBELL").setConcealed().setRound().registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_GOLEMCONNECTOR), new ResearchPage("1"), arcaneRecipePage(LibResearch.KEY_CONNECTOR), new ResearchPage("2"),new ResearchPage("3"));
		}

		if(ConfigHandler.enableKami) {
			research = new KamiResearchItem(LibResearch.KEY_DIMENSION_SHARDS, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList(), 7, 8, 0, new ItemStack(ModItems.kamiResource, 1, 7)).setStub().setAutoUnlock().setRound().registerResearchItem();
			research.setPages(new ResearchPage("0"));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MAN, 1).add(Aspect.LIGHT, 2).add(Aspect.SOUL, 1).add(Aspect.TAINT, 1), 9, 8, 5, new ItemStack(ModItems.kamiResource, 1, 0)).setConcealed().setSpecial().registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHOR));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_CLOTH, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.CLOTH, 2).add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1).add(Aspect.SENSES, 1), 11, 7, 5, new ItemStack(ModItems.kamiResource, 1, 1)).setConcealed().setParents(LibResearch.KEY_ICHOR).registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ICHOR_CLOTH));

			research = new KamiResearchItem(LibResearch.KEY_ICHORIUM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.METAL, 2).add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1).add(Aspect.TOOL, 1), 11, 9, 5, new ItemStack(ModItems.kamiResource, 1, 2)).setConcealed().setParents(LibResearch.KEY_ICHOR).setParentsHidden(LibResearch.KEY_ICHOR_CLOTH).registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ICHORIUM));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_CAP, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TOOL, 2).add(Aspect.METAL, 1).add(Aspect.LIGHT, 1).add(Aspect.MAGIC, 1), 11, 11, 5, new ItemStack(ModItems.kamiResource, 1, 4)).setConcealed().setParents(LibResearch.KEY_ICHORIUM).registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ICHOR_CAP));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_ROD, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TOOL, 2).add(Aspect.CLOTH, 1).add(Aspect.LIGHT, 1).add(Aspect.MAGIC, 1), 14, 2, 5, new ItemStack(ModItems.kamiResource, 1, 5)).setConcealed().setParents(LibResearch.KEY_ICHOR_CLOTH).setParentsHidden(LibResearch.KEY_ICHOR_CAP).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHORCLOTH_ROD));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_ARMOR, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.ARMOR, 2).add(Aspect.CLOTH, 1).add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1), 17, 5, 5, new ItemStack(ModItems.ichorChest)).setConcealed().setParents(LibResearch.KEY_ICHOR_CLOTH).registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_HELM), arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_CHEST), arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_LEGS), arcaneRecipePage(LibResearch.KEY_ICHORCLOTH_BOOTS));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_HELM_GEM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.WATER, 2).add(Aspect.HEAL, 1).add(Aspect.HUNGER, 1).add(Aspect.AURA, 1), 18, 3, 5, new ItemStack(ModItems.ichorHelmGem)).setHidden().setParents(LibResearch.KEY_ICHORCLOTH_ARMOR).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHORCLOTH_HELM_GEM));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_CHEST_GEM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.AIR, 2).add(Aspect.MOTION, 1).add(Aspect.FLIGHT, 1).add(Aspect.ELDRITCH, 1), 17, 7, 5, new ItemStack(ModItems.ichorChestGem)).setHidden().setParents(LibResearch.KEY_ICHORCLOTH_ARMOR).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHORCLOTH_CHEST_GEM));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_LEGS_GEM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.FIRE, 2).add(Aspect.HEAL, 1).add(Aspect.GREED, 1).add(Aspect.ENERGY, 1), 17, 9, 5, new ItemStack(ModItems.ichorLegsGem)).setHidden().setParents(LibResearch.KEY_ICHORCLOTH_ARMOR).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHORCLOTH_LEGS_GEM), new ResearchPage("1"));

			research = new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.EARTH, 2).add(Aspect.TRAVEL, 1).add(Aspect.MINE, 1).add(Aspect.PLANT, 1), 15, 10, 5, new ItemStack(ModItems.ichorBootsGem)).setHidden().setParents(LibResearch.KEY_ICHORCLOTH_ARMOR).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM));

			research = new KamiResearchItem(LibResearch.KEY_CAT_AMULET, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MIND, 2).add(Aspect.ORDER, 1).add(Aspect.DARKNESS, 1).add(Aspect.DEATH, 1), 13, 10, 5, new ItemStack(ModItems.catAmulet)).setHidden().setParents(LibResearch.KEY_ICHORIUM).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_CAT_AMULET));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_TOOLS, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TOOL, 2).add(Aspect.WEAPON, 1).add(Aspect.METAL, 1).add(Aspect.CRAFT, 1), 13, 12, 5, new ItemStack(ModItems.ichorPick)).setConcealed().setParents(LibResearch.KEY_ICHORIUM).setParentsHidden(LibResearch.KEY_ICHORCLOTH_ROD).registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ICHOR_PICK), arcaneRecipePage(LibResearch.KEY_ICHOR_SHOVEL), arcaneRecipePage(LibResearch.KEY_ICHOR_AXE), arcaneRecipePage(LibResearch.KEY_ICHOR_SWORD));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_PICK_GEM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.FIRE, 2).add(Aspect.TOOL, 1).add(Aspect.MINE, 1).add(Aspect.STONE, 1), 13, 15, 5, new ItemStack(ModItems.ichorPickGem)).setHidden().setParents(LibResearch.KEY_ICHOR_TOOLS).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHOR_PICK_GEM), new ResearchPage("1"));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_SHOVEL_GEM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.EARTH, 2).add(Aspect.TOOL, 1).add(Aspect.MINE, 1).add(Aspect.EARTH, 1), 15, 15, 5, new ItemStack(ModItems.ichorShovelGem)).setHidden().setParents(LibResearch.KEY_ICHOR_TOOLS).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHOR_SHOVEL_GEM));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_AXE_GEM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.WATER, 2).add(Aspect.TOOL, 1).add(Aspect.TREE, 1).add(Aspect.SEED, 1), 16, 14, 5, new ItemStack(ModItems.ichorAxeGem)).setHidden().setParents(LibResearch.KEY_ICHOR_TOOLS).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHOR_AXE_GEM));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_SWORD_GEM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.AIR, 2).add(Aspect.WEAPON, 1).add(Aspect.SOUL, 1).add(Aspect.HUNGER, 1), 16, 12, 5, new ItemStack(ModItems.ichorSwordGem)).setHidden().setParents(LibResearch.KEY_ICHOR_TOOLS).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHOR_SWORD_GEM), new ResearchPage("1"));

			research = new KamiResearchItem(LibResearch.KEY_ICHOR_POUCH, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.VOID, 2).add(Aspect.CLOTH, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MAN, 1), 13, 6, 5, new ItemStack(ModItems.ichorPouch)).setHidden().setParents(LibResearch.KEY_ICHOR_CLOTH).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_ICHOR_POUCH));

			research = new KamiResearchItem(LibResearch.KEY_BLOCK_TALISMAN, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.VOID, 2).add(Aspect.DARKNESS, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MAGIC, 1), 14, 17, 5, new ItemStack(ModItems.blockTalisman)).setHidden().setParents(LibResearch.KEY_ICHOR_PICK_GEM, LibResearch.KEY_ICHOR_SHOVEL_GEM).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_BLOCK_TALISMAN));

			research = new KamiResearchItem(LibResearch.KEY_PLACEMENT_MIRROR, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.CRAFT, 2).add(Aspect.CRYSTAL, 1).add(Aspect.ELDRITCH, 1).add(Aspect.MIND, 1), 17, 16, 5, new ItemStack(ModItems.placementMirror)).setHidden().setParents(LibResearch.KEY_BLOCK_TALISMAN).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_PLACEMENT_MIRROR));

			research = new KamiResearchItem(LibResearch.KEY_FOCUS_SHADOWBEAM, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.MAGIC, 1).add(Aspect.ELDRITCH, 1).add(Aspect.TAINT, 1), 14, 4, 5, new ItemStack(ModItems.focusShadowbeam)).setHidden().setParents(LibResearch.KEY_ICHORCLOTH_ROD).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_SHADOWBEAM));

			research = new KamiResearchItem(LibResearch.KEY_FOCUS_XP_DRAIN, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.MIND, 2).add(Aspect.MAGIC, 1).add(Aspect.AURA, 1).add(Aspect.MAN, 1), 12, 3, 5, new ItemStack(ModItems.focusXPDrain)).setHidden().setParents(LibResearch.KEY_ICHORCLOTH_ROD).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_XP_DRAIN));

			research = new KamiResearchItem(LibResearch.KEY_PROTOCLAY, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TOOL, 2).add(Aspect.MINE, 1).add(Aspect.MAN, 1).add(Aspect.MECHANISM, 1), 12, 17, 5, new ItemStack(ModItems.protoclay)).setHidden().setParents(LibResearch.KEY_ICHOR_PICK_GEM).setParentsHidden(LibResearch.KEY_ICHOR_SHOVEL_GEM).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_PROTOCLAY));

			if(Config.allowMirrors) {
				research = new KamiResearchItem(LibResearch.KEY_WARP_GATE, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.ELDRITCH, 1).add(Aspect.FLIGHT, 1).add(Aspect.MECHANISM, 1), 19, 6, 5, new ItemStack(ModBlocks.warpGate)).setHidden().setParents(LibResearch.KEY_ICHORCLOTH_CHEST_GEM).setParentsHidden(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM).registerResearchItem();
				research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_WARP_GATE), new ResearchPage("1"), infusionPage(LibResearch.KEY_SKY_PEARL));

				research = new KamiResearchItem(LibResearch.KEY_FOCUS_RECALL, LibResearch.CATEGORY_THAUMICTINKERER, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.ELDRITCH, 1).add(Aspect.FLIGHT, 1).add(Aspect.MAGIC, 1), 20, 8, 5, new ItemStack(ModItems.focusRecall)).setHidden().setParents(LibResearch.KEY_WARP_GATE).setParentsHidden(LibResearch.KEY_ICHORCLOTH_ROD).registerResearchItem();
				research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_RECALL));
			}
		}
	}

	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");

		//ResearchCategories.registerCategory(LibResearch.CATEGORY_ENCHANTING, new ResourceLocation(LibResources.MISC_R_ENCHANTING), background);
		ResearchCategories.registerCategory(LibResearch.CATEGORY_THAUMICTINKERER, new ResourceLocation(LibResources.MISC_R_ENCHANTING), background);
	}

	private static ResearchPage recipePage(String name) {
		return new ResearchPage((IRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage arcaneRecipePage(String name) {
		return new ResearchPage((IArcaneRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage infusionPage(String name) {
		return new ResearchPage((InfusionRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage enchantPage(String name) {
		return new ResearchPage((InfusionEnchantmentRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage cruciblePage(String name) {
		return new ResearchPage((CrucibleRecipe) ConfigResearch.recipes.get(name));
	}
	private static ResearchPage LeviationaryHelp()
	{
		ResearchPage page=new ResearchPage(Arrays.asList((new AspectList()),5,1,1,Arrays.asList(new ItemStack(ModBlocks.mobilizerRelay),new ItemStack(ConfigBlocks.blockHole,1,15),new ItemStack(ModBlocks.mobilizer),new ItemStack(ConfigBlocks.blockHole,1,15),new ItemStack(ModBlocks.mobilizerRelay))));
		return page;
	}
}
