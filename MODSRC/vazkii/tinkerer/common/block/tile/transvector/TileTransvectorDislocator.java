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
 * File Created @ [Nov 24, 2013, 6:48:04 PM (GMT)]
 */
package vazkii.tinkerer.common.block.tile.transvector;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.common.lib.LibFeatures;

public class TileTransvectorDislocator extends TileTransvector {

	private static final String TAG_ORIENTATION = "orientation";

	public int orientation;

	class BlockData {
		
		int id;
		int meta;
		TileEntity tile;
		
		ChunkCoordinates coords;
		
		public BlockData(int id, int meta, TileEntity tile, ChunkCoordinates coords) {
			this.id = id;
			this.meta = meta;
			this.tile = tile;
			
			this.coords = coords;
		}
		
		public BlockData(ChunkCoordinates coords) {
			this(worldObj.getBlockId(coords.posX, coords.posY, coords.posZ), worldObj.getBlockMetadata(coords.posX, coords.posY, coords.posZ), worldObj.getBlockTileEntity(coords.posX, coords.posY, coords.posZ), coords);
		}
		
		public void setTo(ChunkCoordinates coords) {
			worldObj.setBlock(coords.posX, coords.posY, coords.posZ, id, meta, 1 | 2);
			worldObj.setBlockTileEntity(coords.posX, coords.posY, coords.posZ, tile);
			
			if(tile != null) {
				tile.xCoord = coords.posX;
				tile.yCoord = coords.posY;
				tile.zCoord = coords.posZ;
			}
		}
	}
	
	public void receiveRedstonePulse() {
		ChunkCoordinates target = getBlockTarget();
		if(y < 0)
			return;
		
		if(worldObj.blockExists(x, y, z)) {
			ChunkCoordinates endCoords = new ChunkCoordinates(x, y, z);
			ChunkCoordinates targetCoords = getBlockTarget();
			
			BlockData endData = new BlockData(endCoords);
			BlockData targetData = new BlockData(targetCoords);
			
			endData.setTo(targetCoords);
			targetData.setTo(endCoords);
		}
	}
	
	public ChunkCoordinates getBlockTarget() {
		ForgeDirection dir = ForgeDirection.getOrientation(orientation);
		return new ChunkCoordinates(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound cmp) {
		super.readCustomNBT(cmp);

		orientation = cmp.getInteger(TAG_ORIENTATION);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound cmp) {
		super.writeCustomNBT(cmp);

		cmp.setInteger(TAG_ORIENTATION, orientation);
	}

	@Override
	public int getMaxDistance() {
		return LibFeatures.DISLOCATOR_DISTANCE;
	}

	@Override
	boolean tileRequiredAtLink() {
		return false;
	}

}
