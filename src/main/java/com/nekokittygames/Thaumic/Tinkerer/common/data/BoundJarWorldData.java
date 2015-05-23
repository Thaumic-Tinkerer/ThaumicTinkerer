package com.nekokittygames.Thaumic.Tinkerer.common.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import thaumcraft.api.aspects.AspectList;

import java.util.UUID;

/**
 * Created by katsw on 23/05/2015.
 */
public class BoundJarWorldData extends WorldSavedData {

    public final static String IDENTIFIER="teBoundJar";
    public final AspectList aspect=new AspectList();
    public UUID uuid=UUID.randomUUID();
    public BoundJarWorldData() {
        super(IDENTIFIER);
    }
    public BoundJarWorldData(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        aspect.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        aspect.writeToNBT(nbt);
    }



    public static BoundJarWorldData getJarData(World world,UUID guid)
    {
        BoundJarWorldData data=(BoundJarWorldData)world.getMapStorage().loadData(BoundJarWorldData.class,guid.toString());


        if(data==null)
        {
            data=new BoundJarWorldData(guid.toString());
            data.markDirty();
            world.getMapStorage().setData(guid.toString(),data);
        }
        data.uuid=guid;
        return data;
    }
}
