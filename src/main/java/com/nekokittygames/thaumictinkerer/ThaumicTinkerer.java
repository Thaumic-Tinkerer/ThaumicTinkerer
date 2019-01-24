package com.nekokittygames.thaumictinkerer;

import com.nekokittygames.thaumictinkerer.common.commands.CommandDumpEnchants;
import com.nekokittygames.thaumictinkerer.common.commands.CommandRefreshMultiblocks;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectDislocate;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.recipes.ModRecipes;
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

    @SidedProxy(serverSide = "com.nekokittygames.thaumictinkerer.common.proxy.CommonProxy", clientSide = "com.nekokittygames.thaumictinkerer.client.proxy.ClientProxy")
    public static ITTProxy proxy;


    @Mod.Instance(LibMisc.MOD_ID)
    public static ThaumicTinkerer instance;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        proxy.init(event);
        ModRecipes.InitializeRecipes();
        ResearchCategories.registerCategory("THAUMIC_TINKERER", (String) "FIRSTSTEPS", new AspectList(), new ResourceLocation("thaumictinkerer", "textures/items/share_book.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_1.jpg"), new ResourceLocation("thaumcraft", "textures/gui/gui_research_back_over.png"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/misc.json"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/baubles"));
        ThaumcraftApi.registerResearchLocation(new ResourceLocation("thaumictinkerer", "research/machines"));
        proxy.registerRenderers();
        FocusEngine.registerElement(FocusEffectDislocate.class, new ResourceLocation("thaumictinkerer", "blocks/dark_quartz_block"), 8760709);

    }
}

