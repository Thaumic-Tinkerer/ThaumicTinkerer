package ic2.api.recipe;

import net.minecraft.item.ItemStack;

import java.util.Map;

public interface IScrapboxManager {
    void addDrop(ItemStack drop, float rawChance);

    ItemStack getDrop(ItemStack input, boolean adjustInput);

    Map<ItemStack, Float> getDrops();
}
