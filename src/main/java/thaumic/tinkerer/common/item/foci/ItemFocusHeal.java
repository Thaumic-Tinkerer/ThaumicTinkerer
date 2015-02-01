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
 * File Created @ [26 Oct 2013, 12:04:52 (GMT)]
 */
package thaumic.tinkerer.common.item.foci;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.ResearchHelper;
import thaumic.tinkerer.common.research.TTResearchItem;

import java.util.HashMap;
import java.util.Map;

public class ItemFocusHeal extends ItemModFocus {

    private static final AspectList visUsage = new AspectList().add(Aspect.EARTH, 45).add(Aspect.WATER, 45);

    public static Map<String, Integer> playerHealData = new HashMap();

    public ItemFocusHeal() {
        super();
    }

    @Override
    public void onUsingFocusTick(ItemStack stack, EntityPlayer p, int time) {
        ItemWandCasting wand = (ItemWandCasting) stack.getItem();
        if (!wand.consumeAllVis(stack, p, visUsage, false, false) || !p.shouldHeal())
            return;

        int potency = wand.getFocusPotency(stack);

        if (!playerHealData.containsKey(p.getGameProfile().getName()))
            playerHealData.put(p.getGameProfile().getName(), 0);

        int progress = playerHealData.get(p.getGameProfile().getName()) + 1;
        playerHealData.put(p.getGameProfile().getName(), progress);

        ThaumicTinkerer.tcProxy.sparkle((float) p.posX + p.worldObj.rand.nextFloat() - 0.5F, (float) p.posY + p.worldObj.rand.nextFloat(), (float) p.posZ + p.worldObj.rand.nextFloat() - 0.5F, 0);

        if (progress >= 30 - potency * 10 / 3) {
            playerHealData.put(p.getGameProfile().getName(), 0);

            wand.consumeAllVis(stack, p, visUsage, true, false);
            p.heal(1);
            p.worldObj.playSoundAtEntity(p, "thaumcraft:wand", 0.5F, 1F);
        }
    }

    @Override
    public void onPlayerStoppedUsingFocus(ItemStack paramItemStack, World paramWorld, EntityPlayer paramEntityPlayer, int paramInt) {
        playerHealData.put(paramEntityPlayer.getGameProfile().getName(), 0);
    }

    @Override
    protected boolean hasDepth() {
        return true;
    }

    @Override
    public boolean isVisCostPerTick(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isUseItem(ItemStack stack) {
        return true;
    }

    @Override
    public String getSortingHelper(ItemStack paramItemStack) {
        return "HEAL";
    }

    @Override
    public int getFocusColor(ItemStack stack) {
        return 0xFD00D6;
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
        return LibItemNames.FOCUS_HEAL;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if (!Config.allowMirrors) {
            return null;
        }
        return (TTResearchItem) new TTResearchItem(LibResearch.KEY_FOCUS_HEAL, new AspectList().add(Aspect.HEAL, 2).add(Aspect.SOUL, 1).add(Aspect.MAGIC, 1), -6, -4, 2, new ItemStack(this)).setParents(LibResearch.KEY_FOCUS_DEFLECT).setConcealed()
                .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_FOCUS_HEAL)).setSecondary();

    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_FOCUS_HEAL, new ItemStack(this), 4, new AspectList().add(Aspect.HEAL, 10).add(Aspect.SOUL, 10).add(Aspect.LIFE, 15), new ItemStack(ConfigItems.itemFocusPech),
                new ItemStack(Items.golden_carrot), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget));

    }
}
