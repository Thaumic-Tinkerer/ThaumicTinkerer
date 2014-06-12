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
import vazkii.tinkerer.common.lib.LibResearch;

import java.util.Arrays;

public final class ResearchHelper {

	public static ResearchItem kamiResearch;

	public static void initResearch() {
		registerResearchPages();

		ResearchItem research;

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

		if (Config.allowMirrors) {
		}

		research = new TTResearchItem(LibResearch.KEY_MOBILIZER, new AspectList().add(Aspect.MOTION, 2).add(Aspect.ORDER, 2), -7, 5, 3, new ItemStack(ModBlocks.mobilizer)).setParents(LibResearch.KEY_MAGNETS);
		research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_MOBILIZER), ResearchHelper.arcaneRecipePage(LibResearch.KEY_RELAY)).setSecondary();

		// Peripheral documentation research
		if (Loader.isModLoaded("ComputerCraft")) {
			research = new TTResearchItem(LibResearch.KEY_PERIPHERALS, new AspectList(), -1, 0, 0, new ItemStack(Items.redstone)).setAutoUnlock().setRound();
			research.setPages(new ResearchPage("0"));
		}

		if (ConfigHandler.enableKami) {
			if (Config.allowMirrors) {
				research = new KamiResearchItem(LibResearch.KEY_WARP_GATE, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.ELDRITCH, 1).add(Aspect.FLIGHT, 1).add(Aspect.MECHANISM, 1), 19, 6, 5, new ItemStack(ModBlocks.warpGate)).setParents(LibResearch.KEY_ICHORCLOTH_CHEST_GEM).setParentsHidden(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM);
				research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_WARP_GATE), new ResearchPage("1"), ResearchHelper.infusionPage(LibResearch.KEY_SKY_PEARL));
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
