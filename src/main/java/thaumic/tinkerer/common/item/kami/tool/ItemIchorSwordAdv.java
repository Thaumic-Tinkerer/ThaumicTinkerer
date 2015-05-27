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
 * File Created @ [Dec 29, 2013, 9:08:14 PM (GMT)]
 */
package thaumic.tinkerer.common.item.kami.tool;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import thaumic.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import thaumic.tinkerer.client.core.helper.IconHelper;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.core.handler.kami.SoulHeartHandler;
import thaumic.tinkerer.common.item.kami.ItemKamiResource;
import thaumic.tinkerer.common.lib.LibItemNames;
import thaumic.tinkerer.common.lib.LibResearch;
import thaumic.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;
import thaumic.tinkerer.common.registry.ThaumicTinkererRecipe;
import thaumic.tinkerer.common.research.IRegisterableResearch;
import thaumic.tinkerer.common.research.KamiResearchItem;
import thaumic.tinkerer.common.research.ResearchHelper;

import java.util.List;

public class ItemIchorSwordAdv extends ItemIchorSword implements IAdvancedTool {

    IIcon[] specialIcons = new IIcon[3];
    boolean ignoreLeftClick = false;

    public ItemIchorSwordAdv() {
        super();
        setHasSubtypes(true);
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        for (int i = 0; i < specialIcons.length; i++)
            specialIcons[i] = IconHelper.forItem(par1IconRegister, this, i);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par3EntityPlayer.isSneaking()) {
            ToolHandler.changeMode(par1ItemStack);
            ToolModeHUDHandler.setTooltip(ToolHandler.getToolModeStr(this, par1ItemStack));

            return par1ItemStack;
        } else return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (!ignoreLeftClick && entity instanceof EntityLivingBase && ((EntityLivingBase) entity).hurtTime == 0 && !((EntityLivingBase) entity).isDead)
            switch (ToolHandler.getMode(stack)) {
                case 0:
                    break;
                case 1: {
                    int range = 3;
                    List<Entity> entities = player.worldObj.getEntitiesWithinAABB(entity.getClass(), AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range));
                    ignoreLeftClick = true;
                    for (Entity entity_ : entities)
                        player.attackTargetEntityWithCurrentItem(entity_);
                    ignoreLeftClick = false;

                    break;
                }
                case 2: {
                    EntityLivingBase living = (EntityLivingBase) entity;
                    PotionEffect effect = new PotionEffect(Potion.resistance.id, 1, 1);
                    living.addPotionEffect(effect);

                    SoulHeartHandler.addHearts(player);

                    break;
                }
            }

        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return par1 >= specialIcons.length ? super.getIconFromDamage(par1) : specialIcons[par1];
    }

    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(ToolHandler.getToolModeStr(this, par1ItemStack));
    }

    @Override
    public String getType() {
        return "sword";
    }

    @Override
    public String getItemName() {
        return LibItemNames.ICHOR_SWORD_GEM;
    }

    @Override
    public IRegisterableResearch getResearchItem() {
        return (IRegisterableResearch) new KamiResearchItem(LibResearch.KEY_ICHOR_SWORD_GEM, new AspectList().add(Aspect.AIR, 2).add(Aspect.WEAPON, 1).add(Aspect.SOUL, 1).add(Aspect.HUNGER, 1), 16, 12, 5, new ItemStack(this)).setParents(LibResearch.KEY_ICHOR_TOOLS)
                .setPages(new ResearchPage("0"), ResearchHelper.infusionPage(LibResearch.KEY_ICHOR_SWORD_GEM), new ResearchPage("1"));

    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new ThaumicTinkererInfusionRecipe(LibResearch.KEY_ICHOR_SWORD_GEM, new ItemStack(this), 15, new AspectList().add(Aspect.AIR, 50).add(Aspect.HUNGER, 64).add(Aspect.SOUL, 32).add(Aspect.WEAPON, 32).add(Aspect.ENERGY, 32).add(Aspect.ORDER, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemIchorSword.class)),
                new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 2), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ConfigItems.itemSwordElemental), new ItemStack(ConfigItems.itemFocusFrost), new ItemStack(Blocks.cactus), new ItemStack(ConfigItems.itemNugget, 1, 21), new ItemStack(ConfigItems.itemNugget, 1, 16), new ItemStack(ConfigItems.itemNugget, 1, 31), new ItemStack(Items.diamond), new ItemStack(ConfigItems.itemFocusFrost), new ItemStack(ConfigItems.itemSwordElemental), new ItemStack(ThaumicTinkerer.registry.getFirstItemFromClass(ItemKamiResource.class), 1, 1));

    }

}
