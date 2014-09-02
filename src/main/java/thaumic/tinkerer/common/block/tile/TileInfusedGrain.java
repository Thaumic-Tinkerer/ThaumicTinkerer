package thaumic.tinkerer.common.block.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.BlockInfusedGrain;

import java.util.Random;

/**
 * Created by pixlepix on 7/25/14.
 */
public class TileInfusedGrain extends TileEntity implements IAspectContainer {

    public Aspect aspect;
    public AspectList primalTendencies = new AspectList();

    private final String NBT_MAIN_ASPECT = "mainAspect";
    private final String NBT_ASPEPCT_TENDENCIES = "aspectTendencies";

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }

    @Override
    public void updateEntity() {

        //Growth
        if (!worldObj.isRemote && worldObj.getBlockLightValue(xCoord, yCoord + 1, zCoord) >= 9) {
            int l = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            if (l < 7) {
                if (worldObj.rand.nextInt((((2510 - (int) Math.pow(((TileInfusedGrain) (worldObj.getTileEntity(xCoord, yCoord, zCoord))).primalTendencies.getAmount(Aspect.WATER), 2))) * 6)) == 0) {
                    ++l;
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, l, 3);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                }
            }
        }

        //Aspect Exchange
        if (worldObj.rand.nextInt((2550 - ((int) Math.pow(primalTendencies.getAmount(Aspect.AIR), 2))) * 10) == 0 && !aspect.isPrimal()) {

            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity entity = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                if (entity instanceof TileInfusedGrain) {
                    //Exchange aspects
                    TileInfusedGrain tileInfusedGrain = (TileInfusedGrain) entity;
                    Aspect aspect = tileInfusedGrain.aspect;
                    if (aspect.isPrimal()) {
                        if (primalTendencies.getAmount(aspect) < 5) {
                            primalTendencies.add(aspect, 1);
                            reduceSaturatedAspects();

                            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                            if (worldObj.isRemote) {
                                for (int i = 0; i < 50; i++) {
                                    ThaumicTinkerer.tcProxy.essentiaTrailFx(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, xCoord, yCoord, zCoord, 50, aspect.getColor(), 1F);
                                }
                            }
                            return;
                        }
                    } else {
                        AspectList targetList = tileInfusedGrain.primalTendencies;
                        if (targetList.getAspects().length == 0 || targetList.getAspects()[0] == null) {
                            return;
                        }

                        aspect = targetList.getAspects()[worldObj.rand.nextInt(targetList.getAspects().length)];
                        if (targetList.getAmount(aspect) >= primalTendencies.getAmount(aspect)) {
                            primalTendencies.add(aspect, 1);
                            targetList.reduce(aspect, 1);
                            reduceSaturatedAspects();
                            if (worldObj.isRemote) {
                                for (int i = 0; i < 50; i++) {
                                    ThaumicTinkerer.tcProxy.essentiaTrailFx(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, xCoord, yCoord, zCoord, 50, aspect.getColor(), 1F);
                                }
                            }

                            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
                        }
                        return;
                    }


                }
            }
        }
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return !(oldBlock == newBlock);
    }

    public void reduceSaturatedAspects() {
        int sum = 0;
        for (Integer i : primalTendencies.aspects.values()) {
            sum += i;
        }
        if (sum > 50) {
            int toRemove = sum - 50;
            while (toRemove > 0) {
                Random rand = new Random();
                Aspect target = primalTendencies.getAspects()[rand.nextInt(primalTendencies.getAspects().length)];
                primalTendencies.remove(target, 1);
                toRemove--;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }

    public void writeCustomNBT(NBTTagCompound nbt) {
        NBTTagCompound aspectCompound = new NBTTagCompound();
        new AspectList().add(aspect, 1).writeToNBT(aspectCompound);
        nbt.setTag(NBT_MAIN_ASPECT, aspectCompound);

        NBTTagCompound tendencyCompound = new NBTTagCompound();
        primalTendencies.writeToNBT(tendencyCompound);
        nbt.setTag(NBT_ASPEPCT_TENDENCIES, tendencyCompound);
    }

    public void readCustomNBT(NBTTagCompound nbt) {

        AspectList aspectList = new AspectList();
        aspectList.readFromNBT(nbt.getCompoundTag(NBT_MAIN_ASPECT));
        aspect = aspectList.getAspects()[0];
        aspectList.readFromNBT(nbt.getCompoundTag(NBT_ASPEPCT_TENDENCIES));
        primalTendencies = aspectList;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readCustomNBT(pkt.func_148857_g());
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        writeCustomNBT(compound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, compound);
    }

    @Override
    public AspectList getAspects() {
        return aspect != null ? new AspectList().add(aspect, 1) : null;
    }


    @Override
    public void setAspects(AspectList paramAspectList) {
    }

    @Override
    public boolean doesContainerAccept(Aspect paramAspect) {

        return false;
    }

    @Override
    public int addToContainer(Aspect paramAspect, int paramInt) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect paramAspect, int paramInt) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList paramAspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect paramAspect, int paramInt) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList paramAspectList) {
        return false;
    }

    @Override
    public int containerContains(Aspect paramAspect) {
        return 0;
    }
}
