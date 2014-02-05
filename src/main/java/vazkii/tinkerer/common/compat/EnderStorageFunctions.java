package vazkii.tinkerer.common.compat;

import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.item.foci.ItemFocusEnderChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EnderStorageFunctions {
	public static ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer p, MovingObjectPosition pos) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();

		if(wand.consumeAllVis(stack, p, ItemFocusEnderChest.visUsage, true)) {
			p.displayGUIChest(p.getInventoryEnderChest());
			world.playSoundAtEntity(p, "mob.endermen.portal", 1F, 1F);
		}

		return stack;
	}
}
