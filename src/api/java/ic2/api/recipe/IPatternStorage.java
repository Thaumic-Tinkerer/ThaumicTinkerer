package ic2.api.recipe;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IPatternStorage {
    boolean addPattern(ItemStack itemstack);

    List<ItemStack> getPatterns();
}
