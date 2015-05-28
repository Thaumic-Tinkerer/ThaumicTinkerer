package com.nekokittygames.Thaumic.api;

import thaumcraft.api.aspects.AspectList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * API for the Bound Essentia jar system
 * Created by Katrina on 24/05/2015.
 */
public class BoundJarAPI {





    private static Boolean ttSearched=false;


    /**
     * Returns an aspect list containing the aspect, or lack thereof the network contains
     * @param uuid id of the network to check
     * @return aspect list, or blank if a new network
     */
    public static AspectList getAspects(String uuid)
    {
        findTT();
        if(BoundJarGetAspect!=null)
        {
            try
            {
                return (AspectList)BoundJarGetAspect.invoke(BoundClassInstance.get(null),uuid);
            }
            catch(Exception e)
            {
             return null;
            }
        }
        else
            return null;
    }


    /**
     * Marks a network dirty so it will be saved
     * @param uuid network ID
     */
    public static void markDirty(String uuid)
    {
        findTT();
        if(BoundJarMarkDirty!=null)
        {
            try
            {
                BoundJarMarkDirty.invoke(BoundClassInstance.get(null),uuid);
            }
            catch(Exception e)
            {

            }
        }
    }
    private static Class BoundJarManager;
    private static Field BoundClassInstance;
    public static Method BoundJarGetAspect;
    public static Method BoundJarMarkDirty;
    private static void findTT()
    {
        if(!ttSearched)
        {
            try
            {
                BoundJarManager=Class.forName("com.nekokittygames.Thaumic.Tinkerer.common.data.BoundJarNetworkManager$");
                BoundClassInstance=findTTField("MODULE$");
                BoundJarGetAspect=findTTMethod("getAspect",new Class[] {String.class});
                BoundJarMarkDirty=findTTMethod("markDirty",new Class[] {String.class});

            } catch( Exception e ) {
                System.out.println( "Thaumic Tinkerer API: Thaumic Tinkerer not found." );
            } finally {
                ttSearched = true;
            }
        }
    }

    private static Field findTTField( String name)
    {
        try {
            if( BoundJarManager != null )
            {
                return BoundJarManager.getField(name);
            }
            return null;
        } catch( NoSuchFieldException e ) {
            System.out.println( "Thaumic Tinkerer API: Thaumic Tinkerer method " + name + " not found." );
            return null;
        }
    }

    private static Method findTTMethod( String name, Class[] args )
    {
        try {
            if( BoundJarManager != null )
            {
                return BoundJarManager.getMethod( name, args );
            }
            return null;
        } catch( NoSuchMethodException e ) {
            System.out.println( "Thaumic Tinkerer API: Thaumic Tinkerer method " + name + " not found." );
            return null;
        }
    }
}
