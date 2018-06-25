package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import com.nekokittygames.thaumictinkerer.common.utils.ItemNBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSoulMould extends TTItem {

    public static final String TAG_ENTITYNAME="entity_name";
    public ItemSoulMould() {
        super(LibItemNames.SOUL_MOULD);
        this.addPropertyOverride(new ResourceLocation("thaumictinkerer", "full"), new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack itemStack, @Nullable World world, @Nullable EntityLivingBase entityLivingBase) {
                return getEntityName(itemStack)!=null?1:0;
            }
        });
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        RayTraceResult result = rayTrace(worldIn, playerIn, false);

        if (result == null || (result != null && result.typeOfHit == RayTraceResult.Type.MISS && playerIn.isSneaking())) {
            ItemStack stack=playerIn.getHeldItem(handIn);
            clearEntityName(stack);
            return ActionResult.newResult(EnumActionResult.SUCCESS,stack);
        }
        return super.onItemRightClick(worldIn,playerIn,handIn);

    }


    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        ItemStack actualStack=playerIn.getHeldItem(hand);
        if(target!=null && !(target instanceof EntityPlayer))
        {
            setEntityName(actualStack,target);

            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String entityName=getEntityName(stack);
        if(entityName==null)
        {
            tooltip.add(ThaumicTinkerer.proxy.localize("ttmisc.soulMould.nonAssigned"));
        }
        else
        {
            tooltip.add(ThaumicTinkerer.proxy.localize("ttmisc.soulMould.pattern",ThaumicTinkerer.proxy.localize("entity."+entityName+".name")));
        }
    }

    public static void setEntityName(ItemStack stack, EntityLivingBase entity)
    {
        ItemNBTHelper.setString(stack, TAG_ENTITYNAME,EntityList.getEntityString(entity));
    }

    public static String getEntityName(ItemStack stack)
    {
        NBTTagCompound cmp=ItemNBTHelper.getItemTag(stack);
        if(cmp.hasKey(TAG_ENTITYNAME))
            return cmp.getString(TAG_ENTITYNAME);
        return null;
    }

    public static  void clearEntityName(ItemStack stack)
    {
        NBTTagCompound cmp=ItemNBTHelper.getItemTag(stack);
        cmp.removeTag(TAG_ENTITYNAME);
    }
}
