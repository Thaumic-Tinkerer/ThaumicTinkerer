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

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.RecipeCrucible;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigResearch;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.lib.LibResearch;

public final class ModResearch {

	public static void initResearch() {
		ThaumicTinkerer.proxy.registerResearchPages();

		ResearchItem research;

		research = new TTResearchItem(LibResearch.KEY_DARK_QUARTZ, LibResearch.CATEGORY_ARTIFICE, new AspectList(), -2, -1, 0, new ItemStack(ModItems.darkQuartz)).setStub().setAutoUnlock().setRound().registerResearchItem();
		research.setPages(new ResearchPage("0"), recipePage(LibResearch.KEY_DARK_QUARTZ + 0), recipePage(LibResearch.KEY_DARK_QUARTZ + 1), recipePage(LibResearch.KEY_DARK_QUARTZ + 2), recipePage(LibResearch.KEY_DARK_QUARTZ + 3), recipePage(LibResearch.KEY_DARK_QUARTZ + 4), recipePage(LibResearch.KEY_DARK_QUARTZ + 5));

		research = new TTResearchItem(LibResearch.KEY_INTERFACE, LibResearch.CATEGORY_ARTIFICE, new AspectList().add(Aspect.ENTROPY, 4).add(Aspect.ORDER, 4), 3, -3, 1, new ItemStack(ModBlocks.interfase)).registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_INTERFACE), new ResearchPage("1"), arcaneRecipePage(LibResearch.KEY_CONNECTOR));

		research = new TTResearchItem(LibResearch.KEY_GASEOUS_LIGHT, LibResearch.CATEGORY_ALCHEMY, new AspectList().add(Aspect.LIGHT, 2).add(Aspect.AIR, 1), 4, -2, 2, new ItemStack(ModItems.gaseousLight)).setParents("NITOR").registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_GASEOUS_LIGHT));

		research = new TTResearchItem(LibResearch.KEY_GASEOUS_SHADOW, LibResearch.CATEGORY_ALCHEMY, new AspectList().add(Aspect.DARKNESS, 2).add(Aspect.AIR, 1).add(Aspect.MOTION, 4), 4, 0, 2, new ItemStack(ModItems.gaseousShadow)).setParents("ALUMENTUM").setParentsHidden(LibResearch.KEY_GASEOUS_LIGHT).setSiblings(LibResearch.KEY_GAS_REMOVER).registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_GASEOUS_SHADOW));

		research = new TTResearchItem(LibResearch.KEY_GAS_REMOVER, LibResearch.CATEGORY_ALCHEMY, new AspectList(), 5, 0, 0, new ItemStack(ModItems.gasRemover)).setRound().registerResearchItem();
		research.setPages(new ResearchPage("0"), arcaneRecipePage(LibResearch.KEY_GAS_REMOVER));

		research = new TTResearchItem(LibResearch.KEY_SPELL_CLOTH, LibResearch.CATEGORY_ENCHANTING, new AspectList().add(Aspect.MAGIC, 2).add(Aspect.CLOTH, 1), 0, 0, 2, new ItemStack(ModItems.spellCloth)).setParentsHidden("ENCHFABRIC").registerResearchItem();
		research.setPages(new ResearchPage("0"), cruciblePage(LibResearch.KEY_SPELL_CLOTH));
	}

	private static ResearchPage recipePage(String name) {
		return new ResearchPage((IRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage arcaneRecipePage(String name) {
		return new ResearchPage((IArcaneRecipe) ConfigResearch.recipes.get(name));
	}

	private static ResearchPage cruciblePage(String name) {
		return new ResearchPage((RecipeCrucible) ConfigResearch.recipes.get(name));
	}
}
