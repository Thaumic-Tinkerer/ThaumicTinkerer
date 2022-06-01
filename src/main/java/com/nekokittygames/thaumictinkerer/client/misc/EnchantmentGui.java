/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.Objects;

/**
 * Helper class to load enchanter icons
 */
@SideOnly(Side.CLIENT)
public class EnchantmentGui {

    /**
     * Gets the appropriate ResourceLocation for an enchantment, or the default unknown location if none
     *
     * @param enchantment {@link Enchantment} to get icon for
     * @return {@link ResourceLocation} containing the appropriate icon
     */
    public static ResourceLocation getEnchantmentIcon(Enchantment enchantment) {
        if(enchantment==null)
            return new ResourceLocation("thaumictinkerer", "textures/enchant_icons/unknown.png");
        ResourceLocation object = Enchantment.REGISTRY.getNameForObject(enchantment);
        ResourceLocation iconLoc = new ResourceLocation(Objects.requireNonNull(object).getNamespace(), "textures/enchant_icons/" + object.getPath() + ".png");

        try {
            IResource res = Minecraft.getMinecraft().getResourceManager().getResource(iconLoc);
            if (res == null)
                iconLoc = new ResourceLocation("thaumictinkerer", "textures/enchant_icons/unknown.png");
        } catch (IOException e) {
            iconLoc = new ResourceLocation("thaumictinkerer", "textures/enchant_icons/unknown.png");
        }

        return iconLoc;
    }
}
