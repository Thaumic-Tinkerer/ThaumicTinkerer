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
package vazkii.tinkerer.common.item.kami.foci;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.item.foci.ItemModFocus;
import vazkii.tinkerer.common.item.kami.ItemSkyPearl;

public class ItemFocusRecall extends ItemModFocus {

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

}
