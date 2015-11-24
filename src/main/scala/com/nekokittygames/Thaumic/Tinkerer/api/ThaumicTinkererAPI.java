package com.nekokittygames.Thaumic.Tinkerer.api;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import thaumcraft.api.aspects.Aspect;

/**
 * Created by katsw on 24/05/2015.
 */
public class ThaumicTinkererAPI {


    public final static String FOOD_TALISMAN_IMC="foodTalismanBlacklist";
    public final static String MOB_ASPECT_IMC="mobAspect";
    public final static String ENTITY_CLASS_NAME="entityClass";
    public final static String ASPECT_NAME="aspect";
    public final static String MOD_ID="thaumictinkerer";


    /**
     * Adds an item to the blacklist of the food talisman, so it will not even test it for being edible
     * @param item Item to add to the list
     */
    public static void AddFoodToTalismanBlacklist(Item item)
    {
        if(Loader.isModLoaded(MOD_ID))
        {
            FMLInterModComms.sendMessage(MOD_ID,FOOD_TALISMAN_IMC,item.getUnlocalizedName());
        }
    }

    public static void AddAspectToSpawnList(Class entity, Aspect[] aspects)
    {
        NBTTagCompound cmp=new NBTTagCompound();
        cmp.setString(ENTITY_CLASS_NAME,entity.getName());
        NBTTagList list=new NBTTagList();
        for(Aspect aspect:aspects)
        {
            NBTTagCompound asp=new NBTTagCompound();
            asp.setString(ASPECT_NAME,aspect.getTag());
            list.appendTag(asp);
        }
        cmp.setTag(ASPECT_NAME,list);
        FMLInterModComms.sendMessage(MOD_ID,MOB_ASPECT_IMC,cmp);
    }



}
