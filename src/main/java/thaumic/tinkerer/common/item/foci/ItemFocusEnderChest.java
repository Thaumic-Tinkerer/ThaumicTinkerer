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
 * File Created @ [Nov 24, 2013, 2:49:01 PM (GMT)]
 */
package thaumic.tinkerer.common.item.foci;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.compat.EnderStorageFunctions;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.List;

public class ItemFocusEnderChest extends ItemModFocus {

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
	                           boolean par4) {
		super.addInformation(stack, player, list, par4);
		if (Loader.isModLoaded("EnderStorage")) {
			EnderStorageFunctions.addFocusInformation(stack, player, list, par4);
		}
	}

	public static final AspectList visUsage = new AspectList().add(Aspect.ENTROPY, 100).add(Aspect.ORDER, 100);

	@Override
	public ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer p, MovingObjectPosition pos) {
		if (Loader.isModLoaded("EnderStorage")) {
			return EnderStorageFunctions.onFocusRightClick(stack, world, p, pos);
		}
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();

		if (wand.consumeAllVis(stack, p, visUsage, true, false)) {
			p.displayGUIChest(p.getInventoryEnderChest());
			world.playSoundAtEntity(p, "mob.endermen.portal", 1F, 1F);
		}

		return stack;
	}



	@Override
	public int getFocusColor(ItemStack stack) {
		return 0x132223;
	}

	@Override
	protected boolean hasDepth() {
		return true;
	}

	@Override
	public String getSortingHelper(ItemStack paramItemStack) {
		if (Loader.isModLoaded("EnderStorage")) {
			return EnderStorageFunctions.getSortingHelper(paramItemStack);
		}
		return "ENDERCHEST";
	}

	@Override
	public AspectList getVisCost(ItemStack stack) {
		return visUsage;
	}

	@Override
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemStack, int i) {
		return new FocusUpgradeType[0];
	}

	@Override
	public String getItemName() {
		return LibItemNames.FOCUS_ENDER_CHEST;
	}

	@Override
	public IRegisterableResearch getResearchItem() {

		if (!Config.allowMirrors) {
			return null;
		}
        IRegisterableResearch research = (TTResearchItem) new TTResearchItem(LibResearch.KEY_FOCUS_ENDER_CHEST, new AspectList().add(Aspect.ELDRITCH, 2).add(Aspect.VOID, 1).add(Aspect.MAGIC, 1), -6, -2, 2, new ItemStack(this)).setWarp(1).setParents(LibResearch.KEY_FOCUS_DEFLECT).setConcealed();
        if (Loader.isModLoaded("EnderStorage")) {
			((TTResearchItem) research).setPages(new ResearchPage("ES"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_FOCUS_ENDER_CHEST));
		} else {
			((TTResearchItem) research).setPages(new ResearchPage("0"), ResearchHelper.arcaneRecipePage(LibResearch.KEY_FOCUS_ENDER_CHEST));
		}
		return research;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		if (Config.allowMirrors) {
			return new ThaumicTinkererArcaneRecipe(LibResearch.KEY_FOCUS_ENDER_CHEST, LibResearch.KEY_FOCUS_ENDER_CHEST, new ItemStack(this), new AspectList().add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10),
					"M", "E", "P",
					'M', new ItemStack(ConfigBlocks.blockMirror),
					'E', new ItemStack(Items.ender_eye),
					'P', new ItemStack(ConfigItems.itemFocusPortableHole));
		}
		return null;
	}
}
