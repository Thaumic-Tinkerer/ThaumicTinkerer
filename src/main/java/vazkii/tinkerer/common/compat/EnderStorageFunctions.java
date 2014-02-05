package vazkii.tinkerer.common.compat;

import java.util.List;

import codechicken.enderstorage.api.EnderStorageManager;
import codechicken.enderstorage.storage.item.EnderItemStorage;
import codechicken.enderstorage.storage.item.TileEnderChest;

import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.common.item.foci.ItemFocusEnderChest;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EnderStorageFunctions {
	public static ItemStack onFocusRightClick(ItemStack stack, World world, EntityPlayer p, MovingObjectPosition pos) {
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();
		if(world.isRemote)
			return stack;
		if(pos!=null)
		{
			TileEntity tile = world.getBlockTileEntity(pos.blockX, pos.blockY, pos.blockZ);

			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			if(tile instanceof TileEnderChest && p.isSneaking())
			{
				TileEnderChest chest = (TileEnderChest)tile;

				stack.getTagCompound().setInteger("freq", chest.freq);
				stack.getTagCompound().setString("owner", chest.owner);
				stack.getTagCompound().setBoolean("ender", true);
				return stack;
			}
			if(world.getBlockId(pos.blockX, pos.blockY, pos.blockZ)==Block.obsidian.blockID && p.isSneaking())
			{

				stack.getTagCompound().setInteger("freq", -1);
				stack.getTagCompound().setString("owner", p.username);
				stack.getTagCompound().setBoolean("ender", false);
				return stack;
			}
		}
		boolean vanilla=!stack.getTagCompound().getBoolean("ender");

		if(wand.consumeAllVis(stack, p, ItemFocusEnderChest.visUsage, true)) {
			if(vanilla)
			{
				p.displayGUIChest(p.getInventoryEnderChest());
				world.playSoundAtEntity(p, "mob.endermen.portal", 1F, 1F);
			}
			else
			{
				int freq=stack.getTagCompound().getInteger("freq");
				((EnderItemStorage) EnderStorageManager.instance(world.isRemote)
						.getStorage(getOwner(stack), freq & 0xFFF, "item"))
						.openSMPGui(p, stack.getUnlocalizedName()+".name");
			}
		}

		return stack;
	}

	private static String getOwner(ItemStack stack) {
		return stack.hasTagCompound() ? stack.getTagCompound().getString("owner") : "global";
	}

	public static  void addFocusInformation(ItemStack stack, EntityPlayer player, List list,
			boolean par4) {
		if(stack.hasTagCompound() && !stack.getTagCompound().getString("owner").equals("global"))
			list.add(stack.getTagCompound().getString("owner"));
	}
}
