/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.items;

import com.google.common.collect.Multimap;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.api.Materials;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemBloodSword extends ItemSword {
    private static final int DAMAGE = 10;
    public static final String ACTIVE="Active";
    static int handleNext = 0;
    private String baseName;
    public ItemBloodSword() {
        super(Materials.BLOOD_MATERIAL);
        baseName = "blood_sword";
        TTItem.setItemName(this, baseName);
        if (isInCreativeTab())
            setCreativeTab(ThaumicTinkerer.getTab());


    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)DAMAGE, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 0.25D, 1));
        }

        return multimap;
    }

    @Override
    public float getAttackDamage() {
        return DAMAGE;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        boolean sup=super.hitEntity(stack, target, attacker);
        boolean handle = handleNext == 0;
        if (!handle)
            handleNext--;
        if(sup) {
            if (handle) {
                attacker.attackEntityFrom(DamageSource.MAGIC, 2);
            }
        }

        return sup;
    }

    @SubscribeEvent
    public void onDrops(LivingDropsEvent event) {
        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        ItemStack stack = player.getHeldItemMainhand();
        if (stack != ItemStack.EMPTY && stack.getItem() == this && stack.getTagCompound() != null && stack.getTagCompound().getInteger(ACTIVE) == 1) {
            EnumHelper.addAction()
        }
    }

    private boolean isInCreativeTab() {
        return true;
    }
}
