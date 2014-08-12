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
 * File Created @ [Jan 10, 2014, 9:20:05 PM (GMT)]
 */
package thaumic.tinkerer.common.item.kami.foci;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.kami.TileWarpGate;
import thaumic.tinkerer.common.item.foci.ItemModFocus;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.item.kami.ItemSkyPearl;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

public class ItemFocusRecall extends ItemModKamiFocus{

	AspectList cost = new AspectList().add(Aspect.AIR, 4000).add(Aspect.EARTH, 4000).add(Aspect.ORDER, 4000);

	public ItemFocusRecall() {
		super();
	}

	@Override
	protected boolean hasDepth() {
		return true;
	}

	@Override
	public boolean isUseItem() {
		return true;
	}

	@Override
	public void onUsingFocusTick(ItemStack paramItemStack, EntityPlayer paramEntityPlayer, int paramInt) {
		ItemWandCasting wand = (ItemWandCasting) paramItemStack.getItem();

		if (Integer.MAX_VALUE - paramInt > 60) {
			ItemStack stackToCount = null;
			for (int i = 0; i < 9; i++) {
				ItemStack stackInSlot = paramEntityPlayer.inventory.getStackInSlot(i);
				if (stackInSlot != null && stackInSlot.getItem() instanceof ItemSkyPearl && ItemSkyPearl.isAttuned(stackInSlot)) {
					stackToCount = stackInSlot;
					break;
				}
			}

			if (stackToCount != null) {
				int dim = ItemSkyPearl.getDim(stackToCount);
				if (dim == paramEntityPlayer.dimension) {
					int x = ItemSkyPearl.getX(stackToCount);
					int y = ItemSkyPearl.getY(stackToCount);
					int z = ItemSkyPearl.getZ(stackToCount);

					if (wand.consumeAllVis(paramItemStack, paramEntityPlayer, getVisCost(), false, false) && TileWarpGate.teleportPlayer(paramEntityPlayer, new ChunkCoordinates(x, y, z)))
						wand.consumeAllVis(paramItemStack, paramEntityPlayer, getVisCost(), true, false);
				}
			}

			paramEntityPlayer.clearItemInUse();
		}
	}

	@Override
	public int getFocusColor() {
		return 0x9CF8FF;
	}

	@Override
	public AspectList getVisCost() {
		return cost;
	}

	@Override
	public String getSortingHelper(ItemStack paramItemStack) {
		return "RECALL";
	}

	@Override
	public String getItemName() {
		return LibItemNames.FOCUS_RECALL;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		if (!Config.allowMirrors) {
			return null;
		}
		return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_FOCUS_RECALL, new AspectList().add(Aspect.TRAVEL, 2).add(Aspect.ELDRITCH, 1).add(Aspect.FLIGHT, 1).add(Aspect.MAGIC, 1), 20, 8, 5, new ItemStack(this)).setParents(LibResearch.KEY_WARP_GATE).setParentsHidden(LibResearch.KEY_ICHORCLOTH_ROD)
				.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_RECALL));
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_FOCUS_RECALL, new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusRecall.class)), 10, new AspectList().add(Aspect.TRAVEL, 100).add(Aspect.ELDRITCH, 64).add(Aspect.MAGIC, 50), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemSkyPearl.class)),
				new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(Items.ender_pearl), new ItemStack(Items.diamond), new ItemStack(ConfigBlocks.blockMirror), new ItemStack(ConfigItems.itemFocusPortableHole));

	}
}
