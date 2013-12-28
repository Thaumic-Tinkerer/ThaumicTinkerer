package vazkii.tinkerer.common.item.pipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import vazkii.tinkerer.common.core.handler.ModCreativeTab;
import vazkii.tinkerer.common.lib.LibMisc;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.TransportProxy;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemThaumiumPipe extends ItemPipe {

	public ItemThaumiumPipe(int id) {
		super(id);
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return ("" + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name")).trim();
	}
	
	public static ItemThaumiumPipe registerPipe(int id, Class<? extends Pipe> clazz) {
		ItemThaumiumPipe item = new ItemThaumiumPipe(id);
		item.setUnlocalizedName(LibMisc.MOD_ID + ".thaumiumPipe");
		item.setCreativeTab(ModCreativeTab.INSTANCE);

		GameRegistry.registerItem(item, item.getUnlocalizedName());

		BlockGenericPipe.pipes.put(item.itemID, clazz);

		Pipe dummyPipe = BlockGenericPipe.createPipe(item.itemID);
		if (dummyPipe != null) {
			item.setPipeIconIndex(dummyPipe.getIconIndexForItem());
			TransportProxy.proxy.setIconProviderFromPipe(item, dummyPipe);
		}
		return item;
	}
}