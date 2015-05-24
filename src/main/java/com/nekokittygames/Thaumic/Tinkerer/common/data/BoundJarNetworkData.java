package com.nekokittygames.Thaumic.Tinkerer.common.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import thaumcraft.api.aspects.AspectList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Katrina on 24/05/2015.
 */
public class BoundJarNetworkData extends WorldSavedData {

    public Map<UUID,AspectList> networks=new HashMap<UUID, AspectList>();
    public static String IDENTIFIER="boundJar";
    public BoundJarNetworkData() {
        super(BoundJarNetworkData.IDENTIFIER);
    }
    public BoundJarNetworkData(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList list=nbt.getTagList("networks", Constants.NBT.TAG_COMPOUND);

        for(int i=0;i<list.tagCount();i++)
        {
            NBTTagCompound cmp=list.getCompoundTagAt(i);
            UUID uuid=new UUID(cmp.getLong("high"),cmp.getLong("low"));
            AspectList aspectList=new AspectList();
            aspectList.readFromNBT(cmp);
            networks.put(uuid,aspectList);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {

        NBTTagList list=new NBTTagList();
        for(Map.Entry<UUID,AspectList> entry : networks.entrySet())
        {
            NBTTagCompound cmp=new NBTTagCompound();
            cmp.setLong("high",entry.getKey().getMostSignificantBits());
            cmp.setLong("low", entry.getKey().getLeastSignificantBits());
            entry.getValue().writeToNBT(cmp);
            list.appendTag(cmp);
            }
        nbt.setTag("networks",list);
    }
}
