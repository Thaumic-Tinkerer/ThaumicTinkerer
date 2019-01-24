package com.nekokittygames.thaumictinkerer.common.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

public class ItemNBTHelper {


    @NotNull
    public static NBTTagCompound getItemTag(ItemStack item) {
        if (item.getTagCompound() == null) {
            item.setTagCompound(new NBTTagCompound());
        }
        return item.getTagCompound();
    }

    public static String getString(ItemStack item, String keyName, String defaultValue) {
        if (getItemTag(item).hasKey(keyName))
            return getItemTag(item).getString(keyName);
        else {
            getItemTag(item).setString(keyName, defaultValue);
            return defaultValue;
        }
    }

    public static void setString(ItemStack item, String keyName, String value) {
        getItemTag(item).setString(keyName, value);
    }

    public static boolean getBool(ItemStack item, String keyName, boolean defaultValue) {
        if (getItemTag(item).hasKey(keyName))
            return getItemTag(item).getBoolean(keyName);
        else {
            getItemTag(item).setBoolean(keyName, defaultValue);
            return defaultValue;
        }
    }

    public static void setBool(ItemStack item, String keyName, boolean value) {
        getItemTag(item).setBoolean(keyName, value);
    }

    public static void setInteger(ItemStack item, String keyName, int value) {
        getItemTag(item).setInteger(keyName, value);
    }

    public static int getInteger(ItemStack item, String keyName, int defaultValue) {
        if (getItemTag(item).hasKey(keyName))
            return getItemTag(item).getInteger(keyName);
        else {
            getItemTag(item).setInteger(keyName, defaultValue);
            return defaultValue;
        }
    }
}
