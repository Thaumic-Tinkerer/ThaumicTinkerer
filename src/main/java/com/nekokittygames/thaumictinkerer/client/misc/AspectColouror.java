/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.misc;

import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;

public class AspectColouror implements IItemColor {
    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if(stack.getItem()== ModItems.mob_aspect || stack.getItem()== ModItems.condensed_mob_aspect) {
            return Aspect.getAspect(ModItems.condensed_mob_aspect.GetVariant(stack)).getColor();
        }
        return 0;
    }
}
