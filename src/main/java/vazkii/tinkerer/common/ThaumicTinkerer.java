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
 * File Created @ [4 Sep 2013, 16:01:28 (GMT)]
 */
package vazkii.tinkerer.common;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.DimensionManager;
import thaumcraft.common.CommonProxy;
import thaumcraft.common.Thaumcraft;
import vazkii.tinkerer.api.InterModCommsOperations;
import vazkii.tinkerer.common.core.commands.KamiUnlockedCommand;
import vazkii.tinkerer.common.core.commands.MaxResearchCommand;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.core.proxy.TTCommonProxy;
import vazkii.tinkerer.common.dim.WorldProviderBedrock;
import vazkii.tinkerer.common.lib.LibMisc;
import vazkii.tinkerer.common.research.KamiResearchItem;

import java.util.Arrays;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION, dependencies = LibMisc.DEPENDENCIES)
public class ThaumicTinkerer {

	@Instance(LibMisc.MOD_ID)
	public static ThaumicTinkerer instance;

	@SidedProxy(clientSide = LibMisc.CLIENT_PROXY, serverSide = LibMisc.COMMON_PROXY)
	public static TTCommonProxy proxy;

	public static CommonProxy tcProxy;
	public static SimpleNetworkWrapper netHandler = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_ID + "|B");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		tcProxy = Thaumcraft.proxy;
		proxy.preInit(event);
		if (Loader.isModLoaded("Waila")) {
			FMLInterModComms.sendMessage("Waila", "register", "vazkii.tinkerer.common.compat.TTinkererProvider.callbackRegister");
		}
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		MinecraftServer server = MinecraftServer.getServer();
		ICommandManager command = server.getCommandManager();
		ServerCommandManager manager = (ServerCommandManager) command;
		manager.registerCommand(new MaxResearchCommand());
		manager.registerCommand(new KamiUnlockedCommand());
	}

	@EventHandler
	public void HandleIMCMessages(FMLInterModComms.IMCEvent messages) {
		for (FMLInterModComms.IMCMessage message : messages.getMessages()) {
			if (message.key.equalsIgnoreCase(InterModCommsOperations.ADD_RESEARCH_BLACKLIST)) {
				String[] values = message.getStringValue().split(",");
				KamiResearchItem.Blacklist.addAll(Arrays.asList(values));
			}
		}
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);

		if (ConfigHandler.enableKami && ConfigHandler.bedrockDimensionID != 0) {
			DimensionManager.registerProviderType(ConfigHandler.bedrockDimensionID, WorldProviderBedrock.class, false);
			DimensionManager.registerDimension(ConfigHandler.bedrockDimensionID, ConfigHandler.bedrockDimensionID);
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);

	}
}
