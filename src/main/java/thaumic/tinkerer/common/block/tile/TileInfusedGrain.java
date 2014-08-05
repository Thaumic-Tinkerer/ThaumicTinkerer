package thaumic.tinkerer.common.block.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumic.tinkerer.common.block.BlockInfusedGrain;

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
        new AspectList().add(aspect, 1).writeToNBT(nbt.getCompoundTag(NBT_MAIN_ASPECT));
        primalTendencies.writeToNBT(nbt.getCompoundTag(NBT_ASPEPCT_TENDENCIES));
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z) {
        return !(oldBlock instanceof BlockInfusedGrain && newBlock instanceof BlockInfusedGrain);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        AspectList aspectList = new AspectList();
        aspectList.readFromNBT(nbt.getCompoundTag(NBT_MAIN_ASPECT));
        aspect = aspectList.getAspects()[0];
        aspectList.readFromNBT(nbt.getCompoundTag(NBT_ASPEPCT_TENDENCIES));
        primalTendencies = aspectList;
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