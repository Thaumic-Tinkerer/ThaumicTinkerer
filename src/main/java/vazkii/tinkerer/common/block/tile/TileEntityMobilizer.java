package vazkii.tinkerer.common.block.tile;

import appeng.api.IAppEngApi;
import appeng.api.Util;
import appeng.api.movable.IMovableTile;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.lib.LibBlockIDs;

public class    TileEntityMobilizer extends TileEntity {

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
		TileEntity te = worldObj.getTileEntity(firstRelayX, yCoord, firstRelayZ);
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
			if(worldObj.getBlock(targetX, yCoord, targetZ) != Block.getBlockFromName("air") || worldObj.getBlock(targetX, yCoord + 1, targetZ) != Block.getBlockFromName("air")){
				movementDirection = movementDirection.getOpposite();
			}
		}
		//Move
		if(linked && worldObj.getTotalWorldTime()%100==1 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)){
			//Cache coordinated
			int targetX = xCoord+movementDirection.offsetX;
			int targetZ = zCoord+movementDirection.offsetZ;
			//Check for abandoned TEs
			if(worldObj.getBlock(xCoord, yCoord, zCoord) != ModBlocks.mobilizer)
			{
				return;
			}
			//Check if the space the mobilizer will move into is empty
			if((worldObj.isAirBlock(targetX, yCoord, targetZ) || worldObj.getBlock(targetX, yCoord, targetZ).isAir(worldObj, targetX, yCoord, targetZ)
					&& (worldObj.isAirBlock(xCoord, yCoord+1, zCoord) || worldObj.isAirBlock(targetX, yCoord+1, targetZ)|| worldObj.getBlock(targetX, yCoord+1, targetZ).isAir(worldObj, targetX, yCoord+1, targetZ)))){

				//Move Entities
				//List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+3, zCoord+1));
				//System.out.print(entities);
				//for(Entity e: entities){
				//	e.setPosition(e.posX+movementDirection.offsetX, e.posY, e.posZ+movementDirection.offsetZ);
				//}

				//Move the block on top of the mobilizer
				if(!worldObj.isRemote){

					TileEntity passenger = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
					IAppEngApi api = Util.getAppEngApi();

					//Prevent the passenger from popping off. Not sent to clients.
					worldObj.setBlock(targetX, yCoord, targetZ, Block.getBlockFromName("stone"), 0, 0);
					//Move non-TE blocks
					Block passengerId=worldObj.getBlock(xCoord, yCoord+1, zCoord);

					if(worldObj.isAirBlock(xCoord, yCoord+1, zCoord) || passengerId.canPlaceBlockAt(worldObj, targetX, yCoord+1, targetZ)){

						if(passenger==null){
							if(passengerId !=Block.getBlockFromName("bedrock") && passengerId != Block.getBlockFromName("")){
								worldObj.setBlock(targetX, yCoord+1, targetZ, passengerId, worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord), 3);
								if(passengerId != Block.getBlockFromName("air") && passengerId != Block.getBlockFromName("piston_head")){
									worldObj.setBlock(xCoord, yCoord+1, zCoord, Block.getBlockFromName("air"), 0, 2);
								}
							}
							//If AE is installed, use its handler
						}else if(api != null){
							if(api.getMovableRegistry().askToMove(passenger)){
								worldObj.setBlock(targetX, yCoord + 1, targetZ, worldObj.getBlock(xCoord, yCoord + 1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord), 3);
								passenger.invalidate();
								worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
								api.getMovableRegistry().getHandler(passenger).moveTile(passenger, worldObj, targetX, yCoord+1, targetZ);
								api.getMovableRegistry().doneMoveing(passenger);
								passenger.validate();
							}

							//Handler IMovableTiles and vanilla TEs without AE
						}else if(passenger instanceof IMovableTile ||passenger.getClass().getName().startsWith("net.minecraft.tileentity")){
							boolean imovable=passenger instanceof IMovableTile;
							if(imovable)
								((IMovableTile) passenger).prepareToMove();
							worldObj.setBlock(targetX, yCoord + 1, targetZ, worldObj.getBlock(xCoord, yCoord + 1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord), 3);
							passenger.invalidate();
							worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);

							//IMovableHandler default code
							Chunk c = worldObj.getChunkFromBlockCoords( targetX, targetZ );

							c.func_150812_a( targetX & 0xF, yCoord+1, targetZ & 0xF, passenger );

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
					worldObj.removeTileEntity(xCoord, yCoord, zCoord);
					worldObj.setBlock(xCoord, yCoord, zCoord, Block.getBlockFromName("air"), 0, 2);
					worldObj.setBlock(targetX, yCoord, targetZ, ModBlocks.mobilizer);

					int oldX=xCoord;
					int oldZ=zCoord;

					this.xCoord=targetX;
					this.zCoord=targetZ;
					this.validate();
					worldObj.addTileEntity(this);
					worldObj.removeTileEntity(oldX, yCoord, oldZ);


					worldObj.notifyBlockChange(oldX, yCoord, oldZ, Block.getBlockFromName("air"));

				}

			}

		}
	}

}
