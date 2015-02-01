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
 * File Created @ [9 Sep 2013, 19:27:13 (GMT)]
 */
package thaumic.tinkerer.common.item.foci;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

public class ItemFocusFlight extends ItemModFocus {

    private static final AspectList visUsage = new AspectList().add(Aspect.AIR, 15);

    @Override
    public ItemStack onFocusRightClick(ItemStack itemstack, World world, EntityPlayer p, MovingObjectPosition movingobjectposition) {
        ItemWandCasting wand = (ItemWandCasting) itemstack.getItem();
        int potency = wand.getFocusPotency(itemstack);
		
        if (!ConfigHandler.enableFlight) {
            return itemstack;
        }
        if (wand.consumeAllVis(itemstack, p, getVisCost(itemstack), true, false)) {
            Vec3 vec = p.getLookVec();
            double force = 1 / 1.5 * (1 + potency * 0.2);
            p.motionX = vec.xCoord * force;
            p.motionY = vec.yCoord * force;
            p.motionZ = vec.zCoord * force;
            p.fallDistance = 0F;
            if (p instanceof EntityPlayerMP) {
                ((EntityPlayerMP) p).playerNetServerHandler.floatingTickCount = 0;
            }
            for (int i = 0; i < 5; i++)
                ThaumicTinkerer.tcProxy.smokeSpiral(world, p.posX, p.posY - p.motionY, p.posZ, 2F, (int) (Math.random() * 360), (int) p.posY, 0x9E2FF);
            world.playSoundAtEntity(p, "thaumcraft:wind", 0.4F, 1F);
        }

        if (world.isRemote)
            p.swingItem();

        return itemstack;
    }

    @Override
    public String getSortingHelper(ItemStack itemstack) {
        return "FLIGHT";
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0x9EF2FF;
    }

    @Override
    protected boolean hasOrnament() {
        return true;
    }

    @Override
    public AspectList getVisCost(ItemStack stack) {
        return visUsage;
    }

    @Override
    public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack itemStack, int i) {
        return new FocusUpgradeType[]{FocusUpgradeType.treasure, FocusUpgradeType.potency};
    }

    @Override
    public String getItemName() {
        return LibItemNames.FOCUS_FLIGHT;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FOCUS_FLIGHT, new AspectList().add(Aspect.MOTION, 1).add(Aspect.MAGIC, 1).add(Aspect.AIR, 2), -3, -4, 2, new ItemStack(this)).setParents(LibResearch.KEY_FOCUS_SMELT).setConcealed()
                .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_FLIGHT));

    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return
                new ThaumicTinkererInfusionRecipe(LibResearch.KEY_FOCUS_FLIGHT, new ItemStack(this), 3, new AspectList().add(Aspect.AIR, 15).add(Aspect.MOTION, 20).add(Aspect.TRAVEL, 10), new ItemStack(Items.ender_pearl),
                        new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.feather), new ItemStack(Items.feather), new ItemStack(ConfigItems.itemShard, 1, 0));
    }
}