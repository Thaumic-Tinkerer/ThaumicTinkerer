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

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.codechicken.lib.vec.Vector3;
import thaumcraft.common.config.ConfigBlocks;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.transvector.BlockTransvectorDislocator;
import vazkii.tinkerer.common.lib.LibFeatures;

import java.util.List;

public class TileTransvectorDislocator extends TileTransvector {

	private static final String TAG_ORIENTATION = "orientation";
	public int orientation;
	private int cooldown = 0;
	private boolean pulseStored = false;

	class BlockData {

		Block block;
		int meta;
		NBTTagCompound tile;

		ChunkCoordinates coords;

		public BlockData(Block block, int meta, TileEntity tile, ChunkCoordinates coords) {
			this.block = block;
			this.meta = meta;

			if (tile != null) {
				NBTTagCompound cmp = new NBTTagCompound();
				tile.writeToNBT(cmp);
				this.tile = cmp;
			}
			;

			this.coords = coords;
		}

		public BlockData(ChunkCoordinates coords) {
			this(worldObj.getBlock(coords.posX, coords.posY, coords.posZ), worldObj.getBlockMetadata(coords.posX, coords.posY, coords.posZ), worldObj.getTileEntity(coords.posX, coords.posY, coords.posZ), coords);
		}

		public void clearTileEntityAt() {
			if (block != null) {
				TileEntity tileToSet = block.createTileEntity(worldObj, meta);
				worldObj.setTileEntity(coords.posX, coords.posY, coords.posZ, tileToSet);
			}
		}

		public void setTo(ChunkCoordinates coords) {
			worldObj.setBlock(coords.posX, coords.posY, coords.posZ, block, meta, 1 | 2);

			TileEntity tile = this.tile == null ? null : TileEntity.createAndLoadEntity(this.tile);

			worldObj.setTileEntity(coords.posX, coords.posY, coords.posZ, tile);

			if (tile != null) {
				tile.xCoord = coords.posX;
				tile.yCoord = coords.posY;
				tile.zCoord = coords.posZ;
				tile.updateContainingBlockInfo();
			}

			if (block != null)
				block.onNeighborBlockChange(worldObj, coords.posX, coords.posY, coords.posZ, ThaumicTinkerer.TTRegistry.getFirstBlockFromClass(BlockTransvectorDislocator.class));

			worldObj.setBlockMetadataWithNotify(coords.posX, coords.posY, coords.posZ, meta, 3);

		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		cooldown = Math.max(0, cooldown - 1);
		if (cooldown == 0 && pulseStored) {
			pulseStored = false;
			receiveRedstonePulse();
		}
	}

	public void receiveRedstonePulse() {
		getTile(); // sanity check

		if (y < 0)
			return;

		if (cooldown > 0) {
			pulseStored = true;
			return;
		}

		ChunkCoordinates endCoords = new ChunkCoordinates(x, y, z);
		ChunkCoordinates targetCoords = getBlockTarget();

		if (worldObj.blockExists(x, y, z)) {
			BlockData endData = new BlockData(endCoords);
			BlockData targetData = new BlockData(targetCoords);

			if (checkBlock(targetCoords) && checkBlock(endCoords)) {
				endData.clearTileEntityAt();
				targetData.clearTileEntityAt();

				endData.setTo(targetCoords);
				targetData.setTo(endCoords);
			}
		}

		List<Entity> entitiesAtEnd = getEntitiesAtPoint(endCoords);
		List<Entity> entitiesAtTarget = getEntitiesAtPoint(targetCoords);

		Vector3 targetToEnd = asVector(targetCoords, endCoords);
		Vector3 endToTarget = asVector(endCoords, targetCoords);

		for (Entity entity : entitiesAtEnd)
			moveEntity(entity, endToTarget);
		for (Entity entity : entitiesAtTarget)
			moveEntity(entity, targetToEnd);

		cooldown = 10;
	}

	private boolean checkBlock(ChunkCoordinates coords) {
		Block block = worldObj.getBlock(coords.posX, coords.posY, coords.posZ);
		int meta = worldObj.getBlockMetadata(coords.posX, coords.posY, coords.posZ);

		return !(block == ConfigBlocks.blockAiry && meta == 0) && !ThaumcraftApi.portableHoleBlackList.contains(block) && block != null && block.getBlockHardness(worldObj, coords.posX, coords.posY, coords.posZ) != -1F || block != Blocks.air;
	}

	private List<Entity> getEntitiesAtPoint(ChunkCoordinates coords) {
		return worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(coords.posX, coords.posY, coords.posZ, coords.posX + 1, coords.posY + 1, coords.posZ + 1));
	}

	private Vector3 asVector(ChunkCoordinates source, ChunkCoordinates target) {
		return new Vector3(target.posX, target.posY, target.posZ).subtract(new Vector3(source.posX, source.posY, source.posZ));
	}

	private void moveEntity(Entity entity, Vector3 vec) {
		if (entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entity;
			player.playerNetServerHandler.setPlayerLocation(entity.posX + vec.x, entity.posY + vec.y, entity.posZ + vec.z, player.rotationYaw, player.rotationPitch);
		} else entity.setPosition(entity.posX + vec.x, entity.posY + vec.y, entity.posZ + vec.z);
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
