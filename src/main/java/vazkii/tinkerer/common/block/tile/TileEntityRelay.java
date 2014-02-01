package vazkii.tinkerer.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityRelay extends TileEntity {
	public TileEntityRelay(World world) {
	}

	public void verifyPartner(){
		TileEntity te = worldObj.getBlockTileEntity(partnerX, yCoord, partnerZ);
		if(!(te instanceof TileEntityRelay && ((TileEntityRelay) te).partnerX == this.xCoord && ((TileEntityRelay) te).partnerZ == this.zCoord)){
			hasPartner=false;
		}
	}

	public boolean hasPartner;

	public int partnerX;
	public int partnerZ;

	public void checkForPartner(){
		for(int i=-32;i<32;i++){
			TileEntity te = worldObj.getBlockTileEntity(xCoord+i, yCoord, partnerZ);
			if(te instanceof TileEntityRelay){
				((TileEntityRelay) te).partnerX=this.xCoord;
				((TileEntityRelay) te).partnerZ=this.zCoord;
				this.partnerX=te.xCoord;
				this.partnerZ=te.zCoord;
			}

			te = worldObj.getBlockTileEntity(xCoord+i, yCoord, partnerZ);
			if(te instanceof TileEntityRelay){
				((TileEntityRelay) te).partnerX=this.xCoord;
				((TileEntityRelay) te).partnerZ=this.zCoord;
				this.partnerX=te.xCoord;
				this.partnerZ=te.zCoord;
			}
		}
	}

}
