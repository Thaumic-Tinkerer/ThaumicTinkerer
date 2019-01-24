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
import thaumcraft.api.items.ItemsTC;

import java.util.Objects;

public class ModRecipes {


    private static ResourceLocation defaultGroup = new ResourceLocation("");

    public static void initializeRecipes() {
        initializeArcaneRecipes();
        initializeInfusionRecipes();
    }

    private static void initializeArcaneRecipes() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer", "funnel"), new ShapedArcaneRecipe(defaultGroup, "ESSENTIA_FUNNEL", 10, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.funnel)), "STS", 'S', Blocks.STONE, 'T', "ingotThaumium"));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer", "transvector_interface"), new ShapedArcaneRecipe(defaultGroup, "TRANSVECTOR_INTERFACE", 10, new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16), new ItemStack(Objects.requireNonNull(ModBlocks.transvector_interface)), "BRB", "LEL", "BDB", 'B', new ItemStack(BlocksTC.stoneArcane), 'R', "dustRedstone", 'L', new ItemStack(Items.DYE, 1, 4), 'E', new ItemStack(Items.ENDER_PEARL), 'D', new ItemStack(Objects.requireNonNull(ModBlocks.dissimulation))));
    }

    private static void initializeInfusionRecipes() {
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumictinkerer", "cleaning_talisman"), new InfusionRecipe("CLEANING_TALISMAN", new ItemStack(Objects.requireNonNull(ModItems.cleaning_talisman)), 5, (new AspectList()).add(Aspect.LIFE, 10).add(Aspect.MAN, 20).add(Aspect.AVERSION, 10), new ItemStack(Items.ENDER_PEARL), new ItemStack(ItemsTC.quicksilver), new ItemStack(ItemsTC.quicksilver), new ItemStack(Objects.requireNonNull(ModItems.black_quartz)), new ItemStack(ModItems.black_quartz), new ItemStack(Items.GHAST_TEAR)));
    }
}
