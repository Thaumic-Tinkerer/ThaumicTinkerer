package com.nekokittygames.thaumictinkerer.client.proxy;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.client.misc.Shaders;
import com.nekokittygames.thaumictinkerer.client.rendering.special.multi.NitorRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.TileEntityEnchantmentPillarRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.TileEntityExampleRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.TileEntityFunnelRenderer;
import com.nekokittygames.thaumictinkerer.client.rendering.tileentities.TileEntityRepairerRenderer;
import com.nekokittygames.thaumictinkerer.common.intl.MultiBlockPreviewRendering;
import com.nekokittygames.thaumictinkerer.common.proxy.GuiProxy;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchantmentPillar;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityExample;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityFunnel;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityRepairer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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

        Shaders.initShaders();
        MultiBlockPreviewRendering.RegisterRenderer(BlockNitor.class,new NitorRenderer());
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
