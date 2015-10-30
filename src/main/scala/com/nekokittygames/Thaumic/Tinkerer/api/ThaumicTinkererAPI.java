package com.nekokittygames.Thaumic.Tinkerer.api;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Created by katsw on 24/05/2015.
 */
public class ThaumicTinkererAPI {


    public final static String FOOD_TALISMAN_IMC="foodTalismanBlacklist";
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



}
