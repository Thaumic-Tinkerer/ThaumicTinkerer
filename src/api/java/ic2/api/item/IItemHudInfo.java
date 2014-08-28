package ic2.api.item;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IItemHudInfo {
    /*
    Add Info to Nano- and Quantum-Suit Helm Hud
    for itemStack

        @Override
        public List<String> getHudInfo(ItemStack itemStack) {
        List<String> info = new LinkedList<String>();
        info.add("i am a Cool Item");
        info.add("and have Cool info");
        return info;
    }


	 */

    public List<String> getHudInfo(ItemStack itemStack);
}
