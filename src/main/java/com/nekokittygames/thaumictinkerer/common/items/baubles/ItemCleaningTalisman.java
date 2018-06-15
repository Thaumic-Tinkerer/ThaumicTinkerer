package com.nekokittygames.thaumictinkerer.common.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.items.TTItem;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.utils.IVariant;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.common.lib.SoundsTC;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ItemCleaningTalisman extends TTItem implements IBauble {
    public static final String TAG_STATUS="status";
    public ItemCleaningTalisman() {
        super(LibItemNames.CLEANING_TALISMAN);
        this.addPropertyOverride(new ResourceLocation("thaumictinkerer", "status"), new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack itemStack, @Nullable World world, @Nullable EntityLivingBase entityLivingBase) {
                return getStatus(itemStack)?1:0;
            }
        });
        setMaxDamage(TTConfig.talismanUses);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack current=playerIn.getHeldItem(handIn);
        if(playerIn.isSneaking())
        {
            flipEnabled(current);
            worldIn.playSound(playerIn,playerIn.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER,0.3f,0.1f);
            return ActionResult.newResult(EnumActionResult.SUCCESS,current);
        }
        return super.onItemRightClick(worldIn,playerIn,handIn);
    }

    private static void flipEnabled(ItemStack itemStack) {
        ItemNBTHelper.setBool(itemStack,TAG_STATUS,!ItemNBTHelper.getBool(itemStack,TAG_STATUS,false));
    }

    public static boolean getStatus(ItemStack itemStack)
    {
        return ItemNBTHelper.getBool(itemStack,TAG_STATUS,false);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(getStatus(stack))
            tooltip.add(ThaumicTinkerer.proxy.localize("ttmisc.cleaning_talisman.active"));
        else
            tooltip.add(ThaumicTinkerer.proxy.localize("ttmisc.cleaning_talisman.inactive"));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.UNCOMMON;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        World world=player.world;
        if(getStatus(itemstack) && !world.isRemote)
        {
            if(player.ticksExisted%20==0)
            {
                if(player instanceof EntityPlayer)
                {
                    boolean removed=false;
                    Collection<PotionEffect> effects=player.getActivePotionEffects();
                    if(player.isBurning())
                    {
                        player.extinguish();
                        removed=true;
                    }
                    else
                    {
                        for(PotionEffect effect :effects)
                        {
                            Potion potion=effect.getPotion();
                            if(potion.isBadEffect())
                            {
                                player.removePotionEffect(potion);
                                removed=true;
                                break;
                            }
                        }
                    }
                    if(removed)
                    {
                        itemstack.damageItem(1,player);
                        world.playSound((EntityPlayer)player,player.getPosition(), SoundsTC.wand,SoundCategory.MASTER,0.3f,0.1f);
                    }
                }
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
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }
}
