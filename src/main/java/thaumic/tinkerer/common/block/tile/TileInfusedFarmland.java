package thaumic.tinkerer.common.block.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

import java.util.Random;

/**
 * Created by pixlepix on 8/4/14.
 */
public class TileInfusedFarmland extends TileEntity implements IAspectContainer {
    public AspectList aspectList = new AspectList();

    public static final int MAX_ASPECTS = 20;

    public static final String NBT_ASPECT_LIST = "aspectList";

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        readCustomNBT(nbt);
    }


    public void readCustomNBT(NBTTagCompound nbt) {

        aspectList.readFromNBT(nbt.getCompoundTag(NBT_ASPECT_LIST));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeCustomNBT(nbt);
    }

    public void writeCustomNBT(NBTTagCompound nbt) {

        NBTTagCompound compound = new NBTTagCompound();

        aspectList.writeToNBT(compound);
        nbt.setTag(NBT_ASPECT_LIST, compound);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeCustomNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readCustomNBT(pkt.func_148857_g());
    }

    //Ensures that the farmland only holds a maximum of 20 aspects
    public void reduceSaturatedAspects() {
        int sum = 0;
        for (Integer i : aspectList.aspects.values()) {
            sum += i;
        }
        if (sum > MAX_ASPECTS) {
            int toRemove = sum - 20;
            while (toRemove > 0) {
                Random rand = new Random();
                Aspect target = aspectList.getAspects()[rand.nextInt(aspectList.getAspects().length)];
                aspectList.remove(target, 1);
                toRemove--;
            }
        }
    }

    @Override
    public AspectList getAspects() {
        return aspectList;
    }

    @Override
    public void setAspects(AspectList aspectList) {
        this.aspectList = aspectList;
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return false;
    }

    @Override
    public int addToContainer(Aspect aspect, int i) {
        return 0;
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int i) {
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        return false;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return 0;
    }
}
