package vazkii.tinkerer.common.block.tile;

import appeng.api.movable.IMovableTile;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import vazkii.tinkerer.common.ThaumicTinkerer;

public class TileEntityRelay extends TileEntity implements IMovableTile {
	public TileEntityRelay(World world) {
	}


	public void verifyPartner(){
		TileEntity te = worldObj.getBlockTileEntity(partnerX, yCoord, partnerZ);
		if(!(hasPartner && te instanceof TileEntityRelay && ((TileEntityRelay) te).partnerX == this.xCoord && ((TileEntityRelay) te).partnerZ == this.zCoord)){
			hasPartner=false;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setBoolean("HasPartner", hasPartner);

		nbt.setInteger("PartnerX", partnerX);
		nbt.setInteger("PartnerZ", partnerZ);

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		hasPartner=nbt.getBoolean("HasPartner");

		partnerX=nbt.getInteger("PartnerX");
		partnerZ=nbt.getInteger("PartnerZ");
	}

	public boolean hasPartner;

	public int partnerX;
	public int partnerZ;

	@Override
	public void updateEntity() {

		verifyPartner();
		if(hasPartner){
			float i=0;
			float j=0;
			do{

				j=zCoord;
				do{

					ThaumicTinkerer.tcProxy.sparkle(i, (float) yCoord, i, 1);
					j+=Math.copySign(.1,partnerZ-zCoord);
				}while (j < partnerZ);
				i+=Math.copySign(.1, partnerX-xCoord);
			}while (i < partnerX);
		}
		if(worldObj.getTotalWorldTime()%200==0){
			checkForPartner();
		}
		int i=xCoord;
		if(hasPartner && worldObj.getTotalWorldTime()%200==1){
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

	@Override
	public boolean prepareToMove() {
		return true;
	}

	@Override
	public void doneMoving() {

	}

}
