package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;

public class ModRecipes {


    static ResourceLocation defaultGroup = new ResourceLocation("");
    public static void InitializeRecipes()
    {
        InitializeArcaneRecipes();
    }
    public static void InitializeArcaneRecipes()
    {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer","funnel"),new ShapedArcaneRecipe(defaultGroup,"ESSENTIA_FUNNEL",10,new AspectList().add(Aspect.ORDER,1).add(Aspect.ENTROPY,1),new ItemStack(ModBlocks.funnel),new Object[]{"STS",'S', Blocks.STONE,'T',"ingotThaumium"}));
    }
}
