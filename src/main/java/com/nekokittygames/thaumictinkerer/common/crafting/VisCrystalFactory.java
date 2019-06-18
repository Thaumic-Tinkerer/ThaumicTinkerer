package com.nekokittygames.thaumictinkerer.common.crafting;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class VisCrystalFactory implements IRecipeFactory
{

    @Override
    public IRecipe parse(JsonContext jsonContext, JsonObject jsonObject) {

        // Get Primer
        final CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
        final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(jsonObject, "result"), jsonContext);
        return null;
    }
}
