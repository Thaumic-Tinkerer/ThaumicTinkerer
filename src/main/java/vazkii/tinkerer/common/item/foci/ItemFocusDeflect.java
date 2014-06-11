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
 * File Created @ [Dec 8, 2013, 6:26:09 PM (GMT)]
 */
package vazkii.tinkerer.common.item.foci;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.helper.ProjectileHelper;
import vazkii.tinkerer.common.lib.LibItemNames;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;
import vazkii.tinkerer.common.research.ResearchHelper;
import vazkii.tinkerer.common.research.TTResearchItem;

import java.util.Arrays;
import java.util.List;

public class ItemFocusDeflect extends ItemModFocus {

	public static List<Class<?>> DeflectBlacklist = Arrays.asList(new Class<?>[]{ EntityExpBottle.class });
	AspectList visUsage = new AspectList().add(Aspect.ORDER, 8).add(Aspect.AIR, 4);

	@Override
	public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int ticks) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();

		if (wand.consumeAllVis(stack, p, getVisCost(), true, false))
			protectFromProjectiles(p);
	}

	public static void protectFromProjectiles(EntityPlayer p) {
		List<Entity> projectiles = p.worldObj.getEntitiesWithinAABB(IProjectile.class, AxisAlignedBB.getBoundingBox(p.posX - 4, p.posY - 4, p.posZ - 4, p.posX + 3, p.posY + 3, p.posZ + 3));

		for (Entity e : projectiles) {
			if (DeflectBlacklist.contains(e.getClass()) || ProjectileHelper.getOwner(e) == p)
				continue;
			Vector3 motionVec = new Vector3(e.motionX, e.motionY, e.motionZ).normalize().multiply(Math.sqrt((e.posX - p.posX) * (e.posX - p.posX) + (e.posY - p.posY) * (e.posY - p.posY) + (e.posZ - p.posZ) * (e.posZ - p.posZ)) * 2);

			for (int i = 0; i < 6; i++)
				ThaumicTinkerer.tcProxy.sparkle((float) e.posX, (float) e.posY, (float) e.posZ, 6);

			e.posX += motionVec.x;
			e.posY += motionVec.y;
			e.posZ += motionVec.z;
		}
	}

	@Override
	public String getSortingHelper(ItemStack paramItemStack) {
		return "DEFLECT";
	}

	@Override
	public boolean isVisCostPerTick() {
		return true;
	}

	@Override
	public boolean acceptsEnchant(int paramInt) {
		return paramInt == Config.enchFrugal.effectId;
	}

	@Override
	public int getFocusColor() {
		return 0xFFFFFF;
	}

	@Override
	public AspectList getVisCost() {
		return visUsage;
	}

	@Override
	public String getItemName() {
		return LibItemNames.FOCUS_DEFLECT;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		if (!Config.allowMirrors) {
			return null;
		}
		return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FOCUS_DEFLECT, new AspectList().add(Aspect.MOTION, 2).add(Aspect.AIR, 1).add(Aspect.ORDER, 1).add(Aspect.DEATH, 1), -4, -3, 3, new ItemStack(this)).setConcealed().setParents(LibResearch.KEY_FOCUS_SMELT)
				.setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_DEFLECT)).setSecondary();

	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_FOCUS_DEFLECT, new ItemStack(this), 5, new AspectList().add(Aspect.AIR, 15).add(Aspect.ARMOR, 5).add(Aspect.ORDER, 20), new ItemStack(ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemFocusFlight.class)),
				new ItemStack(ConfigItems.itemResource, 1, 10), new ItemStack(ConfigItems.itemResource, 1, 10), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 4));

	}
}
