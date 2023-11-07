package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.enchantments.TTEnchantments;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.lib.SoundsTC;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID)
public class EnchantmentEvents {
    public static void register(ResourceLocation resourceLocation) {
    }

    public static final String NBTLastTarget = "TTEnchantLastTarget";

    public static final String NBTSuccessiveStrike = "TTEnchantSuccessiveStrike";

    public static final String NBTTunnelDirection = "TTEnchantTunnelDir";

    @SubscribeEvent
    public static void playerJumps(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack stack1 = event.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.LEGS);
            int boost = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.ascentboost, stack1);

            if (boost >= 1 && !player.isSneaking())
                player.motionY *= (boost + 2) / 2D;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityUpdate(net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent event) {
        final double min = -0.0784000015258789;

        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            int slowfall = EnchantmentHelper.getMaxEnchantmentLevel(TTEnchantments.slowfall, player);
            if (slowfall > 0 && !event.getEntityLiving().isSneaking() && event.getEntityLiving().motionY < min && event.getEntityLiving().fallDistance >= 2.9) {
                event.getEntityLiving().motionY /= 1 + slowfall * 0.33F;
                event.getEntityLiving().fallDistance = Math.max(2.9F, player.fallDistance - slowfall / 3F);

                player.world.spawnParticle(EnumParticleTypes.CLOUD, player.posX + 0.25, player.posY - 1, player.posZ + 0.25, -player.motionX, player.motionY, -player.motionZ);

            }
        }
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST, receiveCanceled=true)
    public static void onEvent(LivingEntityUseItemEvent.Tick event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity instanceof EntityPlayer) {
            ItemStack item = event.getItem();
            int time = event.getDuration();
            int quickDraw = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.quickdraw, item);
            if (item.isEmpty()) {
                return;
            }

            if (quickDraw > 0 && item.getItem() instanceof ItemBow) {

                    if ((item.getItem().getMaxItemUseDuration(item) - time) % (6 - quickDraw) == 0)
                        event.setDuration(time - 1);
            }
        }
    }


    @SubscribeEvent
    public static void fall(LivingFallEvent e) {
        ItemStack boots = e.getEntityLiving().getItemStackFromSlot(EntityEquipmentSlot.FEET);

        if (e.getEntityLiving() instanceof EntityPlayer) {
            int shockwave = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.shockwave, boots);
            if (shockwave > 0) {
                for (EntityLivingBase target : (List<EntityLivingBase>) e.getEntity().world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(e.getEntity().posX - 10, e.getEntity().posY - 10, e.getEntity().posZ - 10, e.getEntity().posX + 10, e.getEntity().posY + 10, e.getEntity().posZ + 10))) {
                    if (target != e.getEntity() && e.getDistance() > 3) {
                        target.attackEntityFrom(DamageSource.FALL, .1F * shockwave * e.getDistance());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityLivingBase) {
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
            ItemStack heldItem = attacker.getHeldItem(EnumHand.MAIN_HAND);

            if (heldItem == null)
                return;


            if (attacker instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) attacker;

                ItemStack legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
                int pounce = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.pounce, legs);
                if (pounce > 0) {
                    BlockPos pos = new BlockPos((int) Math.floor(player.posX), (int) Math.floor(player.posY) - 1, (int) Math.floor(player.posZ));
                    if (player.world.getBlockState(pos) == Blocks.AIR.getDefaultState()) {
                        event.setAmount((float) (event.getAmount() * (1 + (.25 * pounce))));
                    }
                }

            }

            int finalStrike = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.finalStrike, heldItem);
            if (finalStrike > 0) {
                Random rand = new Random();
                if (rand.nextInt(20 - finalStrike) == 0) {
                    event.setAmount((float) (event.getAmount() * 3));
                }
            }

            int valiance = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.valiance, heldItem);
            if (valiance > 0) {
                if (attacker.getHealth() / attacker.getMaxHealth() < .5F) {
                    event.setAmount((float) (event.getAmount() * (1 + .1 * valiance)));
                }
            }

            int vampirism = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.vamprisim, heldItem);
            if (vampirism > 0) {
                attacker.heal(vampirism);
                event.getEntityLiving().world.playSound(event.getEntityLiving().posX, event.getEntityLiving().posY, event.getEntityLiving().posZ, SoundsTC.zap, SoundCategory.NEUTRAL, 0.6F, 1F, false);
            }

            int focusedStrikes = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.focusedStrikes, heldItem);

            int dispersedStrikes = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.dispersedStrikes, heldItem);

            if (focusedStrikes > 0 || dispersedStrikes > 0) {
                int lastTarget = heldItem.getTagCompound().getInteger(NBTLastTarget);
                int successiveStrikes = heldItem.getTagCompound().getInteger(NBTSuccessiveStrike);
                int entityId = event.getEntityLiving().getEntityId();

                if (lastTarget != entityId) {
                    heldItem.getTagCompound().setInteger(NBTSuccessiveStrike, 0);
                    successiveStrikes = 0;
                } else {
                    heldItem.getTagCompound().setInteger(NBTSuccessiveStrike, successiveStrikes + 1);
                    successiveStrikes = 1;
                }

                if (focusedStrikes > 0) {
                    event.setAmount(event.getAmount() / 2);
                    event.setAmount((float) (event.getAmount() + (.5 * successiveStrikes * event.getAmount() * focusedStrikes)));
                }
                if (dispersedStrikes > 0) {
                    event.setAmount((float) (event.getAmount() * (1 + 0.2 * successiveStrikes)));
                    event.setAmount(event.getAmount() / (1 + (successiveStrikes * 2)));
                }

                heldItem.getTagCompound().setInteger("TTEnchantLastTarget", entityId);

            }
        }
    }

    @SubscribeEvent
    public void onBreakBlock(BlockEvent.BreakEvent event) {
        ItemStack item = event.getPlayer().getHeldItem(EnumHand.MAIN_HAND);
        int tunnel = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.tunnel, item);
        if (tunnel > 0) {
            float dir = event.getPlayer().rotationYaw;
            item.getTagCompound().setFloat(NBTTunnelDirection, dir);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGetHarvestSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack heldItem = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);

        if (heldItem == null)
            return;

        int shatter = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.shatter, heldItem);
        if (shatter > 0) {
            if (event.getState().getBlockHardness(event.getEntityPlayer().world, BlockPos.ORIGIN) > 20F) {
                event.setNewSpeed((float) (event.getOriginalSpeed() * 3 * shatter));
            } else {
                event.setNewSpeed((float) (event.getOriginalSpeed() * .8));
            }
        }

        int tunnel = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.tunnel, heldItem);
        if (tunnel > 0) {
            float dir = event.getEntityPlayer().rotationYaw;
            if (heldItem.getTagCompound() != null && heldItem.getTagCompound().hasKey(NBTTunnelDirection)) {
                float oldDir = heldItem.getTagCompound().getFloat(NBTTunnelDirection);
                float dif = Math.abs(oldDir - dir);
                if (dif < 50) {
                    event.setNewSpeed((float) (event.getOriginalSpeed() * (1 + (.2 * tunnel))));
                } else {
                    event.setNewSpeed((float) (event.getOriginalSpeed() * .3));
                }
            }
        }

        int desintegrate = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.desintegrate, heldItem);
        int autoSmelt = EnchantmentHelper.getEnchantmentLevel(TTEnchantments.autosmelt, heldItem);

        boolean desintegrateApplies = desintegrate > 0 && event.getState().getBlockHardness(event.getEntityPlayer().world, BlockPos.ORIGIN) <= 1.5F;
        boolean autoSmeltApplies = autoSmelt > 0 && event.getState().getMaterial() == Material.WOOD;

        if (desintegrateApplies || autoSmeltApplies) {
            heldItem.damageItem(1, event.getEntityPlayer());
            event.setNewSpeed(Float.MAX_VALUE);
        } else if (desintegrate > 0 || autoSmelt > 0)
            event.setCanceled(true);
    }
}