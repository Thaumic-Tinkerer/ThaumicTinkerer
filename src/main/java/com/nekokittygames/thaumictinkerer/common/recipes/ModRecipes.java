package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.golems.GolemHelper;
import thaumcraft.api.items.ItemsTC;

public class ModRecipes {


    static ResourceLocation defaultGroup = new ResourceLocation("");
    public static void InitializeRecipes()
    {
        InitializeArcaneRecipes();
        InitializeInfusionRecipes();
    }
    public static void InitializeArcaneRecipes()
    {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer","funnel"),new ShapedArcaneRecipe(defaultGroup,"ESSENTIA_FUNNEL",10,new AspectList().add(Aspect.ORDER,1).add(Aspect.ENTROPY,1),new ItemStack(ModBlocks.funnel),new Object[]{"STS",'S', Blocks.STONE,'T',"ingotThaumium"}));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer","transvector_interface"),new ShapedArcaneRecipe(defaultGroup,"TRANSVECTOR_INTERFACE",10,new AspectList().add(Aspect.ORDER,12).add(Aspect.ENTROPY,16),new ItemStack(ModBlocks.transvector_interface),new Object[]{"BRB","LEL","BDB",'B', new ItemStack(BlocksTC.stoneArcane),'R',"dustRedstone",'L',new ItemStack(Items.DYE,1,4),'E',new ItemStack(Items.ENDER_PEARL),'D',new ItemStack(ModBlocks.dissimulation)}));
    }

    public static void  InitializeInfusionRecipes()
    {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumictinkerer","cleaning_talisman"), new InfusionRecipe("CLEANING_TALISMAN", new ItemStack(ModItems.cleaning_talisman), 5, (new AspectList()).add(Aspect.LIFE, 10).add(Aspect.MAN, 20).add(Aspect.AVERSION, 10), new ItemStack(Items.ENDER_PEARL), new Object[]{new ItemStack(ItemsTC.quicksilver),new ItemStack(ItemsTC.quicksilver),new ItemStack(ModItems.black_quartz),new ItemStack(ModItems.black_quartz),new ItemStack(Items.GHAST_TEAR)}));
    }
}
