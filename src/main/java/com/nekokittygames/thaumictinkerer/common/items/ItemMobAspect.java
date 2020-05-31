/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.common.items;

import com.nekokittygames.thaumictinkerer.common.helper.IItemVariants;
import com.nekokittygames.thaumictinkerer.common.libs.LibItemNames;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMobAspect extends TTItem implements IItemVariants {
    public static String ASPECT_NAME="aspectName";
    public ItemMobAspect() {
        super(LibItemNames.MOB_ASPECT);
    }


    @Override
    public String GetVariant(ItemStack stack) {
        return stack.getTagCompound() != null ? stack.getTagCompound().getString(ASPECT_NAME) : "aer";
    }

    @Override
    public String[] GetVariants() {
        return Aspect.aspects.keySet().toArray(new String[0]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey(ASPECT_NAME))
            tooltip.add(I18n.format("thaumictinkerer.mobaspect.type",stack.getTagCompound().getString(ASPECT_NAME)));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab))
        {
            for(String aspect:GetVariants()) {
                ItemStack itemStack=new ItemStack(this);
                NBTTagCompound cmp=itemStack.getTagCompound();
                if(cmp==null)
                    cmp=new NBTTagCompound();
                cmp.setString(ASPECT_NAME,aspect);
                itemStack.setTagCompound(cmp);
                items.add(itemStack);
            }
        }
    }
}
