package com.nekokittygames.thaumictinkerer.common.items;


import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientMisc;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public abstract class TTItem extends Item {
    private String baseName;

    public TTItem(final String itemName) {
        baseName = itemName;
        setItemName(this, itemName);
        if (isInCreativeTab())
            setCreativeTab(ThaumicTinkerer.getTab());
    }

    public static void setItemName(@Nonnull TTItem item, String itemName) {
        item.setRegistryName(LibMisc.MOD_ID, itemName);
        final ResourceLocation regName = Objects.requireNonNull(item.getRegistryName());
        item.setUnlocalizedName(regName.toString());
    }

    private boolean isInCreativeTab() {
        return true;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int i = 0;
        String name = "item." + LibClientMisc.RESOURCE_PREFIX + baseName + "." + i;
        while (I18n.hasKey(name)) {
            tooltip.add(I18n.format(name));
            i++;
            name = "item." + LibClientMisc.RESOURCE_PREFIX + baseName + "." + i;
        }

    }
}
