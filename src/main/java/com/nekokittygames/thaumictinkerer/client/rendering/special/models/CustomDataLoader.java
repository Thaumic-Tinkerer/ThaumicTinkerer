package com.nekokittygames.thaumictinkerer.client.rendering.special.models;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class CustomDataLoader implements ICustomModelLoader {
    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {

    }

    @Override
    public boolean accepts(ResourceLocation resourceLocation) {
        return false;
    }

    @Override
    public IModel loadModel(ResourceLocation resourceLocation) throws Exception {
        return null;
    }
}
