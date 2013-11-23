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
 * File Created @ [11 Sep 2013, 23:21:21 (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.codechicken.core.vec.Vector3;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.helper.MiscHelper;

public class ItemFocusTelekinesis extends ItemModFocus {

	private static final AspectList visUsage = new AspectList().add(Aspect.AIR, 5).add(Aspect.ENTROPY, 5);

	public ItemFocusTelekinesis(int par1) {
		super(par1);
	}

	@Override
	public void onUsingFocusTick(ItemStack stack, EntityPlayer player, int ticks) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();

		Vector3 target = Vector3.fromEntityCenter(player);

		final int range = 6 + EnchantmentHelper.getEnchantmentLevel(Config.enchPotency.effectId, wand.getFocusItem(stack));
		final double distance = range - 1;
		if(!player.isSneaking())
			target.add(new Vector3(player.getLookVec()).multiply(distance));

		target.y += 0.5;

		List<EntityItem> entities = player.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(target.x - range, target.y - range, target.z - range, target.x + range, target.y + range, target.z + range));

		if(!entities.isEmpty() && wand.consumeAllVis(stack, player, getVisCost(), true)) {
			for(EntityItem item : entities) {
				MiscHelper.setEntityMotionFromVector(item, target, 0.3333F);
				ThaumicTinkerer.tcProxy.sparkle((float) item.posX, (float) item.posY, (float) item.posZ, 0);
			}
		}
	}

	@Override
	public String getSortingHelper(ItemStack itemstack) {
		return "TELEKINESIS";
	}

	@Override
	boolean hasOrnament() {
		return true;
	}

	@Override
	public int getFocusColor() {
		return 0x9C00BE;
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}

	@Override
	public boolean isVisCostPerTick() {
		return true;
	}

}
