package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectTelekenesis;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;
import com.nekokittygames.thaumictinkerer.common.recipes.ing.TTFocusIngredient;
import com.nekokittygames.thaumictinkerer.common.recipes.ing.TTIngredientNBT;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IngredientNBT;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.IThaumcraftRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.casters.ItemFocus;
import thaumcraft.common.items.resources.ItemCrystalEssence;

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
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumictinkerer:soul_mould"),new CrucibleRecipe("TT_THAUMIC_MAGNETS",new ItemStack(ModItems.soul_mould),new ItemStack(Items.ENDER_PEARL),new AspectList().add(Aspect.BEAST,4).add(Aspect.MIND,8).add(Aspect.SENSES,8)));
    }

    private static void initializeCraftingRecipes() {
    }

    private static void initializeArcaneRecipes() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:funnel"), new ShapedArcaneRecipe(defaultGroup, "TT_ESSENTIA_FUNNEL", 10, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.funnel)), "STS", 'S', Blocks.STONE, 'T', "ingotThaumium"));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:dissimulation"),new ShapedArcaneRecipe(defaultGroup,"TT_DISSIMULATION",10,new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16),new ItemStack(ModBlocks.dissimulation),"BEB","PBP","BEB",'B',new ItemStack(BlocksTC.stoneArcane),'E',new ItemStack(Items.CLAY_BALL),'P',new ItemStack(Items.PRISMARINE_SHARD)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:transvector_interface"), new ShapedArcaneRecipe(defaultGroup, "TT_TRANSVECTOR_INTERFACE", 10, new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16), new ItemStack(Objects.requireNonNull(ModBlocks.transvector_interface)), "BRB", "LEL", "BDB", 'B', new ItemStack(BlocksTC.stoneArcane), 'R', "dustRedstone", 'L', new ItemStack(Items.DYE, 1, 4), 'E', new ItemStack(Items.ENDER_PEARL), 'D', new ItemStack(Objects.requireNonNull(ModBlocks.dissimulation))));
        // Magnets
        ItemStack airCrystal=new ItemStack(ItemsTC.crystalEssence);
        ((ItemCrystalEssence)ItemsTC.crystalEssence).setAspects(airCrystal,new AspectList().add(Aspect.AIR,1));
        ItemStack earthCrystal=new ItemStack(ItemsTC.crystalEssence);
        ((ItemCrystalEssence)ItemsTC.crystalEssence).setAspects(earthCrystal,new AspectList().add(Aspect.EARTH,1));
        ItemStack focus=new ItemStack(ItemsTC.focus1);
        FocusPackage focusPackage=new FocusPackage();
        focusPackage.addNode(new FocusEffectTelekenesis());
        ItemFocus.setPackage(focus,focusPackage);
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:corporeal_attactor"),new ShapedArcaneRecipe(defaultGroup,"TT_THAUMIC_MAGNETS",20,new AspectList().add(Aspect.AIR,20).add(Aspect.ORDER,5).add(Aspect.EARTH,15).add(Aspect.ENTROPY,5),new ItemStack(Objects.requireNonNull(ModBlocks.mob_magnet))," C ","ACE","GFG",'C', new ItemStack(ItemsTC.ingots, 1, 0),'A',new TTIngredientNBT(airCrystal),'E',new TTIngredientNBT(earthCrystal),'G',new ItemStack(BlocksTC.logGreatwood),'F',new TTFocusIngredient(FocusEffectTelekenesis.class, focus)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:kinetic_attactor"),new ShapedArcaneRecipe(defaultGroup,"TT_THAUMIC_MAGNETS",20,new AspectList().add(Aspect.AIR,20).add(Aspect.ORDER,5).add(Aspect.EARTH,15).add(Aspect.ENTROPY,5),new ItemStack(Objects.requireNonNull(ModBlocks.magnet))," C ","ACE","GFG",'C',"ingotIron",'A',new TTIngredientNBT(airCrystal),'E',new TTIngredientNBT(earthCrystal),'G',new ItemStack(BlocksTC.logGreatwood),'F',new TTFocusIngredient(FocusEffectTelekenesis.class, focus)));
    }

    private static void initializeInfusionRecipes() {
        // Empty for now
    }
}
