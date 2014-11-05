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
 * File Created @ [Dec 21, 2013, 7:34:11 PM (GMT)]
 */
package thaumic.tinkerer.common.item.kami;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.proxy.TTCommonProxy;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.*;
import thaumic.tinkerer.common.research.*;

import java.util.List;

public class ItemKamiResource extends ItemKamiBase {

	final int subtypes = 8;
	IIcon[] icons;

	public ItemKamiResource() {
		super();
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < subtypes; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[subtypes];

		for (int i = 0; i < icons.length; i++)
			icons[i] = IconHelper.forName(par1IconRegister, LibItemNames.KAMI_RESOURCE_NAMES[i]);
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return icons[Math.min(subtypes - 1, par1)];
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() >= subtypes ? super.getUnlocalizedName(par1ItemStack) : "item." + LibItemNames.KAMI_RESOURCE_NAMES[par1ItemStack.getItemDamage()];
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() != 7 && par1ItemStack.getItemDamage() != 6 ? TTCommonProxy.kamiRarity : super.getRarity(par1ItemStack);
	}

	@Override
	public String getItemName() {
		return LibItemNames.KAMI_RESOURCE;
	}

	@Override
	public IRegisterableResearch getResearchItem() {

		TTResearchItem research;
		TTResearchItemMulti researchItemMulti = new TTResearchItemMulti();

		research = (TTResearchItem) new KamiResearchItem(LibResearch.KEY_DIMENSION_SHARDS, new AspectList(), 7, 8, 0, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 7)).setStub().setAutoUnlock().setRound();
		research.setPages(new ResearchPage("0"));
		researchItemMulti.addResearch(research);

        research = new KamiResearchItem(LibResearch.KEY_ICHOR, new AspectList().add(Aspect.MAN, 1).add(Aspect.LIGHT, 2).add(Aspect.SOUL, 1).add(Aspect.TAINT, 1), 9, 8, 5, new ItemStack(this, 1, 0)).setWarp(5);
        research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR));
		ResearchHelper.kamiResearch = research;

		researchItemMulti.addResearch(research);

		research = (TTResearchItem) new KamiResearchItem(LibResearch.KEY_ICHOR_CLOTH, new AspectList().add(Aspect.CLOTH, 2).add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1).add(Aspect.SENSES, 1), 11, 7, 5, new ItemStack(this, 1, 1)).setConcealed().setParents(LibResearch.KEY_ICHOR);
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHOR_CLOTH));
		researchItemMulti.addResearch(research);

		research = (TTResearchItem) new KamiResearchItem(LibResearch.KEY_ICHORIUM, new AspectList().add(Aspect.METAL, 2).add(Aspect.LIGHT, 1).add(Aspect.CRAFT, 1).add(Aspect.TOOL, 1), 11, 9, 5, new ItemStack(this, 1, 2)).setConcealed().setParents(LibResearch.KEY_ICHOR).setParentsHidden(LibResearch.KEY_ICHOR_CLOTH);
		research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHORIUM));
		researchItemMulti.addResearch(research);

        research = (TTResearchItem) new KamiResearchItem(LibResearch.KEY_ICHOR_CAP, new AspectList().add(Aspect.TOOL, 2).add(Aspect.METAL, 1).add(Aspect.LIGHT, 1).add(Aspect.MAGIC, 1), 11, 11, 5, new ItemStack(this, 1, 4)).setWarp(2).setConcealed().setParents(LibResearch.KEY_ICHORIUM);
        research.setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_ICHOR_CAP));
		researchItemMulti.addResearch(research);

        research = (TTResearchItem) new KamiResearchItem(LibResearch.KEY_ICHORCLOTH_ROD, new AspectList().add(Aspect.TOOL, 2).add(Aspect.CLOTH, 1).add(Aspect.LIGHT, 1).add(Aspect.MAGIC, 1), 14, 2, 5, new ItemStack(this, 1, 5)).setWarp(1).setConcealed().setParents(LibResearch.KEY_ICHOR_CLOTH).setParentsHidden(LibResearch.KEY_ICHOR_CAP);
        research.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHORCLOTH_ROD));
		researchItemMulti.addResearch(research);

		return researchItemMulti;

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		GameRegistry.addShapelessRecipe(new ItemStack(this, 9, 3), new ItemStack(this, 1, 2));
		return new ThaumicTinkererRecipeMulti(
				new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ICHOR_CLOTH, LibResearch.KEY_ICHOR_CLOTH, new ItemStack(this, 3, 1), new AspectList().add(Aspect.FIRE, 125).add(Aspect.EARTH, 125).add(Aspect.WATER, 125).add(Aspect.AIR, 125).add(Aspect.ORDER, 125).add(Aspect.ENTROPY, 125),
						"CCC", "III", "DDD",
						'C', new ItemStack(ConfigItems.itemResource, 1, 7),
						'I', new ItemStack(this, 1, 0),
						'D', new ItemStack(Items.diamond)),
				new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ICHORIUM, LibResearch.KEY_ICHORIUM, new ItemStack(this, 1, 2), new AspectList().add(Aspect.FIRE, 100).add(Aspect.EARTH, 100).add(Aspect.WATER, 100).add(Aspect.AIR, 100).add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100),
						" T ", "IDI", " I ",
						'T', new ItemStack(ConfigItems.itemResource, 1, 2),
						'I', new ItemStack(this, 1, 0),
						'D', new ItemStack(Items.diamond)),
				new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ICHOR_CAP, LibResearch.KEY_ICHOR_CAP, new ItemStack(this, 2, 4), new AspectList().add(Aspect.FIRE, 100).add(Aspect.EARTH, 100).add(Aspect.WATER, 100).add(Aspect.AIR, 100).add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100),
						"ICI", " M ", "ICI",
						'M', new ItemStack(this, 1, 2),
						'I', new ItemStack(this, 1, 0),
						'C', new ItemStack(ConfigItems.itemWandCap, 1, 2)),

				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_ICHOR, new ItemStack(this, 8, 0), 7, new AspectList().add(Aspect.MAN, 32).add(Aspect.LIGHT, 32).add(Aspect.SOUL, 64), new ItemStack(Items.nether_star),
						new ItemStack(Items.diamond), new ItemStack(this, 8, 7), new ItemStack(Items.ender_eye), new ItemStack(this, 8, 6)),
				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_ICHORCLOTH_ROD, new ItemStack(this, 1, 5), 9, new AspectList().add(Aspect.MAGIC, 100).add(Aspect.LIGHT, 32).add(Aspect.TOOL, 32), new ItemStack(ConfigItems.itemWandRod, 1, 2),
						new ItemStack(this), new ItemStack(this, 1, 1), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Items.ghast_tear), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(this, 1, 1))
		);
	}
}
