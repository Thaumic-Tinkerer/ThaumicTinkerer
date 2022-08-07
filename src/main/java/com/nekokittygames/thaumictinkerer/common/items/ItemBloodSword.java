/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.items;

import com.google.common.collect.Multimap;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.api.Materials;
import com.nekokittygames.thaumictinkerer.api.MobAspect;
import com.nekokittygames.thaumictinkerer.api.MobAspects;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.Aspect;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ItemBloodSword extends ItemSword {
    private static final int DAMAGE = 10;
    public static final String ACTIVE="Active";
    static int handleNext = 0;
    private String baseName;
    public ItemBloodSword() {
        super(Materials.BLOOD_MATERIAL);
        baseName = LibItemNames.BLOOD_SWORD;
        TTItem.setItemName(this, baseName);
        if (isInCreativeTab())
            setCreativeTab(ThaumicTinkerer.getTabMain());

        MinecraftForge.EVENT_BUS.register(this);
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
    public void onLivingDrops(LivingDropsEvent event) {
        if(event.getSource()!=null && event.getSource().getTrueSource() instanceof EntityPlayer && event.getSource().getTrueSource()!=null) {
	   EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack stack = player.getHeldItemMainhand();
            if (stack != ItemStack.EMPTY && stack.getItem() == this) { //&& stack.getTagCompound() != null && stack.getTagCompound().getInteger(ACTIVE) == 1) {
                //EnumHelper.addAction()
                MobAspect mobAspect = MobAspects.getAspects().get(event.getEntity().getClass());
                if (mobAspect != null) {
                    event.getDrops().clear();
                    ThaumicTinkerer.logger.debug("Outputting: " + mobAspect.toString());
                    for(Aspect aspect: mobAspect.getAspects().getAspects()) {
                        int amount=mobAspect.getAspects().getAmount(aspect);
                        ItemStack aspectStack=new ItemStack(ModItems.mob_aspect);
                        ItemMobAspect.setAspectType(aspectStack, aspect);
                        aspectStack.setCount(amount);
                        EntityItem item=new EntityItem(event.getEntity().getEntityWorld(),event.getEntityLiving().posX,event.getEntityLiving().posY,event.getEntityLiving().posZ,aspectStack);
                        event.getDrops().add(item);

                    }
                }
            }
        }
    }

    private boolean isInCreativeTab() {
        return true;
    }
}
