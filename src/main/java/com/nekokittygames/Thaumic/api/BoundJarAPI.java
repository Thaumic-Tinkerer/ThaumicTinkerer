package com.nekokittygames.Thaumic.api;

import thaumcraft.api.aspects.AspectList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by katsw on 24/05/2015.
 */
public class BoundJarAPI {





    private static Boolean ttSearched=false;


    public static String getAPIVersion()
    {
        return "someVersionhere"; //TODO: make this keep up
    }


    public static AspectList getAspects(UUID uuid)
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



    public static void markDirty(UUID uuid)
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
                BoundJarGetAspect=findTTMethod("getAspect",new Class[] {UUID.class});
                BoundJarMarkDirty=findTTMethod("markDirty",new Class[] {UUID.class});

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
