package com.nekokittygames.thaumictinkerer;

import com.nekokittygames.thaumictinkerer.common.commands.CommandDumpEnchants;
import com.nekokittygames.thaumictinkerer.common.commands.CommandRefreshMultiblocks;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectDislocate;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectTelekenesis;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.misc.ThaumicTInkererCreativeTab;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.recipes.ModRecipes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.casters.FocusEngine;
import thaumcraft.api.research.ResearchCategories;

import java.io.IOException;
import java.net.URISyntaxException;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.MOD_VERSION, dependencies = LibMisc.MOD_DEPENDENCIES)
public class ThaumicTinkerer {
    public static Logger logger;

    private static CreativeTabs tab;

    @SidedProxy(serverSide = "com.nekokittygames.thaumictinkerer.common.proxy.CommonProxy", clientSide = "com.nekokittygames.thaumictinkerer.client.proxy.ClientProxy")
    public static ITTProxy proxy;


    @Mod.Instance(LibMisc.MOD_ID)
    public static ThaumicTinkerer instance;

    public static CreativeTabs getTab() {
        return tab;
    }

    public static void setTab(CreativeTabs tab) {
        ThaumicTinkerer.tab = tab;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        tab = new ThaumicTInkererCreativeTab();
        logger = event.getModLog();

        proxy.preInit(event);
        PacketHandler.registerMessages(LibMisc.MOD_ID);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandRefreshMultiblocks());
        event.registerServerCommand(new CommandDumpEnchants());

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            MultiblockManager.initMultiblocks();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        proxy.init(event);
        ResearchCategories.registerCategory("THAUMIC_TINKERER", null, new AspectList(), new ResourceLocation("thaumictinkerer", "textures/items/share_book.png"), new ResourceLocation("thaumictinkerer", "textures/misc/sky1.png"), new ResourceLocation("thaumictinkerer", "textures/misc/sky1.png"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/misc"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/baubles"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/machines"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/foci"));
        proxy.registerRenderers();
        FocusEngine.registerElement(FocusEffectDislocate.class, new ResourceLocation("thaumictinkerer", "textures/foci_icons/dislocation.png"), 8760709);
        logger.info("Initializing Telekenetic powers");
        FocusEngine.registerElement(FocusEffectTelekenesis.class, new ResourceLocation("thaumictinkerer", "textures/foci_icons/telekenesis.png"), 8760708);

    }
}

