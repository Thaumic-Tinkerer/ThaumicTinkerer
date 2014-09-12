/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [4 Sep 2013, 16:33:54 (GMT)]
 */
package thaumic.tinkerer.client.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import thaumic.tinkerer.client.core.handler.ClientTickHandler;
import thaumic.tinkerer.client.core.handler.GemArmorKeyHandler;
import thaumic.tinkerer.client.core.handler.HUDHandler;
import thaumic.tinkerer.client.core.handler.LocalizationHandler;
import thaumic.tinkerer.client.core.handler.kami.KamiArmorClientHandler;
import thaumic.tinkerer.client.core.handler.kami.PlacementMirrorPredictionRenderer;
import thaumic.tinkerer.client.core.handler.kami.SoulHeartClientHandler;
import thaumic.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import thaumic.tinkerer.client.core.helper.ClientHelper;
import thaumic.tinkerer.client.lib.LibRenderIDs;
import thaumic.tinkerer.client.render.block.RenderInfusedCrops;
import thaumic.tinkerer.client.render.block.RenderMagnet;
import thaumic.tinkerer.client.render.block.RenderRepairer;
import thaumic.tinkerer.client.render.block.kami.RenderWarpGate;
import thaumic.tinkerer.client.render.item.RenderGenericSeeds;
import thaumic.tinkerer.client.render.item.RenderMobDisplay;
import thaumic.tinkerer.client.render.item.kami.RenderPlacementMirror;
import thaumic.tinkerer.client.render.tile.*;
import thaumic.tinkerer.client.render.tile.kami.RenderTileWarpGate;
import thaumic.tinkerer.common.ThaumicTinkerer;
import thaumic.tinkerer.common.block.tile.*;
import thaumic.tinkerer.common.block.tile.kami.TileWarpGate;
import thaumic.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import thaumic.tinkerer.common.core.handler.ConfigHandler;
import thaumic.tinkerer.common.core.proxy.TTCommonProxy;
import thaumic.tinkerer.common.item.ItemInfusedSeeds;
import thaumic.tinkerer.common.item.ItemMobDisplay;
import thaumic.tinkerer.common.item.kami.ItemPlacementMirror;
import thaumic.tinkerer.common.item.kami.foci.ItemFocusShadowbeam;

public class TTClientProxy extends TTCommonProxy {

    public static EnumRarity kamiRarity;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        //Temporarly disabled for 1.7
        //MinecraftForge.EVENT_BUS.register(new FumeTool());
        if (ConfigHandler.enableKami)
            //kamiRarity = EnumHelperClient.addRarity("KAMI", 0x6, "Kami");
            kamiRarity = EnumHelperClient.addEnum(new Class[][]{{EnumRarity.class, EnumChatFormatting.class, String.class}}, EnumRarity.class, "KAMI", EnumChatFormatting.LIGHT_PURPLE, "Kami");
    }

    public static EntityPlayer getPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        LocalizationHandler.loadLocalizations();
        MinecraftForge.EVENT_BUS.register(new HUDHandler());
        ClientTickHandler cthandler = new ClientTickHandler();
        FMLCommonHandler.instance().bus().register(cthandler);
        MinecraftForge.EVENT_BUS.register(cthandler);
        MinecraftForge.EVENT_BUS.register(new GemArmorKeyHandler());
        registerTiles();
        registerRenderIDs();

        if (ConfigHandler.enableKami) {
            MinecraftForge.EVENT_BUS.register(new SoulHeartClientHandler());
            MinecraftForge.EVENT_BUS.register(new ToolModeHUDHandler());
            if (ConfigHandler.showPlacementMirrorBlocks)
                MinecraftForge.EVENT_BUS.register(new PlacementMirrorPredictionRenderer());
        }
    }



    private void registerTiles() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAnimationTablet.class, new RenderTileAnimationTablet());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMagnet.class, new RenderTileMagnet());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEnchanter.class, new RenderTileEnchanter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFunnel.class, new RenderTileFunnel());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRepairer.class, new RenderTileRepairer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSynth.class, new RenderTileSynth());

        LibRenderIDs.idGrain = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(LibRenderIDs.idGrain, new RenderInfusedCrops());

        if (ConfigHandler.enableKami) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileWarpGate.class, new RenderTileWarpGate());
        }
    }

    private void registerRenderIDs() {
        LibRenderIDs.idMagnet = RenderingRegistry.getNextAvailableRenderId();
        LibRenderIDs.idRepairer = RenderingRegistry.getNextAvailableRenderId();
        LibRenderIDs.idFire = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderMagnet());
        RenderingRegistry.registerBlockHandler(new RenderRepairer());

        MinecraftForgeClient.registerItemRenderer(ThaumicTinkerer.registry.getFirstItemFromClass(ItemMobDisplay.class), new RenderMobDisplay());
        MinecraftForgeClient.registerItemRenderer(ThaumicTinkerer.registry.getFirstItemFromClass(ItemInfusedSeeds.class), new RenderGenericSeeds());

        if (ConfigHandler.enableKami) {
            MinecraftForgeClient.registerItemRenderer(ThaumicTinkerer.registry.getFirstItemFromClass(ItemPlacementMirror.class), new RenderPlacementMirror());

            LibRenderIDs.idWarpGate = RenderingRegistry.getNextAvailableRenderId();

            RenderingRegistry.registerBlockHandler(new RenderWarpGate());
            //KeyBindingRegistry.registerKeyBinding(new GemArmorKeyHandler());
        }
    }

    @Override
    public void shadowSparkle(World world, float x, float y, float z, int size) {
        ItemFocusShadowbeam.Particle particle = new ItemFocusShadowbeam.Particle(world, x, y, z, 1.5F, 0, size);
        ClientHelper.minecraft().effectRenderer.addEffect(particle);
    }

    @Override
    protected void initCCPeripherals() {
        try {
            super.initCCPeripherals();
        } catch (Throwable e) {
            ThaumicTinkerer.log.info("Thaumic Tinkerer: ComputerCraft not found.");
        }
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public boolean armorStatus(EntityPlayer player) {
        return KamiArmorClientHandler.ArmorEnabled;

    }

    @Override
    public void setArmor(EntityPlayer player, boolean status) {
        super.setArmor(player, status);
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            KamiArmorClientHandler.ArmorEnabled = status;
        }
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return ClientHelper.clientPlayer();
    }

}
