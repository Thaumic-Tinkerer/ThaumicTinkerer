package thaumic.tinkerer.common.block.tile;

import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

/**
 * Created by pixlepix on 7/25/14.
 */
public class TileInfusedGrain extends TileEntity implements IAspectContainer {

    public Aspect aspect;
    public AspectList primalTendencies;

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