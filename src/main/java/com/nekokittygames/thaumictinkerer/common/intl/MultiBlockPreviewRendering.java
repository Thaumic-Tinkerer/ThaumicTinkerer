package com.nekokittygames.thaumictinkerer.common.intl;

import com.nekokittygames.thaumictinkerer.api.rendering.IMultiBlockPreviewRenderer;

import java.util.HashMap;
import java.util.Map;

public class MultiBlockPreviewRendering {
    private static Map<Class, IMultiBlockPreviewRenderer> renderers=new HashMap<>();

    public static void RegisterRenderer(Class clazz,IMultiBlockPreviewRenderer renderer)
    {
        if(!renderers.containsKey(clazz))
            renderers.put(clazz,renderer);
    }

    public static IMultiBlockPreviewRenderer getRenderer(Class clazz)
    {
        if(renderers.containsKey(clazz))
            return renderers.get(clazz);
        return null;
    }
}
