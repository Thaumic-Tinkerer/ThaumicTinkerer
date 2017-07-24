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
package thaumic.tinkerer.common.item.foci;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.compat.BloodMagic;
import thaumic.tinkerer.common.compat.BotaniaFunctions;
import thaumic.tinkerer.common.core.helper.ProjectileHelper;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemFocusDeflect extends ItemModFocus {

    public static List<Class<?>> DeflectBlacklist = new ArrayList<Class<?>>();
    AspectList visUsage = new AspectList().add(Aspect.ORDER, 8).add(Aspect.AIR, 4);


    public static void  setupBlackList()
    {
        DeflectBlacklist.add(EntityExpBottle.class);
        if(Loader.isModLoaded("BloodMagic"))
        {
            BloodMagic.setupClass();
        }

    }
    public static void protectFromProjectiles(EntityPlayer p) {
        List<Entity> projectiles = p.worldObj.getEntitiesWithinAABB(IProjectile.class, AxisAlignedBB.getBoundingBox(p.posX - 4, p.posY - 4, p.posZ - 4, p.posX + 3, p.posY + 3, p.posZ + 3));

	// only deflect projectiles if the player is within a set angle in front of their trajectory
	// this angle will scale inversely with distance - narrow angles at long range, wider angles at close range
	// we define an exclusion zone around the player as 1.5x the maximum radius (core to corner)
	// and from this exclusion zone, we derive the distance-dependent angle
	
	// due to things like Morph, we'll need to calculate the exclusion zone instead of using a static value
	// calculation is done outside of the projectile checking loop to avoid repeated computations of the same value
	double safe_exclusion_zone = 1.5F * Math.sqrt((p.height * p.height * 0.25F) + (p.width * p.width * 0.25F));
	double safe_exclusion_zone_sq = safe_exclusion_zone * safe_exclusion_zone;

        for (Entity e : projectiles) {
            if (CheckBlackList(e) || ProjectileHelper.getOwner(e) == p)
                continue;

	    Vector3 entPos = new Vector3(p.posX, p.posY + p.height * 0.5F, p.posZ);
	    Vector3 projPos = new Vector3(e.posX, e.posY, e.posZ);
	    Vector3 projVel = new Vector3(e.motionX, e.motionY, e.motionZ);
	    
	    Vector3 separation = entPos.subtract(projPos);
	    double distance = separation.mag();
	    double cosine = separation.normalize().dotProduct(projVel.normalize());
	    
	    double comparison = distance / Math.sqrt(distance * distance + safe_exclusion_zone_sq);
	    
	    // 0.707 = sqrt(2) = cos(45 degrees) - the detection angle won't go any higher than this
	    // the angle cap is what prevents your own (outgoing) projectiles from being affected
	    // for incoming projectiles already in flight, this is not an issue
	    // but anything which is actively fired from inside the exclusion zone will hit (tested with arrows in a dispenser)
	    // I don't consider this a problem, because you are now well within melee range
	    if(comparison < 0.707) {
	        comparison = 0.707;
	    }
	    
	    // if the target is not within a cone of semi-angle acos(comparison) from the projectile's velocity vector
	    // then we don't consider it a threat, hence we won't deflect it
	    if(cosine < comparison) {
	        continue;
	    }

            Vector3 motionVec = new Vector3(e.motionX, e.motionY, e.motionZ).normalize().multiply(Math.sqrt((e.posX - p.posX) * (e.posX - p.posX) + (e.posY - p.posY) * (e.posY - p.posY) + (e.posZ - p.posZ) * (e.posZ - p.posZ)) * 2);

            for (int i = 0; i < 6; i++)
                ThaumicTinkerer.tcProxy.sparkle((float) e.posX, (float) e.posY, (float) e.posZ, 6);

            e.posX += motionVec.x;
            e.posY += motionVec.y;
            e.posZ += motionVec.z;
        }
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank) {
        return new FocusUpgradeType[]{FocusUpgradeType.frugal};
    }

    private static boolean CheckBlackList(Entity entity) {
        Class<? extends Entity> aClass=entity.getClass();
        if(DeflectBlacklist.contains(aClass))
            return true;
        if (Loader.isModLoaded("Botania"))
        {
            return BotaniaFunctions.isEntityHarmless(entity);
        }
        for(Class<?> testClass:DeflectBlacklist)
        {
            if(testClass.isInterface())
                if(testClass.isAssignableFrom(aClass))
                    return true;
        }
        return false;
    }

    @Override
    public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int ticks) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();

        if (wand.consumeAllVis(stack, p, getVisCost(stack), true, false))
            protectFromProjectiles(p);
    }

    @Override
    public String getSortingHelper(ItemStack paramItemStack) {
        return "DEFLECT";
    }

    @Override
    public boolean isVisCostPerTick(ItemStack stack) {
        return true;
    }


    @Override
    public int getFocusColor(ItemStack stack) {
        return 0xFFFFFF;
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        float modifier=this.getUpgradeLevel(stack,FocusUpgradeType.frugal)*0.10f;
        AspectList cost=visUsage.copy();
        for(Aspect a:visUsage.getAspects())
        {
            cost.remove(a, (int) Math.round(cost.getAmount(a)*modifier));
        }
        return cost;
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
        return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_FOCUS_DEFLECT, new ItemStack(this), 5, new AspectList().add(Aspect.AIR, 15).add(Aspect.ARMOR, 5).add(Aspect.ORDER, 20), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemFocusFlight.class)),
                new ItemStack(ConfigItems.itemResource, 1, 10), new ItemStack(ConfigItems.itemResource, 1, 10), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 4));

    }
}
