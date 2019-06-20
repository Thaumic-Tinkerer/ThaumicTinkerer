package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.IThaumcraftRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.common.config.ConfigItems;

import java.util.Objects;

public class ModRecipes {


    private static ResourceLocation defaultGroup = new ResourceLocation("");

    public static void initializeRecipes() {
        initializeCraftingRecipes();
        initializeCauldronRecipes();
        initializeArcaneRecipes();
        initializeInfusionRecipes();
    }

    private static void initializeCauldronRecipes() {
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumictinkerer:prismarine"),new CrucibleRecipe("TT_PRISMARINE",new ItemStack(Items.PRISMARINE_SHARD), "paneGlass",new AspectList().add(Aspect.WATER,5).add(Aspect.EARTH,5)));
    }

    private static void initializeCraftingRecipes() {
    }

    private static void initializeArcaneRecipes() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:funnel"), new ShapedArcaneRecipe(defaultGroup, "TT_ESSENTIA_FUNNEL", 10, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.funnel)), "STS", 'S', Blocks.STONE, 'T', "ingotThaumium"));
        // temp
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:dissimulation"),new ShapedArcaneRecipe(defaultGroup,"TT_DISSIMULATION",10,new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16),new ItemStack(ModBlocks.dissimulation),"BEB","PBP","BEB",'B',new ItemStack(BlocksTC.stoneArcane),'E',new ItemStack(Items.CLAY_BALL),'P',new ItemStack(Items.PRISMARINE_SHARD)));
        IRecipe dis=new ShapedArcaneRecipe(defaultGroup, "TT_TRANSVECTOR_INTERFACE", 10, new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16), new ItemStack(Objects.requireNonNull(ModBlocks.transvector_interface)), "BRB", "LEL", "BDB", 'B', new ItemStack(BlocksTC.stoneArcane), 'R', "dustRedstone", 'L', new ItemStack(Items.DYE, 1, 4), 'E', new ItemStack(Items.ENDER_PEARL), 'D', new ItemStack(Objects.requireNonNull(ModBlocks.dissimulation)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:transvector_interface"), (IArcaneRecipe) dis);
        //CommonInternals.craftingRecipeCatalog.put(dis.getRegistryName(), (IThaumcraftRecipe) dis);
    }

    private static void initializeInfusionRecipes() {
        // Empty for now
    }
}
