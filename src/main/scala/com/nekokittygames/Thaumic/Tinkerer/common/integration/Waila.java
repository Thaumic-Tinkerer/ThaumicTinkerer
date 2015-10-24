package com.nekokittygames.Thaumic.Tinkerer.common.integration;

import codechicken.lib.util.LangProxy;
import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockBoundJar;
import com.nekokittygames.Thaumic.Tinkerer.common.blocks.BlockBoundJar$;
import mcp.mobius.waila.api.IWailaRegistrar;

/**
 * Created by Katrina on 01/06/2015.
 */
public class Waila {

public static LangProxy lang=new LangProxy("ttwaila");

    public static void callbackRegister(IWailaRegistrar registrar)
    {
        registrar.registerBodyProvider(new WailaBoundJarProvider(), BlockBoundJar$.class);
    }
}
