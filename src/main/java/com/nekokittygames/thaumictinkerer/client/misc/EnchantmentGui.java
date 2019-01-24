package com.nekokittygames.thaumictinkerer.client.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

/**
 * Created by nekosune on 26/07/18.
 */
@SideOnly(Side.CLIENT)
public class EnchantmentGui {

    public static ResourceLocation getEnchantmentIcon(Enchantment enchantment) {
        ResourceLocation location;
        ResourceLocation object = Enchantment.REGISTRY.getNameForObject(enchantment);
        ResourceLocation iconLoc = new ResourceLocation(object.getResourceDomain(), "textures/enchant_icons/" + object.getResourcePath() + ".png");

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
