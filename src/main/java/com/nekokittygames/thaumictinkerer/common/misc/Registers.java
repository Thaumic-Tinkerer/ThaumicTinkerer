package com.nekokittygames.thaumictinkerer.common.misc;

import com.nekokittygames.thaumictinkerer.common.recipes.ModRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber
public class Registers {

    @SubscribeEvent
    public static void registerVanillaRecipes(RegistryEvent.Register<IRecipe> event) {
        ModRecipes.initializeRecipes(event.getRegistry());
    }
}
