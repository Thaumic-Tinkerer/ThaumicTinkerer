/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [8 Sep 2013, 19:35:02 (GMT)]
 */
package vazkii.tinkerer.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvector;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorDislocator;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import vazkii.tinkerer.common.core.helper.ItemNBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemConnector extends ItemMod {

	private static final String TAG_POS_X = "posx";
	private static final String TAG_POS_Y = "posy";
	private static final String TAG_POS_Z = "posz";

	public ItemConnector(int par1) {
		super(par1);

		setMaxStackSize(1);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if (par3World.isRemote)
			return false;

		TileEntity tile = par3World.getBlockTileEntity(par4, par5, par6);
		boolean isInterface = tile instanceof TileTransvectorInterface;
		
		if (getY(par1ItemStack) == -1) {
			if (tile != null && tile instanceof TileTransvector) {
				setX(par1ItemStack, par4);
				setY(par1ItemStack, par5);
				setZ(par1ItemStack, par6);

				if(par3World.isRemote)
					par2EntityPlayer.swingItem();

				playSound(par3World, par4, par5, par6);
				par2EntityPlayer.addChatMessage("ttmisc.connector.set");
			} else
				par2EntityPlayer.addChatMessage("ttmisc.connector.notinterf");
		} else {
			int x = getX(par1ItemStack);
			int y = getY(par1ItemStack);
			int z = getZ(par1ItemStack);

			TileEntity tile1 = par3World.getBlockTileEntity(x, y, z);
			if (tile1 == null || !(tile1 instanceof TileTransvectorInterface)) {
				setY(par1ItemStack, -1);

				par2EntityPlayer.addChatMessage("ttmisc.connector.notpresent");
			} else {
				TileTransvector trans = (TileTransvector) tile1;
				
				if (tile != null && tile1 instanceof TileTransvectorInterface && tile instanceof TileTransvectorInterface) {
					par2EntityPlayer.addChatMessage("ttmisc.connector.interffail");
					return true;
				}

				if(Math.abs(x - par4) > trans.getMaxDistance() || Math.abs(y - par5) > trans.getMaxDistance() || Math.abs(z - par6) > trans.getMaxDistance()) {
					par2EntityPlayer.addChatMessage("ttmisc.connector.toofar");
					return true;
				}

				trans.x = par4;
				trans.y = par5;
				trans.z = par6;

				setY(par1ItemStack, -1);

				if(par3World.isRemote)
					par2EntityPlayer.swingItem();

				playSound(par3World, par4, par5, par6);
				par2EntityPlayer.addChatMessage("ttmisc.connector.complete");
			}
		}

		return true;
	}

	private void playSound(World world, int x, int y, int z) {
		if(!world.isRemote)
			world.playSoundEffect(x, y, z, "random.orb", 0.8F, 1F);
	}

	public static void setX(ItemStack stack, int x) {
		ItemNBTHelper.setInt(stack, TAG_POS_X, x);
	}

	public static void setY(ItemStack stack, int y) {
		ItemNBTHelper.setInt(stack, TAG_POS_Y, y);
	}

	public static void setZ(ItemStack stack, int z) {
		ItemNBTHelper.setInt(stack, TAG_POS_Z, z);
	}

	public static int getX(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_POS_X, 0);
	}

	public static int getY(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_POS_Y, -1);
	}

	public static int getZ(ItemStack stack) {
		return ItemNBTHelper.getInt(stack, TAG_POS_Z, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

}
