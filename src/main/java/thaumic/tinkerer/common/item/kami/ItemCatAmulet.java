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
 * File Created @ [Dec 27, 2013, 7:41:50 PM (GMT)]
 */
package thaumic.tinkerer.common.item.kami;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.proxy.TTCommonProxy;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ItemKamiBase;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemCatAmulet extends ItemKamiBase implements IBauble {

    public ItemCatAmulet() {
        super();
        setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity e, int par4, boolean par5) {

    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return TTCommonProxy.kamiRarity;
    }

    private boolean messWithRunAwayAI(EntityAIAvoidEntity aiEntry) {
        if (aiEntry.targetEntityClass == EntityOcelot.class) {
            aiEntry.targetEntityClass = EntityPlayer.class;
            return true;
        }
        return false;
    }

    private void messWithGetTargetAI(EntityAINearestAttackableTarget aiEntry) {
        if (aiEntry.targetClass == EntityPlayer.class)
            aiEntry.targetClass = EntityEnderCrystal.class; // Something random that won't be around
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        int range = 10;
        int rangeY = 4;
        List<EntityLiving> entities = player.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(player.posX - range, player.posY - rangeY, player.posZ - range, player.posX + range, player.posY + rangeY, player.posZ + range));

        for (EntityLiving entity : entities) {
            List<EntityAITasks.EntityAITaskEntry> entries = new ArrayList(entity.tasks.taskEntries);
            entries.addAll(new ArrayList(entity.targetTasks.taskEntries));

            boolean avoidsOcelots = false;
            for (EntityAITasks.EntityAITaskEntry entry : entries) {
                if (entry.action instanceof EntityAIAvoidEntity)
                    avoidsOcelots = messWithRunAwayAI((EntityAIAvoidEntity) entry.action) || avoidsOcelots;

                if (entry.action instanceof EntityAINearestAttackableTarget)
                    messWithGetTargetAI((EntityAINearestAttackableTarget) entry.action);
            }

            if (entity instanceof EntityCreeper) {
                ((EntityCreeper) entity).timeSinceIgnited = 2;
                entity.setAttackTarget(null);
            }
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public String getItemName() {
        return LibItemNames.CAT_AMULET;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        if(!ConfigHandler.enableKami)
            return null;
        return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_CAT_AMULET, new AspectList().add(Aspect.MIND, 2).add(Aspect.ORDER, 1).add(Aspect.DARKNESS, 1).add(Aspect.DEATH, 1), 13, 10, 5, new ItemStack(this)).setParents(LibResearch.KEY_ICHORIUM)
                .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_CAT_AMULET));

    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_CAT_AMULET, new ItemStack(this), 8, new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.ORDER, 32).add(Aspect.MIND, 16), new ItemStack(Blocks.quartz_block),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), new ItemStack(Items.dye, 1, 3), new ItemStack(Blocks.leaves, 1, 3), new ItemStack(Items.fish));

    }
}
