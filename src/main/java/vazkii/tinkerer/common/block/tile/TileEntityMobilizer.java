package vazkii.tinkerer.common.block.tile;

import appeng.api.IAppEngApi;
import appeng.api.Util;
import appeng.api.WorldCoord;
import appeng.api.movable.IMovableTile;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.lib.LibBlockIDs;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMobilizer extends TileEntity {

	public boolean linked;

	public int firstRelayX;
	public int secondRelayX;

	public int firstRelayZ;
	public int secondRelayZ;
	public boolean dead=false;;

	public ForgeDirection movementDirection;

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("Linked", linked);

		nbt.setInteger("FirstRelayX", firstRelayX);
		nbt.setInteger("FirstRelayZ", firstRelayZ);

		nbt.setInteger("SecondRelayX", secondRelayX);
		nbt.setInteger("SecondRelayZ", secondRelayZ);

		nbt.setInteger("Direction", movementDirection != null ? movementDirection.ordinal() : 0);

		nbt.setBoolean("DelayedMove", delayedMove);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.linked = nbt.getBoolean("Linked");

		this.firstRelayX = nbt.getInteger("FirstRelayX");
		this.firstRelayZ = nbt.getInteger("FirstRelayZ");

		this.secondRelayX = nbt.getInteger("SecondRelayX");
		this.secondRelayZ = nbt.getInteger("SecondRelayZ");

		this.delayedMove = nbt.getBoolean("DelayedMove");

		movementDirection=ForgeDirection.VALID_DIRECTIONS[nbt.getInteger("Direction")];
	}



	public void verifyRelay(){
		TileEntity te = worldObj.getBlockTileEntity(firstRelayX, yCoord, firstRelayZ);
		if(te instanceof TileEntityRelay){
			((TileEntityRelay) te).verifyPartner();
		}
		if(!(linked && te instanceof TileEntityRelay && ((TileEntityRelay) te).partnerX == this.secondRelayX && ((TileEntityRelay) te).partnerZ == this.secondRelayZ)){
			linked=false;
		}
	}

	//For chaining multiple mobilizers
	public boolean delayedMove;

	public void updateEntity(){
		if(dead)
			return;
		verifyRelay();
		if(linked && worldObj.getTotalWorldTime()%100==0 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){

			int targetX = xCoord+movementDirection.offsetX;
			int targetZ = zCoord+movementDirection.offsetZ;


			//Switch direction if at end of track

			int idFront=worldObj.getBlockId(targetX, yCoord, targetZ);
			int idTop=worldObj.getBlockId(targetX, yCoord+1, targetZ);
			if((idFront != 0 && !Block.blocksList[idFront].isAirBlock(worldObj, targetX, yCoord, targetZ) && idFront != LibBlockIDs.idMobilizer)
					||((idTop != 0 && !Block.blocksList[idTop].isAirBlock(worldObj, targetX, yCoord+1, targetZ)))){
				ArrayList<WorldCoord> switched = new ArrayList<WorldCoord>();
				ArrayList<WorldCoord> toSwitch = new ArrayList<WorldCoord>();
				toSwitch.add(new WorldCoord(xCoord, yCoord, zCoord));
				while(toSwitch.size() > 0){
					WorldCoord curr = toSwitch.remove(0);
					for(ForgeDirection d:ForgeDirection.VALID_DIRECTIONS){
						WorldCoord next = curr.copy().add(d, 1);
						if(!switched.contains(next) && worldObj.getBlockId(next.x, next.y, next.z) == LibBlockIDs.idMobilizer){
							toSwitch.add(next);
						}
					}
					TileEntity te = worldObj.getBlockTileEntity(curr.x, curr.y, curr.z);
					if(te instanceof TileEntityMobilizer){
						((TileEntityMobilizer) te).movementDirection = ((TileEntityMobilizer) te).movementDirection.getOpposite();
					}
					switched.add(curr);
				}

			}
		}
		if(linked && (delayedMove || worldObj.getTotalWorldTime()%100==1) && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
			delayedMove=false;
			int targetX = xCoord+movementDirection.offsetX;
			int targetZ = zCoord+movementDirection.offsetZ;
			if(worldObj.getBlockId(xCoord, yCoord, zCoord) != ModBlocks.mobilizer.blockID)
			{
				return;
			}

			if((worldObj.getBlockId(targetX, yCoord, targetZ) == 0|| Block.blocksList[worldObj.getBlockId(targetX, yCoord, targetZ)].isAirBlock(worldObj, targetX, yCoord, targetZ))
					&& (worldObj.getBlockId(targetX, yCoord+1, targetZ) == 0|| Block.blocksList[worldObj.getBlockId(targetX, yCoord+1, targetZ)].isAirBlock(worldObj, targetX, yCoord+1, targetZ))){

				//Move Entities
				List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+3, zCoord+1));
				System.out.print(entities);
				for(Entity e: entities){
					e.setPosition(e.posX+movementDirection.offsetX, e.posY, e.posZ+movementDirection.offsetZ);
				}

				if(!worldObj.isRemote){
					//Move Passenger

					TileEntity passenger = worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);
					IAppEngApi api = Util.getAppEngApi();
					if(passenger==null){
						worldObj.setBlock(targetX, yCoord+1, targetZ, worldObj.getBlockId(xCoord, yCoord+1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord), 3);
						worldObj.setBlock(xCoord, yCoord+1, zCoord, 0);
					}else if(api != null){
						if(api.getMovableRegistry().askToMove(passenger)){
							worldObj.setBlock(targetX, yCoord + 1, targetZ, worldObj.getBlockId(xCoord, yCoord + 1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord), 3);
							passenger.invalidate();
							worldObj.setBlock(xCoord, yCoord + 1, zCoord, 0);
							api.getMovableRegistry().getHandler(passenger).moveTile(passenger, worldObj, targetX, yCoord+1, targetZ);
							api.getMovableRegistry().doneMoveing(passenger);
							passenger.validate();
						}

					}else if(passenger instanceof IMovableTile || passenger.getClass().getName().startsWith("net.minecraft.tileentity")){
						boolean imovable=passenger instanceof IMovableTile;
						if(imovable)
							((IMovableTile) passenger).prepareToMove();
						worldObj.setBlock(targetX, yCoord + 1, targetZ, worldObj.getBlockId(xCoord, yCoord + 1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord), 3);
						passenger.invalidate();
						worldObj.setBlock(xCoord, yCoord + 1, zCoord, 0);

						//IMovableHandler default code
						Chunk c = worldObj.getChunkFromBlockCoords( targetX, targetZ );
						c.setChunkBlockTileEntity( targetX & 0xF, yCoord+1, targetZ & 0xF, passenger );

						if ( c.isChunkLoaded )
						{
							worldObj.addTileEntity(passenger);
							worldObj.markBlockForUpdate(targetX, yCoord+1, targetZ);
						}
						if(imovable)
							((IMovableTile) passenger).doneMoving();
						passenger.validate();

					}
					//Move self
					this.invalidate();
					worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
					worldObj.setBlock(xCoord, yCoord, zCoord, 0);
					worldObj.setBlock(targetX, yCoord, targetZ, LibBlockIDs.idMobilizer);

					int oldX=xCoord;
					int oldZ=zCoord;

					this.xCoord=targetX;
					this.zCoord=targetZ;
					this.validate();
					worldObj.addTileEntity(this);
					worldObj.removeBlockTileEntity(oldX, yCoord, oldZ);

				}

			}else{
				delayedMove=false;
				if(worldObj.getBlockId(targetX, yCoord, targetZ) == LibBlockIDs.idMobilizer){
					delayedMove=true;
				}
			}

		}
	}

}
