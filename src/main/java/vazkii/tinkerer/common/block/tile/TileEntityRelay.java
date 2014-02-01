package vazkii.tinkerer.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityRelay extends TileEntity {
	public TileEntityRelay(World world) {
	}


	public void verifyPartner(){
		TileEntity te = worldObj.getBlockTileEntity(partnerX, yCoord, partnerZ);
		if(!(hasPartner && te instanceof TileEntityRelay && ((TileEntityRelay) te).partnerX == this.xCoord && ((TileEntityRelay) te).partnerZ == this.zCoord)){
			hasPartner=false;
		}
	}

	public boolean hasPartner;

	public int partnerX;
	public int partnerZ;

	@Override
	public void updateEntity() {

		verifyPartner();

		if(worldObj.getTotalWorldTime()%100==0){
			checkForPartner();
		}
		int i=xCoord;
		if(hasPartner && worldObj.getTotalWorldTime()%200==0){
			do{

				int j=zCoord;
				do{
					if(worldObj.getBlockTileEntity(i, yCoord, j) instanceof TileEntityMobilizer){
						TileEntityMobilizer te = (TileEntityMobilizer) worldObj.getBlockTileEntity(i, yCoord, j);
						te.verifyRelay();
						if(!te.linked){
							te.firstRelayX = xCoord;
							te.firstRelayZ = zCoord;

							te.secondRelayX = partnerX;
							te.secondRelayZ = partnerZ;

							te.linked = true;

							if(xCoord < partnerX){
								te.movementDirection = ForgeDirection.EAST;
							}else if(xCoord > partnerX){
								te.movementDirection = ForgeDirection.WEST;
							}else if(zCoord < partnerZ){
								te.movementDirection = ForgeDirection.SOUTH;
							}else if(zCoord > partnerZ){
								te.movementDirection = ForgeDirection.NORTH;
							}
						}
					}

					j+=Math.copySign(1,partnerZ-zCoord);
				}while (j < partnerZ);
				i+=Math.copySign(1, partnerX-xCoord);
			}while (i < partnerX);
		}
	}

	public void checkForPartner(){
		if(!hasPartner){
			for(int i=-10;i<10;i++){

				TileEntity te = worldObj.getBlockTileEntity(xCoord+i, yCoord, zCoord);

				if(te instanceof TileEntityRelay){
					((TileEntityRelay) te).partnerX=this.xCoord;
					((TileEntityRelay) te).partnerZ=this.zCoord;
					this.partnerX=te.xCoord;
					this.partnerZ=te.zCoord;

					this.hasPartner=true;
					((TileEntityRelay) te).hasPartner=true;

				}



				te = worldObj.getBlockTileEntity(xCoord+i, yCoord, zCoord);
				if(te instanceof TileEntityRelay){
					((TileEntityRelay) te).partnerX=this.xCoord;
					((TileEntityRelay) te).partnerZ=this.zCoord;
					this.partnerX=te.xCoord;
					this.partnerZ=te.zCoord;


					this.hasPartner=true;
					((TileEntityRelay) te).hasPartner=true;
				}
			}
		}
	}

}
