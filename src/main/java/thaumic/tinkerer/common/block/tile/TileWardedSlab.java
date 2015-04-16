package thaumic.tinkerer.common.block.tile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileWardingStone;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Katrina on 14/04/2015.
 */
public class TileWardedSlab extends TileEntity {
    int count = 0;

    public TileWardedSlab() {
    }

    public boolean gettingPower() {
        return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
    }

    public boolean canUpdate() {
        return true;
    }

    public void updateEntity() {
        if(!this.worldObj.isRemote) {
            if(this.count == 0) {
                this.count = this.worldObj.rand.nextInt(100);
            }

            if(this.count % 5 == 0 && !this.gettingPower()) {
                List targets = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)this.yCoord-1, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 3), (double)(this.zCoord + 1)).expand(0.1D, 0.1D, 0.1D));
                if(targets.size() > 0) {
                    Iterator i$ = targets.iterator();

                    while(i$.hasNext()) {
                        EntityLivingBase e = (EntityLivingBase)i$.next();
                        if(!e.onGround && !(e instanceof EntityPlayer)) {
                            e.addVelocity((double)(-MathHelper.sin((e.rotationYaw + 180.0F) * 3.1415927F / 180.0F) * 0.2F), -0.1D, (double)(MathHelper.cos((e.rotationYaw + 180.0F) * 3.1415927F / 180.0F) * 0.2F));
                        }
                    }
                }
            }

            if(++this.count % 100 == 0) {
                if((this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) != ConfigBlocks.blockAiry || this.worldObj.getBlockMetadata(this.xCoord, this.yCoord + 1, this.zCoord) != 3) && this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord)) {
                    this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, ConfigBlocks.blockAiry, 4, 3);
                }

                if((this.worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord) != ConfigBlocks.blockAiry || this.worldObj.getBlockMetadata(this.xCoord, this.yCoord + 2, this.zCoord) != 3) && this.worldObj.getBlock(this.xCoord, this.yCoord + 2, this.zCoord).isReplaceable(this.worldObj, this.xCoord, this.yCoord + 2, this.zCoord)) {
                    this.worldObj.setBlock(this.xCoord, this.yCoord + 2, this.zCoord, ConfigBlocks.blockAiry, 4, 3);
                }
            }
        }

    }

}
