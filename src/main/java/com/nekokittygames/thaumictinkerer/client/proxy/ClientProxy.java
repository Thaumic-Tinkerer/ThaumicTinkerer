package com.nekokittygames.thaumictinkerer.client.proxy;

import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.TileEntityExampleRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.TileEntityFunnelRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.TileEntityRepairerRenderer;
import com.nekokittygames.thaumictinkerer.common.proxy.GuiProxy;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityExample;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityFunnel;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityRepairer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static com.nekokittygames.thaumictinkerer.ThaumicTinkerer.instance;

public class ClientProxy implements ITTProxy {
    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFunnel.class,new TileEntityFunnelRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRepairer.class,new TileEntityRepairerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExample.class,new TileEntityExampleRenderer());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());
    }
    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.format(unlocalized, args);
    }
}
