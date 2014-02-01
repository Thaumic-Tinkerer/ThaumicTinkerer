package vazkii.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.linked = nbt.getBoolean("Linked");

		this.firstRelayX = nbt.getInteger("FirstRelayX");
		this.firstRelayZ = nbt.getInteger("FirstRelayZ");

		this.secondRelayX = nbt.getInteger("SecondRelayX");
		this.secondRelayZ = nbt.getInteger("SecondRelayZ");
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
		verifyRelay();

		if(linked && worldObj.getTotalWorldTime()%20==0){


			int targetX = xCoord+movementDirection.offsetX;
			int targetZ = zCoord+movementDirection.offsetZ;
			//Switch direction if at end of track
			if(worldObj.getBlockId(targetX, yCoord, targetZ) != 0){
				movementDirection = movementDirection.getOpposite();
				targetX = xCoord+movementDirection.offsetX;
				targetZ = zCoord+movementDirection.offsetZ;
			}


			if(worldObj.getBlockId(targetX, yCoord, targetZ) == 0){
				//Move self
				worldObj.setBlock(targetX, yCoord, targetZ, LibBlockIDs.idMobilizer);
				worldObj.setBlockTileEntity(xCoord, yCoord, zCoord, this);
				worldObj.setBlock(xCoord, yCoord, zCoord, 0);
				this.xCoord = targetX;
				this.zCoord = targetZ;

				//Move Passenger
				worldObj.setBlock(targetX, yCoord+1, targetZ, worldObj.getBlockId(xCoord, yCoord+1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord), 3);
				TileEntity passenger = worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);

				if(passenger instanceof IMovableTile){
					((IMovableTile) passenger).prepareToMove();
					worldObj.removeBlockTileEntity(xCoord, yCoord+1, zCoord);
					passenger.xCoord=targetX;
					passenger.zCoord=targetZ;
					worldObj.setBlockTileEntity(targetX, yCoord+1, targetZ, passenger);
					passenger.validate();
					((IMovableTile) passenger).doneMoving();
				}


			}


		}
	}

}
