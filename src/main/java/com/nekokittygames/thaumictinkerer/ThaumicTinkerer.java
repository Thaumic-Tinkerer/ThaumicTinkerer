package com.nekokittygames.thaumictinkerer;

import com.nekokittygames.thaumictinkerer.api.ThaumicTinkererAPI;
import com.nekokittygames.thaumictinkerer.common.blocks.ModBlocks;
import com.nekokittygames.thaumictinkerer.common.commands.CommandDumpEnchants;
import com.nekokittygames.thaumictinkerer.common.commands.CommandRefreshMultiblocks;
import com.nekokittygames.thaumictinkerer.common.compat.botania.BotaniaCompat;
import com.nekokittygames.thaumictinkerer.common.config.TTConfig;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectDislocate;
import com.nekokittygames.thaumictinkerer.common.foci.FocusEffectTelekenesis;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.misc.ThaumicTinkererAspectCreativeTab;
import com.nekokittygames.thaumictinkerer.common.misc.ThaumicTinkererMainCreativeTab;
import com.nekokittygames.thaumictinkerer.common.multiblocks.MultiblockManager;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.proxy.ITTProxy;
import com.nekokittygames.thaumictinkerer.common.research.theorycraft.AidBlackQuartz;
import com.nekokittygames.thaumictinkerer.common.research.theorycraft.CardExperience;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.casters.FocusEngine;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.theorycraft.TheorycraftManager;

import java.io.IOException;
import java.net.URISyntaxException;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.MOD_VERSION, dependencies = LibMisc.MOD_DEPENDENCIES)
public class ThaumicTinkerer {
    public static Logger logger;

    private static CreativeTabs tabMain;

    private static CreativeTabs tabAspects;


    @SidedProxy(serverSide = "com.nekokittygames.thaumictinkerer.common.proxy.CommonProxy", clientSide = "com.nekokittygames.thaumictinkerer.client.proxy.ClientProxy")
    public static ITTProxy proxy;


    @Mod.Instance(LibMisc.MOD_ID)
    public static ThaumicTinkerer instance;

    public static CreativeTabs getTabMain() {
        return tabMain;
    }

    public static void setTabMain(CreativeTabs tabMain) {
        ThaumicTinkerer.tabMain = tabMain;
    }

    public static CreativeTabs getTabAspects() {
        return tabAspects;
    }

    public static void setTabAspects(CreativeTabs tabAspects) {
        ThaumicTinkerer.tabAspects = tabAspects;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        tabMain = new ThaumicTinkererMainCreativeTab();
        tabAspects = new ThaumicTinkererAspectCreativeTab();
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
    public void processIMC(FMLInterModComms.IMCEvent event) {
        for(FMLInterModComms.IMCMessage message:event.getMessages()) {
            if(message.key.equalsIgnoreCase("addDislocateBlacklist") && message.isStringMessage())
            {
                ThaumicTinkererAPI.getDislocationBlacklist().add(message.getStringValue());
            }
            if(message.key.equalsIgnoreCase("addTabletBlacklist") && message.isStringMessage())
            {
                ThaumicTinkererAPI.getAnimationTabletBlacklist().add(message.getStringValue());
            }
        }
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
        TheorycraftManager.registerCard(CardExperience.class);
        TheorycraftManager.registerAid(new AidBlackQuartz(ModBlocks.black_quartz_block));
        BotaniaCompat.addTheorycraft();
        proxy.registerRenderers();
        initFoci();
        //IDustTrigger.registerDustTrigger(ModBlocks.osmotic_enchanter);

    }

    private void initFoci() {
        if(TTConfig.DislocationFocusEnabled)
            FocusEngine.registerElement(FocusEffectDislocate.class, new ResourceLocation("thaumictinkerer", "textures/foci_icons/dislocation.png"), 15121988);
        if(TTConfig.TelekenesisFocusEnabled) {
            logger.info("Initializing Telekenetic powers");
            FocusEngine.registerElement(FocusEffectTelekenesis.class, new ResourceLocation("thaumictinkerer", "textures/foci_icons/telekenesis.png"), 13566207);
        }
    }
}

