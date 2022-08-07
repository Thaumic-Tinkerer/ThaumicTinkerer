/*
 * Copyright (c) 2022. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.helper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.nekokittygames.thaumictinkerer.common.libs.LibMisc.MOD_ID;

public class StatusProperty  implements IItemPropertyGetter {


    public static final String STATUS="status";

    public static final ResourceLocation RESOURCE_LOCATION=new ResourceLocation(MOD_ID,"status");
    @Override
    public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
        return getStatus(stack) ? 1 : 0 ;
    }


    public static boolean getStatus(ItemStack stack) {
        if(!stack.hasTagCompound())
            return false;
       return stack.getTagCompound().getBoolean(STATUS);
    }

    public static void setStatus(@NotNull ItemStack stack, boolean stat) {
        NBTTagCompound cmp;
        if(stack.hasTagCompound())
            cmp=stack.getTagCompound();
        else
            cmp=new NBTTagCompound();
        cmp.setBoolean(STATUS,stat);
        stack.setTagCompound(cmp);
    }
    public static void toggleStatus(@NotNull ItemStack stack) {
        NBTTagCompound cmp;
        boolean curStatus=false;
        if(stack.hasTagCompound()) {
            cmp = stack.getTagCompound();
            curStatus = cmp.getBoolean(STATUS);
        }
        else
            cmp=new NBTTagCompound();

        cmp.setBoolean(STATUS,!curStatus);
        stack.setTagCompound(cmp);
    }

}
