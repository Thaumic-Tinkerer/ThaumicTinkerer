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

import vazkii.tinkerer.common.lib.LibFeatures;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileTransvector extends TileEntity {

	private static final String TAG_X_TARGET = "xt";
	private static final String TAG_Y_TARGET = "yt";
	private static final String TAG_Z_TARGET = "zt";
	private static final String TAG_CHEATY_MODE = "cheatyMode";

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
}
