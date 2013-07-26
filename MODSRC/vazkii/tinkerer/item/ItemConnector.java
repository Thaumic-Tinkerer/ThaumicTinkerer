/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [26 Jul 2013, 02:22:00 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.tile.TileEntityInterface;
import vazkii.tinkerer.util.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemConnector extends ItemMod {

	public ItemConnector(int par1) {
		super(par1);

		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (par3World.isRemote)
			return false;

		TileEntity tile = par3World.getBlockTileEntity(par4, par5, par6);

		// TODO Needs polish!
		
		if (getY(par1ItemStack) == -1) {
			if (tile != null && tile instanceof TileEntityInterface) {
				setX(par1ItemStack, par4);
				setY(par1ItemStack, par5);
				setZ(par1ItemStack, par6);

				par2EntityPlayer.addChatMessage("Location Bound!");
			} else
				par2EntityPlayer.addChatMessage("Not a " + LibBlockNames.INTERFACE_D + ".");
		} else {
			if (tile != null && tile instanceof TileEntityInterface) {
				par2EntityPlayer.addChatMessage("Can't bind an Interface to another.");
				return true;
			}

			int x = getX(par1ItemStack);
			int y = getY(par1ItemStack);
			int z = getZ(par1ItemStack);

			TileEntity tile1 = par3World.getBlockTileEntity(x, y, z);
			if (tile1 == null || !(tile1 instanceof TileEntityInterface)) {
				setY(par1ItemStack, -1);

				par2EntityPlayer.addChatMessage(LibBlockNames.INTERFACE_D + " isn't present.");
			} else {
				((TileEntityInterface) tile1).x = par4;
				((TileEntityInterface) tile1).y = par5;
				((TileEntityInterface) tile1).z = par6;

				setY(par1ItemStack, -1);

				par2EntityPlayer.addChatMessage("Locations bound!");
			}
		}

		return true;
	}

	public static void setX(ItemStack stack, int x) {
		ItemNBTHelper.setInt(stack, "posx", x);
	}

	public static void setY(ItemStack stack, int y) {
		ItemNBTHelper.setInt(stack, "posy", y);
	}

	public static void setZ(ItemStack stack, int z) {
		ItemNBTHelper.setInt(stack, "posz", z);
	}

	public static int getX(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, "posx", 0);
	}

	public static int getY(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, "posy", -1);
	}

	public static int getZ(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, "posz", 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

}
