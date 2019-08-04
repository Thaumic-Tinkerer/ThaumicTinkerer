package com.nekokittygames.thaumictinkerer.common.recipes.ing;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.items.casters.ItemFocus;

import javax.annotation.Nullable;
import java.util.Objects;

public class TTFocusIngredient extends Ingredient {
    private Class<? extends FocusEffect> effect;
    public TTFocusIngredient(Class<? extends FocusEffect> effect,ItemStack... ingredient) {
        super(ingredient);
        this.effect=effect;
    }

    @Override
    public boolean apply(@Nullable ItemStack ingredient) {
        if(!(Objects.requireNonNull(ingredient).getItem() instanceof ItemFocus))
            return false;
        if(ItemFocus.getPackage(ingredient)==null)
            return false;
        for (FocusEffect eff:ItemFocus.getPackage(ingredient).getFocusEffects()) {
            if(eff.getClass().equals(effect))
            {
                return true;
            }
        }
        return false;
    }
}
