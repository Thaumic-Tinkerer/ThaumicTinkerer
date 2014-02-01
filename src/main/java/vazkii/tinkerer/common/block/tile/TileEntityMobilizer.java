package vazkii.tinkerer.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityMobilizer extends TileEntity {

	public TileEntityMobilizer(World world) {

	}

	public boolean linked;

	public int firstRelayX;
	public int secondRelayX;

	public int firstRelayZ;
	public int secondRelayZ;

	public ForgeDirection movementDirection;

	public void verifyRelay(){
		TileEntity te = worldObj.getBlockTileEntity(firstRelayX, yCoord, firstRelayZ);
		if(te instanceof TileEntityRelay){
			((TileEntityRelay) te).verifyPartner();
		}
		if(!(te instanceof TileEntityRelay && ((TileEntityRelay) te).partnerX == this.secondRelayX && ((TileEntityRelay) te).partnerZ == this.secondRelayZ)){
			linked=false;
		}
	}

	public void updateEntity(){
		verifyRelay();

		if(worldObj.getTotalWorldTime()%100==0){

			//Switch direction if at end of track
			if((xCoord+movementDirection.offsetZ==firstRelayX && zCoord + movementDirection.offsetZ == firstRelayZ) || (xCoord+movementDirection.offsetZ==secondRelayX && zCoord + movementDirection.offsetZ == secondRelayZ)){
				movementDirection = movementDirection.getOpposite();
			}

			int targetX = xCoord+movementDirection.offsetX;
			int targetZ = zCoord+movementDirection.offsetZ;

			if(worldObj.getBlockId(targetX, yCoord, targetZ) == 0){
				worldObj.setBlock(targetX, yCoord, targetZ, this.blockType.blockID);
				worldObj.setBlock(xCoord, yCoord, zCoord, 0);
			}


		}
	}

}
