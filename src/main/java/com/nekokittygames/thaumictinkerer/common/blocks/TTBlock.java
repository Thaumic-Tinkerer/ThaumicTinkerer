package com.nekokittygames.thaumictinkerer.common.blocks;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientMisc;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.misc.CreativeTabThaumicTinkerer;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class TTBlock extends Block {
    private String baseName;
    public TTBlock(String name,Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
        baseName=name;
        setBlockName(this, name);
        if(isInCreativeTab())
            setCreativeTab(CreativeTabThaumicTinkerer.getInstance());
    }

    private static void setBlockName(final TTBlock block, final String blockName) {
        block.setRegistryName(LibMisc.MOD_ID, blockName);
        final ResourceLocation registryName = Objects.requireNonNull(block.getRegistryName());
        block.setUnlocalizedName(registryName.toString());
    }

    public TTBlock(String name,Material materialIn) {
        this(name,materialIn,materialIn.getMaterialMapColor());

    }


    protected boolean isInCreativeTab() {
        return true;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int i=0;
        String name="item."+ LibClientMisc.RESOURCE_PREFIX+baseName+"."+i;
        while(I18n.hasKey(name))
        {
            tooltip.add(I18n.format(name));
            i++;
            name="item."+ LibClientMisc.RESOURCE_PREFIX+baseName+"."+i;
        }

    }
}
