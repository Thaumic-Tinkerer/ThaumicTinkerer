package com.nekokittygames.thaumictinkerer.client.proxy;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.client.misc.Shaders;
import com.nekokittygames.thaumictinkerer.client.rendering.special.multi.NitorRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.*;
import com.nekokittygames.thaumictinkerer.common.commands.CommandThaumicTinkererClient;
import com.nekokittygames.thaumictinkerer.common.intl.MultiBlockPreviewRendering;
import com.nekokittygames.thaumictinkerer.common.proxy.GuiProxy;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.tileentity.*;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.common.blocks.misc.BlockNitor;

import static com.nekokittygames.thaumictinkerer.ThaumicTinkerer.instance;

public class ClientProxy implements ITTProxy {
    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFunnel.class,new TileEntityFunnelRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRepairer.class,new TileEntityRepairerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExample.class,new TileEntityExampleRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentPillar.class,new TileEntityEnchantmentPillarRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchanter.class,new TileEntityEnchanterRenderer());

        Shaders.initShaders();
        MultiBlockPreviewRendering.RegisterRenderer(BlockNitor.class,new NitorRenderer());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandThaumicTinkererClient());
    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.format(unlocalized, args);
    }
}
