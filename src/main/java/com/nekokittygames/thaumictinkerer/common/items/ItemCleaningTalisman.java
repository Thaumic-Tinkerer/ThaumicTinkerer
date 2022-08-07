/*
 * Copyright (c) 2022. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.helper.StatusProperty;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import thaumcraft.common.lib.potions.*;

import java.util.Collection;
import java.util.List;

public class ItemCleaningTalisman extends TTItem implements IBauble {


    public ItemCleaningTalisman() {
        super(LibItemNames.CLEANING_TALISMAN);
        setMaxStackSize(1);
        setMaxDamage(TTConfig.talismanUses);
        this.addPropertyOverride(StatusProperty.RESOURCE_LOCATION,new StatusProperty());
    }

    public static boolean isEnabled(ItemStack stack) {
        return StatusProperty.getStatus(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ActionResult<ItemStack> result = super.onItemRightClick(world,player,hand);
        if(player.isSneaking()) {
            StatusProperty.toggleStatus(player.getHeldItem(hand));
            world.playSound(player,player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS,0.3f,0.1f);
        }
        return result;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(isEnabled(stack)? I18n.format("ttmisc.active") : I18n.format("ttmisc.inactive"));
    }

    @Override
    public EnumRarity getRarity(ItemStack p_77613_1_) {
        return EnumRarity.UNCOMMON;
    }



    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        World world= player.world;
        if(isEnabled(itemstack) && !world.isRemote) {
            if(player.ticksExisted % 20 ==0 ) {
                if(player instanceof EntityPlayer) {
                    boolean removed = false;
                    int damage = 1;

                    Collection<PotionEffect> potions = player.getActivePotionEffects();
                    if(player.isBurning())
                    {
                        player.extinguish();
                        removed=true;
                    } else {
                        for(PotionEffect potion : potions) {
                            boolean badEffect = potion.getPotion().isBadEffect();
                            if(potion.getPotion() instanceof PotionWarpWard)
                                badEffect=false;
                            if(badEffect) {
                                player.removePotionEffect(potion.getPotion());
                                removed=true;
                                if(potion.getPotion() instanceof PotionBlurredVision || potion.getPotion() instanceof PotionDeathGaze ||potion.getPotion() instanceof PotionInfectiousVisExhaust ||  potion.getPotion() instanceof PotionSunScorned || potion.getPotion() instanceof PotionUnnaturalHunger ) {
                                    damage=10;
                                }
                                break;
                            }
                        }
                    }
                    if(removed) {
                        itemstack.damageItem(damage,player);
                        if(itemstack.getItemDamage()<=0) {
                            //BaublesApi.getBaublesHandler(player);
                        }
                        world.playSound((EntityPlayer) player,player.getPosition(),SoundEvents.BLOCK_CHORUS_FLOWER_GROW,SoundCategory.PLAYERS,0.3f,0.1f);
                    }
                }
            }
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        IBauble.super.onEquipped(itemstack, player);
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        IBauble.super.onUnequipped(itemstack, player);
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return IBauble.super.canEquip(itemstack, player);
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return IBauble.super.canUnequip(itemstack, player);
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return IBauble.super.willAutoSync(itemstack, player);
    }
}
