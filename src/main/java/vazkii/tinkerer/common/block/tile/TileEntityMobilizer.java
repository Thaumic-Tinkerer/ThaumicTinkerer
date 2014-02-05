package vazkii.tinkerer.common.block.tile;

import appeng.api.IAppEngApi;
import appeng.api.Util;
import appeng.api.movable.IMovableTile;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.common.lib.LibBlockIDs;

public class TileEntityMobilizer extends TileEntity {

	public TileEntityMobilizer(World world) {

	}

	public boolean linked;

	public int firstRelayX;
	public int secondRelayX;

	public int firstRelayZ;
	public int secondRelayZ;

	public ForgeDirection movementDirection;

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("Linked", linked);

		nbt.setInteger("FirstRelayX", firstRelayX);
		nbt.setInteger("FirstRelayZ", firstRelayZ);

		nbt.setInteger("SecondRelayX", secondRelayX);
		nbt.setInteger("SecondRelayZ", secondRelayZ);

		nbt.setInteger("Direction", movementDirection.ordinal());
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
		if(!worldObj.isRemote){
			verifyRelay();
			if(linked && worldObj.getTotalWorldTime()%100==0){
				int targetX = xCoord+movementDirection.offsetX;
				int targetZ = zCoord+movementDirection.offsetZ;

				//Switch direction if at end of track
				if(worldObj.getBlockId(targetX, yCoord, targetZ) != 0){
					movementDirection = movementDirection.getOpposite();
					targetX = xCoord+movementDirection.offsetX;
					targetZ = zCoord+movementDirection.offsetZ;
				}
				if(worldObj.getBlockId(targetX, yCoord, targetZ) == 0){

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
						c.setChunkBlockTileEntity( targetX & 0xF, yCoord+1 + yCoord+1, targetZ & 0xF, passenger );

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


			}
		}
	}

}
