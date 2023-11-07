package com.nekokittygames.thaumictinkerer.common.items.Kami.Tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.entity.SharedMonsterAttributes.ATTACK_DAMAGE;

public class IchoriumSwordAdv extends Item 
{
    boolean ignoreLeftClick = false;

    public IchoriumSwordAdv(String name, CreativeTabs tab, ToolMaterial material) {
        super();
        setTranslationKey(name);
        setRegistryName(name);
        maxStackSize = 1;
        setCreativeTab(tab);
        this.addPropertyOverride(new ResourceLocation("ichoriumswordadv:awaken"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") != 0) {
                    if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2) {
                        return 2.0F;
                    }
                    return 1.0F;
                }
                return 0.0F;
            }
        });

        
    }

    @Override
    public EnumRarity getRarity(ItemStack itemstack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        boolean ret = super.onLeftClickEntity(stack, player, entity);
        if (!ignoreLeftClick && entity instanceof EntityLivingBase && entity.hurtResistantTime == 0 && !entity.isDead) {
            if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 1) {
                int range = 3;
                List<Entity> entities = player.world.getEntitiesWithinAABB(entity.getClass(), new AxisAlignedBB(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range));
                ignoreLeftClick = true;
                for (Entity entity1 : entities)
                    entity1.attackEntityFrom(DamageSource.causeMobDamage(player), (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                ignoreLeftClick = false;

            } else if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2) {
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1, 1));
                float absorption = player.getAbsorptionAmount();
                if (absorption < 20) {
                    player.setAbsorptionAmount(absorption + 1);
                }
            }
        }
        return ret;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking())     {
            NBTTagCompound nbtTagCompound = stack.getTagCompound();

            if (nbtTagCompound == null)
            {
                nbtTagCompound = new NBTTagCompound();
                stack.setTagCompound(nbtTagCompound);
            }

            nbtTagCompound.setInteger("awaken", (nbtTagCompound.getInteger("awaken") + 1) % 3);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 1){
            tooltip.add(TextFormatting.RED +
                    I18n.translateToLocal("tip.awakensword.name1"));}
        else if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2){
            tooltip.add(TextFormatting.BLUE +
                    I18n.translateToLocal("tip.awakensword.name2"));}
        else{tooltip.add(TextFormatting.DARK_GREEN +
                I18n.translateToLocal("tip.awakensword.name0"));}
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();

        if (block == Blocks.WEB)
        {
            return 15.0F;
        }
        else
        {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return blockIn.getBlock() == Blocks.WEB;
    }

    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean isFull3D()
    {
        return true;
    }

    @Override
    public int getItemEnchantability()
    {
        return 22;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        return !enchantment.equals(Enchantments.SWEEPING) && (enchantment.type == EnumEnchantmentType.WEAPON);
    }

    @Override
    public boolean getIsRepairable(ItemStack repairingItem, ItemStack material)
    {
        return false;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        if (equipmentSlot != EntityEquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(equipmentSlot, stack);
        }

        int damage = 9;
        if (stack.getTagCompound() != null && stack.getTagCompound().getInteger("awaken") == 2)
            damage = 5;

        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
                multimap.put(ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));

        return multimap;
    }
}