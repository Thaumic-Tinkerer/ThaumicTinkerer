package com.nekokittygames.thaumictinkerer.client.proxy;

import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.TileEntityFunnelRenderer;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityFunnel;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements ITTProxy {
    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFunnel.class,new TileEntityFunnelRenderer());
    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.format(unlocalized, args);
    }
}
