package com.nekokittygames.thaumictinkerer.common.items.Kami;

import com.google.common.collect.Multimap;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.misc.ModelWings;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IVisDiscountGear;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.lib.events.PlayerEvents;

import java.util.List;

public class KamiArmor extends IchorArmor implements IGoggles{

    public KamiArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(name, materialIn, renderIndexIn, equipmentSlotIn);
        setTranslationKey(name);
        setCreativeTab(ThaumicTinkerer.getTab());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        switch (armorType) {
            case HEAD: {
                if(itemStack.getItemDamage() != 1){
                    player.setAir(300);
                if (player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock() == Blocks.WATER || player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock() == Blocks.FLOWING_WATER) {
                    player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400, 0, true, false));
                }
                if ((player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock() == Blocks.LAVA || player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock() == Blocks.FLOWING_LAVA) && player.ticksExisted % 10 == 0)
                    player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 31, 0, true, false));
                int food = player.getFoodStats().getFoodLevel();
                if (food > 0 && food < 18 && player.shouldHeal()
                        && player.ticksExisted % 80 == 0)
                    player.heal(1F);
            }}
            break;

            case CHEST: {
                if(itemStack.getItemDamage() != 1) {
                    player.getEntityData().setBoolean("can_fly", true);
                    doProjectileEffect(player);
                }
            }
            break;
            case LEGS: {
                if (itemStack.getItemDamage() != 1) {
                    if (player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null || player.getActivePotionEffect(MobEffects.FIRE_RESISTANCE).getDuration() <= 1) {
                        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1, 0, false, false));
                        if (player.isBurning()) {
                            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 1, true, false));
                            player.extinguish();
                        }
                    }
                }
            }
            break;

            case FEET: {
                if (itemStack.getItemDamage() != 1) {
                    {
                        if (player.world.isRemote && !player.isSneaking() && !player.capabilities.isFlying) {
                        if (!PlayerEvents.prevStep.containsKey(player.getEntityId()))
                            PlayerEvents.prevStep.put(player.getEntityId(), player.stepHeight);
                        player.stepHeight = 1.0F;
                    }
                        if ( player.moveForward > 0.0F) {
                            if(!player.capabilities.isFlying) {
                                if (player.onGround) {
                                    float bonus = 0.15F;
                                    if (player.isInWater())
                                        bonus /= 4.0F;
                                    player.moveRelative(0.0F, 0.0F, bonus, 1.0F);
                                } else {
                                    if (player.isInWater())
                                        player.moveRelative(0.0F, 0.0F, 0.25F, 1.0F);
                                    player.jumpMovementFactor = 0.05F;
                                }
                            }
                            if(player.capabilities.isFlying){
                                player.moveRelative(0.0F, 0.0F, 0.075F, 1.0F);
                            }
                            player.fallDistance = 0F;
                            player.jumpMovementFactor = player.isSprinting() ? 0.05F : 0.04F;
                        }
                        if (player.getActivePotionEffect(MobEffects.HASTE) == null || player.getActivePotionEffect(MobEffects.HASTE).getDuration() <= 1) {
                            player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 200, 1, false, false));
                        }
                    }
                    BlockPos posBelow = player.getPosition().down();
                    IBlockState blockStateBelow = player.world.getBlockState(posBelow);
                    if (blockStateBelow.getBlock() == Blocks.DIRT) {
                        player.getEntityWorld().setBlockState(player.getPosition().down(), Blocks.GRASS.getDefaultState(), 0);
                    }
                }
            }
            break;

        }
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
        if (slot == armorType) {
            if (slot == EntityEquipmentSlot.LEGS) {
            } else if (slot == EntityEquipmentSlot.FEET) {

            }
        }
        return map;
    }

    @Override
    public KamiArmor setTranslationKey(String key) {
        return (KamiArmor) super.setTranslationKey(key);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return LibMisc.MOD_ID + ":textures/models/armor/kami_layer_" + (slot == EntityEquipmentSlot.LEGS ? "2" : "1") + ".png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot
            armorSlot, ModelBiped _default) {
        if (armorType == EntityEquipmentSlot.CHEST) {
            return new ModelWings();
        }
        return null;
    }

    @Override
    public boolean showIngamePopups(ItemStack stack, EntityLivingBase owner) {
        return armorType == EntityEquipmentSlot.HEAD;
    }

    private void doProjectileEffect(EntityPlayer mp) {
        if (!mp.isSneaking()) {
            List<Entity> projectiles = mp.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(mp.posX - 2, mp.posY - 2, mp.posZ - 2, mp.posX + 2, mp.posY + 2, mp.posZ + 2), e -> e instanceof IProjectile);
            for (Entity potion : projectiles) {
                Vector3 motionVec = new Vector3(potion.motionX, potion.motionY, potion.motionZ).normalize().multiply(Math.sqrt((potion.posX - mp.posX) * (potion.posX - mp.posX) + (potion.posY - mp.posY) * (potion.posY - mp.posY) + (potion.posZ - mp.posZ) * (potion.posZ - mp.posZ)) * 2);

                for (int i = 0; i < 6; i++)
                    FXDispatcher.INSTANCE.sparkle((float) potion.posX, (float) potion.posY, (float) potion.posZ, 6, 0, 0);

                potion.posX += motionVec.x;
                potion.posY += motionVec.y;
                potion.posZ += motionVec.z;
            }
        }
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            int dmg = stack.getItemDamage();
            stack.setItemDamage(~dmg & 1);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getItemDamage() == 1) {
            tooltip.add(TextFormatting.RED +
                    I18n.translateToLocal("tip.awakenarmor.name1"));
        } else {
            tooltip.add(TextFormatting.DARK_GREEN +
                    I18n.translateToLocal("tip.awakenarmor.name0"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public void setBlock(BlockPos pos, World world, EntityPlayer player) {
        ItemStack itemStack = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        if ((world.isAirBlock(pos) || world.getBlockState(pos).equals(ModBlocks.nitor_vapor)) && !world.isRemote && itemStack.getItem() instanceof KamiArmor && itemStack.getItemDamage() != 1) {
            world.setBlockState(pos, ModBlocks.nitor_vapor.getDefaultState());
        }
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World world, Entity entity, int par4, boolean par5) {
        if (entity instanceof EntityPlayer && par1ItemStack.getItemDamage() != 1) {
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ).up();
            setBlock(pos, world, (EntityPlayer) entity);
        }
    }
}
