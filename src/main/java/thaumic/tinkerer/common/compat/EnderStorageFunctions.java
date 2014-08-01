package thaumic.tinkerer.common.compat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

public class EnderStorageFunctions {
	public static ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer p, MovingObjectPosition pos) {
		return stack;
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();
		ItemStack focus=wand.getFocusItem(stack);
		if(world.isRemote)
			return stack;
		if(!focus.hasTagCompound())
			focus.setTagCompound(new NBTTagCompound());
		if(pos!=null)
		{
			TileEntity tile = world.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);

			
			if(tile instanceof TileEnderChest && p.isSneaking())
			{
				TileEnderChest chest = (TileEnderChest)tile;

				focus.getTagCompound().setInteger("freq", chest.freq);
				focus.getTagCompound().setString("owner", chest.owner);
				focus.getTagCompound().setBoolean("ender", true);
				wand.setFocus(stack, focus);
				return stack;
			}
			if(world.getBlockId(pos.blockX, pos.blockY, pos.blockZ)==Block.obsidian.blockID && p.isSneaking())
			{

				focus.getTagCompound().setInteger("freq", -1);
				focus.getTagCompound().setString("owner", p.username);
				focus.getTagCompound().setBoolean("ender", false);
				wand.setFocus(stack, focus);
				return stack;
			}
		}
		boolean vanilla=!focus.getTagCompound().getBoolean("ender");
		
		if(wand.consumeAllVis(stack, p, ItemFocusEnderChest.visUsage, true)) {
			if(vanilla)
			{
				p.displayGUIChest(p.getInventoryEnderChest());
				world.playSoundAtEntity(p, "mob.endermen.portal", 1F, 1F);
			}
			else
			{
				int freq=focus.getTagCompound().getInteger("freq");
				((EnderItemStorage) EnderStorageManager.instance(world.isRemote)
						.getStorage(getOwner(focus), freq & 0xFFF, "item"))
						.openSMPGui(p, focus.getDisplayName());
			}
		}

		return stack;
	}

	private static String getOwner(ItemStack stack) {
		return stack.hasTagCompound() ? stack.getTagCompound().getString("owner") : "global";
	}

	public static void addFocusInformation(ItemStack stack, EntityPlayer player, List list,
	                                       boolean par4) {
		if (stack.hasTagCompound() && !stack.getTagCompound().getString("owner").equals("global"))
			list.add(stack.getTagCompound().getString("owner"));
	}

	public static String getSortingHelper(ItemStack focus) {
		String base = "ENDERCHEST";
		if (!focus.hasTagCompound())
			return base + "-VANILLA";
		boolean vanilla = !focus.getTagCompound().getBoolean("ender");
		if (vanilla)
			return base + "-VANILLA";
		int freq = focus.getTagCompound().getInteger("freq");
		return base + Integer.toString(freq) + getOwner(focus);
	}
}
