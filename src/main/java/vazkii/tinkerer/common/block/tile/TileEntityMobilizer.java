package vazkii.tinkerer.common.block.tile;

import appeng.api.IAppEngApi;
import appeng.api.Util;
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
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.linked = nbt.getBoolean("Linked");

		this.firstRelayX = nbt.getInteger("FirstRelayX");
		this.firstRelayZ = nbt.getInteger("FirstRelayZ");

		this.secondRelayX = nbt.getInteger("SecondRelayX");
		this.secondRelayZ = nbt.getInteger("SecondRelayZ");

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

	public void updateEntity(){
		//Check for ghost TEs
		if(dead)
			return;
		//Make sure the relays haven't been broken
		verifyRelay();
		//Bounce
		if(linked && worldObj.getTotalWorldTime()%100==0 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
			//Target coordinates to check
			int targetX = xCoord+movementDirection.offsetX;
			int targetZ = zCoord+movementDirection.offsetZ;
			//Switch direction if at end of track
			if(worldObj.getBlockId(targetX, yCoord, targetZ) != 0 || worldObj.getBlockId(targetX, yCoord + 1, targetZ) != 0){
				movementDirection = movementDirection.getOpposite();
			}
		}
		//Move
		if(linked && worldObj.getTotalWorldTime()%100==1 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
			//Cache coordinated
			int targetX = xCoord+movementDirection.offsetX;
			int targetZ = zCoord+movementDirection.offsetZ;
			//Check for abandoned TEs
			if(worldObj.getBlockId(xCoord, yCoord, zCoord) != ModBlocks.mobilizer.blockID)
			{
				return;
			}
			//Check if the space the mobilizer will move into is empty
			if((worldObj.getBlockId(targetX, yCoord, targetZ) == 0|| Block.blocksList[worldObj.getBlockId(targetX, yCoord, targetZ)].isAirBlock(worldObj, targetX, yCoord, targetZ))
					&& (worldObj.getBlockId(xCoord, yCoord+1, zCoord) ==0 || worldObj.getBlockId(targetX, yCoord+1, targetZ) == 0|| Block.blocksList[worldObj.getBlockId(targetX, yCoord+1, targetZ)].isAirBlock(worldObj, targetX, yCoord+1, targetZ))){

				//Move Entities
				List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+3, zCoord+1));
				System.out.print(entities);
				for(Entity e: entities){
					e.setPosition(e.posX+movementDirection.offsetX, e.posY, e.posZ+movementDirection.offsetZ);
				}

				//Move the block on top of the mobilizer
				if(!worldObj.isRemote){

					TileEntity passenger = worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);
					IAppEngApi api = Util.getAppEngApi();

					//Prevent the passenger from popping off. Not sent to clients.
					worldObj.setBlock(targetX, yCoord, targetZ, 1, 0, 0);
					//Move non-TE blocks
					int passengerId=worldObj.getBlockId(xCoord, yCoord+1, zCoord);

					if(passengerId == 0 || Block.blocksList[passengerId].canPlaceBlockAt(worldObj, targetX, yCoord+1, targetZ)){

						if(passenger==null){
							if(passengerId != 7 && passengerId != 34){
								worldObj.setBlock(targetX, yCoord+1, targetZ, worldObj.getBlockId(xCoord, yCoord+1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord), 3);
								worldObj.setBlock(xCoord, yCoord+1, zCoord, 0, 0, 2);
							}
							//If AE is installed, use its handler
						}else if(api != null){
							if(api.getMovableRegistry().askToMove(passenger)){
								worldObj.setBlock(targetX, yCoord + 1, targetZ, worldObj.getBlockId(xCoord, yCoord + 1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord), 3);
								passenger.invalidate();
								worldObj.setBlock(xCoord, yCoord + 1, zCoord, 0);
								api.getMovableRegistry().getHandler(passenger).moveTile(passenger, worldObj, targetX, yCoord+1, targetZ);
								api.getMovableRegistry().doneMoveing(passenger);
								passenger.validate();
							}

							//Handler IMovableTiles and vanilla TEs without AE
						}else if(passenger instanceof IMovableTile ||passenger.getClass().getName().startsWith("net.minecraft.tileentity")){
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
					}
					//Move self
					this.invalidate();
					worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
					worldObj.setBlock(xCoord, yCoord, zCoord, 0, 0, 2);
					worldObj.setBlock(targetX, yCoord, targetZ, LibBlockIDs.idMobilizer);

					int oldX=xCoord;
					int oldZ=zCoord;

					this.xCoord=targetX;
					this.zCoord=targetZ;
					this.validate();
					worldObj.addTileEntity(this);
					worldObj.removeBlockTileEntity(oldX, yCoord, oldZ);

					worldObj.notifyBlockChange(oldX, yCoord, oldZ, 0);

				}

			}

		}
	}

}
