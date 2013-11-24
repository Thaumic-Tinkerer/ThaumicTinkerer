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
 * File Created @ [Nov 24, 2013, 6:40:49 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.transvector;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public abstract class TileTransvector extends TileEntity {

	private static final String TAG_X_TARGET = "xt";
	private static final String TAG_Y_TARGET = "yt";
	private static final String TAG_Z_TARGET = "zt";
	private static final String TAG_CHEATY_MODE = "cheatyMode";
	private static final String TAG_CAMO = "camo";
	private static final String TAG_CAMO_META = "camoMeta";

	public int camo;
	public int camoMeta;

	public int x, y, z;
	private boolean cheaty;

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);

		par1nbtTagCompound.setInteger(TAG_X_TARGET, x);
		par1nbtTagCompound.setInteger(TAG_Y_TARGET, y);
		par1nbtTagCompound.setInteger(TAG_Z_TARGET, z);
		par1nbtTagCompound.setBoolean(TAG_CHEATY_MODE, cheaty);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);

		x = par1nbtTagCompound.getInteger(TAG_X_TARGET);
		y = par1nbtTagCompound.getInteger(TAG_Y_TARGET);
		z = par1nbtTagCompound.getInteger(TAG_Z_TARGET);
		cheaty = par1nbtTagCompound.getBoolean(TAG_CHEATY_MODE);
	}

	public void writeCustomNBT(NBTTagCompound cmp) {
		cmp.setInteger(TAG_CAMO, camo);
		cmp.setInteger(TAG_CAMO_META, camoMeta);
	}

	public void readCustomNBT(NBTTagCompound cmp) {
		camo = cmp.getInteger(TAG_CAMO);
		camoMeta = cmp.getInteger(TAG_CAMO_META);
	}

	final TileEntity getTile() {
		if(!worldObj.blockExists(x, y, z))
			return null;

		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);

		if(tile == null || (Math.abs(x - xCoord) > getMaxDistance() || Math.abs(y - yCoord) > getMaxDistance() || Math.abs(z - zCoord) > getMaxDistance()) && !cheaty) {
			y = -1;
			return null;
		}

		return tile;
	}

	public abstract int getMaxDistance();

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeCustomNBT(nbttagcompound);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, -999, nbttagcompound);
	}

	@Override
	public void onDataPacket(INetworkManager manager, Packet132TileEntityData packet) {
		super.onDataPacket(manager, packet);
		readCustomNBT(packet.data);
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
}
