package com.nekokittygames.thaumictinkerer.common.recipes;

import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.blocks.TTBlock;
import com.nekokittygames.thaumictinkerer.common.compat.BotaniaCompat;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectTelekenesis;
import com.nekokittygames.thaumictinkerer.common.items.ModItems;

import com.nekokittygames.thaumictinkerer.common.libs.LibResearch;

import com.nekokittygames.thaumictinkerer.common.libs.LibOreDict;

import com.nekokittygames.thaumictinkerer.common.recipes.ing.TTFocusIngredient;
import com.nekokittygames.thaumictinkerer.common.recipes.ing.TTIngredientNBT;
import li.cil.oc.integration.Mod;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.IForgeRegistry;

import net.minecraftforge.oredict.OreDictionary;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.casters.FocusPackage;
import thaumcraft.api.crafting.*;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.misc.BlockNitor;
import thaumcraft.common.items.casters.ItemFocus;
import thaumcraft.common.items.resources.ItemCrystalEssence;
import thaumcraft.common.lib.crafting.DustTriggerMultiblock;

import java.util.Objects;

public class ModRecipes {


    private static ResourceLocation defaultGroup = new ResourceLocation("");


    public static void initializeRecipes(IForgeRegistry<IRecipe> registry) {
        registerOreDict();
        initializeCraftingRecipes(registry);
        initializeCauldronRecipes();
        initializeArcaneRecipes();
        initializeInfusionRecipes();
        initializeMultiblockRecipes();
        BotaniaCompat.addOreDict();

    }

    private static void registerOreDict() {
        OreDictionary.registerOre(LibOreDict.BLACK_QUARTZ_BLOCK,ModBlocks.black_quartz_block);
        OreDictionary.registerOre(LibOreDict.BLACK_QUARTZ,ModItems.black_quartz);
    }

    private static void initializeMultiblockRecipes() {
        Part AB = new Part(BlocksTC.stoneArcaneBrick, BlocksTC.stoneArcaneBrick);
        Part BQ = new Part(LibOreDict.BLACK_QUARTZ_BLOCK, ModBlocks.black_quartz_block);
        Part DBQ = new Part(ModBlocks.black_quartz_block, ModBlocks.black_quartz_block);

        Part[] pillars = new Part[16];

        for (int i = 0; i < 16; i++) {
            pillars[i] = new Part(BlocksTC.stoneArcane, new ItemStack(ModBlocks.enchantment_pillar, 1, i));
        }
        Part NR = new Part(BlockNitor.class, "AIR");
        Part DN = new Part(ModBlocks.dummy_nitor,"AIR");
        Part OE = new Part(ModBlocks.osmotic_enchanter, ModBlocks.osmotic_enchanter);
        Part[][][] enchanterBP = new Part[][][]{{{null, null, null, NR, null, null, null}, {null, NR, null, null, null, NR, null}, {null, null, null, null, null, null, null}, {NR, null, null, null, null, null, NR}, {null, null, null, null, null, null, null}, {null, NR, null, null, null, NR, null}, {null, null, null, NR, null, null, null}},
                {{null, null, null, pillars[15], null, null, null}, {null, pillars[14], null, null, null,pillars[8], null}, {null, null, null, null, null, null, null}, {pillars[13], null, null, null, null, null, pillars[9]}, {null, null, null, null, null, null, null}, {null, pillars[12], null, null, null, pillars[10], null}, {null, null, null, pillars[11], null, null, null}},
                {{null, null, null, pillars[7], null, null, null}, {null, pillars[6], null, null, null, pillars[0], null}, {null, null, null, null, null, null, null}, {pillars[5], null, null, OE, null, null, pillars[1]}, {null, null, null, null, null, null, null}, {null, pillars[4], null, null, null, pillars[2], null}, {null, null, null, pillars[3], null, null, null}},
                {{null,null,null,AB,null,null,null},{null,AB,AB,BQ,AB,AB,null},{null,AB,BQ,BQ,BQ,AB,null},{AB,BQ,BQ,BQ,BQ,BQ,AB},{null,AB,BQ,BQ,BQ,AB,null},{null,AB,AB,BQ,AB,AB,null},{null,null,null,AB,null,null,null}}

        };

        Part[][][] dummyenchanterBP = new Part[][][]{{{null, null, null, DN, null, null, null}, {null, DN, null, null, null, DN, null}, {null, null, null, null, null, null, null}, {DN, null, null, null, null, null, DN}, {null, null, null, null, null, null, null}, {null, DN, null, null, null, DN, null}, {null, null, null, DN, null, null, null}},
                {{null, null, null, pillars[15], null, null, null}, {null, pillars[14], null, null, null,pillars[8], null}, {null, null, null, null, null, null, null}, {pillars[13], null, null, null, null, null, pillars[9]}, {null, null, null, null, null, null, null}, {null, pillars[12], null, null, null, pillars[10], null}, {null, null, null, pillars[11], null, null, null}},
                {{null, null, null, pillars[7], null, null, null}, {null, pillars[6], null, null, null, pillars[0], null}, {null, null, null, null, null, null, null}, {pillars[5], null, null, OE, null, null, pillars[1]}, {null, null, null, null, null, null, null}, {null, pillars[4], null, null, null, pillars[2], null}, {null, null, null, pillars[3], null, null, null}},
                {{null,null,AB,AB,AB,null,null},{null,AB,AB,DBQ,AB,AB,null},{AB,AB,DBQ,DBQ,DBQ,AB,AB},{AB,DBQ,DBQ,DBQ,DBQ,DBQ,AB},{AB,AB,DBQ,DBQ,DBQ,AB,AB},{null,AB,AB,DBQ,AB,AB,null},{null,null,AB,AB,AB,null,null}}
        };
        IDustTrigger.registerDustTrigger(new TTDustTriggerMultiblock("TT_ENCHANTER",enchanterBP));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation("thaumictinkerer:osmotic_enchanter_mb"),new ThaumcraftApi.BluePrint("TT_ENCHANTER",dummyenchanterBP,new ItemStack[]{new ItemStack(ModBlocks.black_quartz_block,13),new ItemStack(BlocksTC.stoneArcaneBrick,24),new ItemStack(BlocksTC.stoneArcane,16),new ItemStack(BlocksTC.nitor.get(EnumDyeColor.WHITE),8)}));

    }
    private static void initializeCauldronRecipes() {
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumictinkerer:prismarine"),new CrucibleRecipe("TT_PRISMARINE",new ItemStack(Items.PRISMARINE_SHARD), "paneGlass",new AspectList().add(Aspect.WATER,5).add(Aspect.EARTH,5)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumictinkerer:soul_mould"),new CrucibleRecipe("TT_THAUMIC_MAGNETS",new ItemStack(ModItems.soul_mould),new ItemStack(Items.ENDER_PEARL),new AspectList().add(Aspect.BEAST,5).add(Aspect.MIND,10).add(Aspect.SENSES,10)));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation("thaumictinkerer:spellbinding_cloth"),new CrucibleRecipe(LibResearch.SPELLBINDING_CLOTH,new ItemStack(ModItems.spellbinding_cloth),new ItemStack(ItemsTC.fabric),new AspectList().add(Aspect.EXCHANGE,4).add(Aspect.ENTROPY,6).add(Aspect.MAGIC,10)));
    }

    private static void initializeCraftingRecipes(IForgeRegistry<IRecipe> registry) {
        registry.register(new SpellClothRecipe(ModItems.spellbinding_cloth));
    }

    private static void initializeArcaneRecipes() {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:funnel"), new ShapedArcaneRecipe(defaultGroup, "TT_ESSENTIA_FUNNEL", 60, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.funnel)), "STS", 'S', Blocks.STONE, 'T', "ingotThaumium"));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:dissimulation"),new ShapedArcaneRecipe(defaultGroup,"TT_DISSIMULATION",30,new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),new ItemStack(ModBlocks.dissimulation),"BEB","PBP","BEB",'B',new ItemStack(BlocksTC.stoneArcane),'E',new ItemStack(Items.CLAY_BALL),'P',new ItemStack(Items.PRISMARINE_SHARD)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:transvector_interface"), new ShapedArcaneRecipe(defaultGroup, "TT_TRANSVECTOR_INTERFACE", 200, new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1), new ItemStack(Objects.requireNonNull(ModBlocks.transvector_interface)), "BRB", "LEL", "BDB", 'B', new ItemStack(BlocksTC.stoneArcane), 'R', "dustRedstone", 'L', new ItemStack(Items.DYE, 1, 4), 'E', new ItemStack(Items.ENDER_PEARL), 'D', new ItemStack(Objects.requireNonNull(ModBlocks.dissimulation))));
        // Magnets
        ItemStack airCrystal=new ItemStack(ItemsTC.crystalEssence);
        ((ItemCrystalEssence)ItemsTC.crystalEssence).setAspects(airCrystal,new AspectList().add(Aspect.AIR,1));
        ItemStack earthCrystal=new ItemStack(ItemsTC.crystalEssence);
        ((ItemCrystalEssence)ItemsTC.crystalEssence).setAspects(earthCrystal,new AspectList().add(Aspect.EARTH,1));
        ItemStack focus=new ItemStack(ItemsTC.focus1);
        FocusPackage focusPackage=new FocusPackage();
        focusPackage.addNode(new FocusEffectTelekenesis());
        ItemFocus.setPackage(focus,focusPackage);
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:corporeal_attactor"),new ShapedArcaneRecipe(defaultGroup,"TT_THAUMIC_MAGNETS",200,new AspectList().add(Aspect.AIR,1).add(Aspect.ORDER,1).add(Aspect.EARTH,15).add(Aspect.ENTROPY,1),new ItemStack(Objects.requireNonNull(ModBlocks.mob_magnet))," C ","ACE","GFG",'C', new ItemStack(ItemsTC.ingots, 1, 0),'A',new TTIngredientNBT(airCrystal),'E',new TTIngredientNBT(earthCrystal),'G',new ItemStack(BlocksTC.logGreatwood),'F',new TTFocusIngredient(FocusEffectTelekenesis.class, focus)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation("thaumictinkerer:kinetic_attactor"),new ShapedArcaneRecipe(defaultGroup,"TT_THAUMIC_MAGNETS",20,new AspectList().add(Aspect.AIR,1).add(Aspect.ORDER,1).add(Aspect.EARTH,15).add(Aspect.ENTROPY,1),new ItemStack(Objects.requireNonNull(ModBlocks.magnet))," C ","ACE","GFG",'C',"ingotIron",'A',new TTIngredientNBT(airCrystal),'E',new TTIngredientNBT(earthCrystal),'G',new ItemStack(BlocksTC.logGreatwood),'F',new TTFocusIngredient(FocusEffectTelekenesis.class, focus)));
    }

    private static void initializeInfusionRecipes() {
        // Empty for now
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation("thaumictinkerer:osmotic_enchanter"),new InfusionRecipe("TT_ENCHANTER",new ItemStack(ModBlocks.osmotic_enchanter),1,new AspectList().add(Aspect.ELDRITCH,20).add(Aspect.MIND,10).add(Aspect.ENERGY,20).add(Aspect.MAGIC,50).add(Aspect.VOID,20),new ItemStack(Blocks.ENCHANTING_TABLE),new ItemStack(Blocks.OBSIDIAN),new ItemStack(Blocks.OBSIDIAN),new ItemStack(Blocks.OBSIDIAN),new ItemStack(Blocks.OBSIDIAN),new ItemStack(Blocks.OBSIDIAN),new ItemStack(ModItems.spellbinding_cloth),new ItemStack(ItemsTC.ingots, 1, 0),new ItemStack(ItemsTC.ingots, 1, 0)));
    }
}
