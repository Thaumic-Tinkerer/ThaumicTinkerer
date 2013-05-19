/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [24 Apr 2013, 19:28:32 (GMT)]
 */
package vazkii.tinkerer;

import net.minecraftforge.common.MinecraftForge;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.CommonProxy;
import thaumcraft.common.Thaumcraft;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.core.proxy.TTCommonProxy;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibNetwork;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.PlayerTracker;
import vazkii.tinkerer.potion.ModPotions;
import vazkii.tinkerer.research.ModArcaneRecipes;
import vazkii.tinkerer.research.ModCraftingRecipes;
import vazkii.tinkerer.research.ModInfusionRecipes;
import vazkii.tinkerer.research.ModResearchItems;
import vazkii.tinkerer.util.handler.ConfigurationHandler;
import vazkii.tinkerer.util.handler.EntityInteractionHandler;
import vazkii.tinkerer.util.handler.GuiHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.MOD_VERSION, dependencies = LibMisc.DEPENDENCIES)
@NetworkMod(channels = { LibNetwork.PACKET_CHANNEL }, clientSideRequired = true, packetHandler = PacketManager.class)
public class ThaumicTinkerer {

	@Instance(LibMisc.MOD_ID)
	public static ThaumicTinkerer modInstance;

	@SidedProxy(clientSide = LibMisc.CLIENT_PROXY, serverSide = LibMisc.COMMON_PROXY)
	public static TTCommonProxy proxy;

	public static CommonProxy tcProxy;

	@PreInit
	public void onPreInit(FMLPreInitializationEvent event) {
		modInstance = this;
		tcProxy = Thaumcraft.proxy;

		ConfigurationHandler.loadConfig(event.getSuggestedConfigurationFile());

		proxy.initPackets();

		ThaumcraftApi.registerResearchXML(LibResources.RESEARCH_XML);
	}

	@Init
	public void onInit(FMLInitializationEvent event) {
		ModBlocks.initBlocks();
		ModItems.initItems();
		ModPotions.initPotions();

		proxy.initEntities();
		proxy.initTileEntities();
		proxy.initRenders();

		MinecraftForge.EVENT_BUS.register(new EntityInteractionHandler());

		GameRegistry.registerPlayerTracker(new PlayerTracker());
		NetworkRegistry.instance().registerGuiHandler(modInstance, new GuiHandler());

		proxy.initTickHandlers();
	}

	@PostInit
	public void onPostInit(FMLPostInitializationEvent event) {
		ModCraftingRecipes.initCraftingRecipes();
		ModResearchItems.registerModResearchItems();
		ModInfusionRecipes.initInfusionRecipes();
		ModArcaneRecipes.initArcaneRecipes();
		
		ModItems.applyObjectTags();
		ModBlocks.applyObjectTags();
	}
}
