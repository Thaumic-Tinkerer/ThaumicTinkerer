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
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.lib.LibResearch;
import cpw.mods.fml.common.Loader;

public final class ModResearch {

	public static void initResearch() {
		registerResearchPages();

		ResearchItem research;

		research = new TTResearchItem(LibResearch.KEY_DARK_QUARTZ, LibResearch.CATEGORY_ARTIFICE, new AspectList(), -2, -1, 0, new ItemStack(ModItems.darkQuartz)).setStub().setAutoUnlock().setRound().registerResearchItem();
		research.setPages(new ResearchPage("0"), recipePage(LibResearch.KEY_DARK_QUARTZ + 0), recipePage(LibResearch.KEY_DARK_QUARTZ + 1), recipePage(LibResearch.KEY_DARK_QUARTZ + 2), recipePage(LibResearch.KEY_DARK_QUARTZ + 3), recipePage(LibResearch.KEY_DARK_QUARTZ + 4), recipePage(LibResearch.KEY_DARK_QUARTZ + 5));

		research = new TTResearchItem(LibResearch.KEY_INTERFACE, LibResearch.CATEGORY_ARTIFICE, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.ORDER, 4), 3, -3, 1, new ItemStack(ModBlocks.interfase)).setParents("ARCANESTONE").registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_INTERFACE), new ResearchPage("1"), arcaneRecipePage(LibResearch.KEY_CONNECTOR), new ResearchPage("2"));

		research = new TTResearchItem(LibResearch.KEY_GASEOUS_LIGHT, LibResearch.CATEGORY_ALCHEMY, new AspectList().add(Aspect.LIGHT, 2).add(Aspect.AIR, 1), 1, -3, 1, new ItemStack(ModItems.gaseousLight)).setParents("NITOR").registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_GASEOUS_LIGHT));

		research = new TTResearchItem(LibResearch.KEY_GASEOUS_SHADOW, LibResearch.CATEGORY_ALCHEMY, new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.AIR, 1).add(Aspect.MOTION, 4), 2, 3, 2, new ItemStack(ModItems.gaseousShadow)).setParents("ALUMENTUM").setParentsHidden(LibResearch.KEY_GASEOUS_LIGHT).setSiblings(LibResearch.KEY_GAS_REMOVER).registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_GASEOUS_SHADOW));

		research = new TTResearchItem(LibResearch.KEY_GAS_REMOVER, LibResearch.CATEGORY_ALCHEMY, new AspectList(), 1, 3, 0, new ItemStack(ModItems.gasRemover)).setRound().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_GAS_REMOVER));

		research = new TTResearchItem(LibResearch.KEY_SPELL_CLOTH, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.CLOTH, 1), 0, 0, 2, new ItemStack(ModItems.spellCloth)).setParentsHidden("ENCHFABRIC").registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_SPELL_CLOTH));

		research = new TTResearchItem(LibResearch.KEY_ANIMATION_TABLET, LibResearch.CATEGORY_GOLEMANCY, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.METAL, 1).add(Aspect.MOTION, 1).add(Aspect.ENERGY, 1), -3, 1, 4, new ItemStack(ModBlocks.animationTablet)).setParents("COREGATHER").setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ANIMATION_TABLET));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_FLIGHT, LibResearch.CATEGORY_THAUMATURGY, new AspectList().add(Aspect.MOTION, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR, 2), 4, -6, 2, new ItemStack(ModItems.focusFlight)).setParents("FOCUSSHOCK").setParentsHidden("ELEMENTALSWORD").setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_FLIGHT));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_DISLOCATION, LibResearch.CATEGORY_THAUMATURGY, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.MAGIC, 1).add(Aspect.EXCHANGE, 1), 6, -2, 2, new ItemStack(ModItems.focusDislocation)).setParents("FOCUSTRADE").setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), infusionPage(LibResearch.KEY_FOCUS_DISLOCATION));

		research = new TTResearchItem(LibResearch.KEY_CLEANSING_TALISMAN, LibResearch.CATEGORY_ARTIFICE, new AspectList().add(Aspect.HEAL, 2).add(Aspect.ORDER, 1).add(Aspect.POISON, 1), 2, 4, 3, new ItemStack(ModItems.cleansingTalisman)).setParents("ENCHFABRIC").setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_CLEANSING_TALISMAN));

		research = new TTResearchItem(LibResearch.KEY_BRIGHT_NITOR, LibResearch.CATEGORY_ALCHEMY, new AspectList().add(Aspect.LIGHT, 2).add(Aspect.FIRE, 1).add(Aspect.ENERGY, 1).add(Aspect.AIR, 1), 3, -4, 2, new ItemStack(ModItems.brightNitor)).setParents(LibResearch.KEY_GASEOUS_LIGHT).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_BRIGHT_NITOR));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_TELEKINESIS, LibResearch.CATEGORY_THAUMATURGY, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.MAGIC, 1).add(Aspect.MOTION, 1), 6, 0, 2, new ItemStack(ModItems.focusTelekinesis)).setParents(LibResearch.KEY_FOCUS_DISLOCATION).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_TELEKINESIS));

		research = new TTResearchItem(LibResearch.KEY_MAGNETS, LibResearch.CATEGORY_ARTIFICE, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.MOTION, 1).add(Aspect.SENSES, 1), -5, -3, 3, new ItemStack(ModBlocks.magnet)).setParentsHidden(LibResearch.KEY_FOCUS_TELEKINESIS).setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), arcaneRecipePage(LibResearch.KEY_MAGNET), arcaneRecipePage(LibResearch.KEY_MOB_MAGNET), cruciblePage(LibResearch.KEY_MAGNETS));

		research = new TTResearchItem(LibResearch.KEY_ENCHANTER, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.AURA, 1).add(Aspect.ELDRITCH, 1).add(Aspect.DARKNESS, 1).add(Aspect.MIND, 1), 2, -2, 5, new ItemStack(ModBlocks.enchanter)).setParents(LibResearch.KEY_SPELL_CLOTH).setParentsHidden("RESEARCHER2").setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), new ResearchPage("1"), new ResearchPage("2"), infusionPage(LibResearch.KEY_ENCHANTER));

		research = new TTResearchItem(LibResearch.KEY_XP_TALISMAN, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.GREED, 1).add(Aspect.MAGIC, 1).add(Aspect.MAN, 1), -2, 2, 2, new ItemStack(ModItems.xpTalisman, 1, 1)).setParents("JARBRAIN").setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_XP_TALISMAN));

		research = new TTResearchItem(LibResearch.KEY_FUNNEL, LibResearch.CATEGORY_ALCHEMY, new AspectList().add(Aspect.TOOL, 1).add(Aspect.TRAVEL, 2), 6, -3, 1, new ItemStack(ModBlocks.funnel)).setParents("DISTILESSENTIA").setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_FUNNEL));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_ASCENT_BOOST, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.AIR, 1).add(Aspect.MOTION, 1).add(Aspect.MAGIC, 2), -2, -3, 2, new ResourceLocation(LibResources.ENCHANT_ASCENT_BOOST)).setParents(LibResearch.KEY_ENCHANTER).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_SLOW_FALL, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.AIR, 1).add(Aspect.MOTION, 1).add(Aspect.MAGIC, 2), -1, -5, 2, new ResourceLocation(LibResources.ENCHANT_SLOW_FALL)).setParents(LibResearch.KEY_ENCHANTER).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_AUTO_SMELT, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.FIRE, 1).add(Aspect.ENTROPY, 1).add(Aspect.MAGIC, 2), 1, -6, 2, new ResourceLocation(LibResources.ENCHANT_AUTO_SMELT)).setParents(LibResearch.KEY_ENCHANTER).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_DESINTEGRATE, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.ENTROPY, 1).add(Aspect.VOID, 1).add(Aspect.MAGIC, 2), 3, -6, 2, new ResourceLocation(LibResources.ENCHANT_DESINTEGRATE)).setParents(LibResearch.KEY_ENCHANTER).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_QUICK_DRAW, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.SENSES, 1).add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 2), 5, -5, 2, new ResourceLocation(LibResources.ENCHANT_QUICK_DRAW)).setParents(LibResearch.KEY_ENCHANTER).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_ENCHANT_VAMPIRISM, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.HUNGER, 1).add(Aspect.WEAPON, 1).add(Aspect.MAGIC, 2), 6, -3, 2, new ResourceLocation(LibResources.ENCHANT_VAMPIRISM)).setParents(LibResearch.KEY_ENCHANTER).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_SMELT, LibResearch.CATEGORY_THAUMATURGY, new AspectList().add(Aspect.FIRE, 2).add(Aspect.ENERGY, 1).add(Aspect.MAGIC, 1), -1, -5, 2, new ItemStack(ModItems.focusSmelt)).setParents("FOCUSEXCAVATION").setParentsHidden("INFERNALFURNACE").setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_FOCUS_SMELT));

		research = new TTResearchItem(LibResearch.KEY_FOCUS_HEAL, LibResearch.CATEGORY_THAUMATURGY, new AspectList().add(Aspect.HEAL, 2).add(Aspect.SOUL, 1).add(Aspect.MAGIC, 1), 0, -7, 2, new ItemStack(ModItems.focusHeal)).setParentsHidden("FOCUSPORTABLEHOLE").setConcealed().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_HEAL));

		if(Config.allowMirrors) {
			research = new TTResearchItem(LibResearch.KEY_FOCUS_ENDER_CHEST, LibResearch.CATEGORY_THAUMATURGY, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.VOID, 1).add(Aspect.MAGIC, 1), 7, -4, 2, new ItemStack(ModItems.focusEnderChest)).setParents("FOCUSPORTABLEHOLE").setParentsHidden("MIRRORHAND").setConcealed().registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_FOCUS_ENDER_CHEST));

			research = new TTResearchItem(LibResearch.KEY_FOCUS_DEFLECT, LibResearch.CATEGORY_THAUMATURGY, new AspectList().add(Aspect.MOTION, 2).add(Aspect.AIR, 1).add(Aspect.ORDER, 1).add(Aspect.DEATH, 1), 2, -7, 3, new ItemStack(ModItems.focusDeflect)).setConcealed().setParentsHidden("MIRROR").setParents(LibResearch.KEY_FOCUS_FLIGHT).registerResearchItem();
			research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_FOCUS_DEFLECT));
			
			research = new TTResearchItem(LibResearch.KEY_DISLOCATOR, LibResearch.CATEGORY_ARTIFICE, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.MECHANISM, 1).add(Aspect.ELDRITCH, 1), 2, -5, 3, new ItemStack(ModBlocks.dislocator)).setConcealed().setParents(LibResearch.KEY_INTERFACE).setParentsHidden("MIRROR").registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_DISLOCATOR));
		}

		research = new TTResearchItem(LibResearch.KEY_BLOOD_SWORD, LibResearch.CATEGORY_ARTIFICE, new AspectList().add(Aspect.HUNGER, 2).add(Aspect.WEAPON, 1).add(Aspect.FLESH, 1).add(Aspect.SOUL, 1), -5, 8, 3, new ItemStack(ModItems.bloodSword)).setHidden().registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_BLOOD_SWORD)).setParentsHidden(LibResearch.KEY_FOCUS_HEAL, LibResearch.KEY_CLEANSING_TALISMAN).setParents("INFUSION");

		research = new TTResearchItem(LibResearch.KEY_REVEALING_HELM, LibResearch.CATEGORY_ARTIFICE, new AspectList().add(Aspect.AURA, 2).add(Aspect.ARMOR, 1), -2, -5, 1, new ItemStack(ModItems.revealingHelm)).setParents("GOGGLES").setParentsHidden("THAUMIUM").registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_REVEALING_HELM));
		
		research = new TTResearchItem(LibResearch.KEY_INFUSED_INKWELL, LibResearch.CATEGORY_BASICS, new AspectList().add(Aspect.VOID, 2).add(Aspect.DARKNESS, 1).add(Aspect.MIND, 1), 5, -1, 2, new ItemStack(ModItems.infusedInkwell)).setHidden().setParents("RESEARCH").registerResearchItem();
		research.setParentsHidden(LibResearch.KEY_INFUSED_INKWELL); // Prevent the research from being researched. TODO: Remove when azanor adds API hook
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_INFUSED_INKWELL), recipePage(LibResearch.KEY_INFUSED_INKWELL + 0));

		research = new TTResearchItem(LibResearch.KEY_REPAIRER, LibResearch.CATEGORY_ALCHEMY, new AspectList().add(Aspect.TOOL, 2).add(Aspect.CRAFT, 1).add(Aspect.ORDER, 1).add(Aspect.MAGIC, 1), 6, 2, 3, new ItemStack(ModBlocks.repairer)).setConcealed().setParents("TUBES").setParentsHidden("THAUMIUM", "ENCHFABRIC").registerResearchItem();
		research.setPages(new ResearchPage("0"), infusionPage(LibResearch.KEY_REPAIRER));
				
		// Peripheral documentation research
		if(Loader.isModLoaded("ComputerCraft")) {
			research = new TTResearchItem(LibResearch.KEY_PERIPHERALS, LibResearch.CATEGORY_BASICS, new AspectList(), 0, 2, 0, new ItemStack(Item.redstone)).setAutoUnlock().setRound().registerResearchItem();
			research.setPages(new ResearchPage("0"));
			
			research = new TTResearchItem(LibResearch.KEY_ASPECT_ANALYZER, LibResearch.CATEGORY_BASICS, new AspectList().add(Aspect.MECHANISM, 2).add(Aspect.SENSES, 1).add(Aspect.MIND, 1), 0, 4, 2, new ItemStack(ModBlocks.aspectAnalyzer)).setParents(LibResearch.KEY_PERIPHERALS).setParentsHidden("GOGGLES", "THAUMIUM").setConcealed().setRound().registerResearchItem();
			research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_ASPECT_ANALYZER));
		}
		
		// Move the Brain in a jar research
		ResearchCategories.researchCategories.get(LibResearch.CATEGORY_ARTIFICE).research.remove("JARBRAIN");

		research = new ResearchItem("JARBRAIN", LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.SENSES, 1).add(Aspect.MIND, 2).add(Aspect.UNDEAD, 2), -3, 0, 2, new ItemStack(ConfigBlocks.blockJar, 1, 1)).setParentsHidden("INFUSION").registerResearchItem();
		research.setPages(new ResearchPage("tc.research_page.JARBRAIN.1"), infusionPage("JarBrain"));

		// Move the Infusion Enchanting research
		ResearchCategories.researchCategories.get(LibResearch.CATEGORY_ARTIFICE).research.remove("INFUSIONENCHANTMENT");

		research = new ResearchItem("INFUSIONENCHANTMENT", LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.MIND, 1).add(Aspect.WEAPON, 1).add(Aspect.ARMOR, 1).add(Aspect.TOOL, 1), -4, -2, 5, new ResourceLocation("thaumcraft", "textures/misc/r_enchant.png"));
		research.setPages(new ResearchPage("tc.research_page.INFUSIONENCHANTMENT.1"), new ResearchPage("tc.research_page.INFUSIONENCHANTMENT.2"), new ResearchPage("tc.research_page.INFUSIONENCHANTMENT.3"), enchantPage("InfEnchRepair"), enchantPage("InfEnchHaste"), enchantPage("InfEnchPotency"), enchantPage("InfEnchFrugal"), enchantPage("InfEnchFortune"), enchantPage("InfEnch0"), enchantPage("InfEnch1"), enchantPage("InfEnch2"), enchantPage("InfEnch3"), enchantPage("InfEnch4"), enchantPage("InfEnch5"), enchantPage("InfEnch6"), enchantPage("InfEnch7"), enchantPage("InfEnch8"), enchantPage("InfEnch9"), enchantPage("InfEnch10"), enchantPage("InfEnch11"), enchantPage("InfEnch12"), enchantPage("InfEnch13"), enchantPage("InfEnch14"), enchantPage("InfEnch15"), enchantPage("InfEnch16"), enchantPage("InfEnch17"), enchantPage("InfEnch18"), enchantPage("InfEnch19"), enchantPage("InfEnch20"), enchantPage("InfEnch21")).setConcealed().setParents("JARBRAIN").registerResearchItem();
	}

	private static void registerResearchPages() {
		ResourceLocation background = new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png");

		ResearchCategories.registerCategory(LibResearch.CATEGORY_ENCHANTING, new ResourceLocation(LibResources.MISC_R_ENCHANTING), background);
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
}
