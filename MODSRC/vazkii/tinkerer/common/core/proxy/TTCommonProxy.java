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
 * File Created @ [4 Sep 2013, 16:29:41 (GMT)]
 */
package vazkii.tinkerer.common.core.proxy;

import thaumcraft.common.tiles.TileAlembic;
import thaumcraft.common.tiles.TileCentrifuge;
import thaumcraft.common.tiles.TileCrucible;
import thaumcraft.common.tiles.TileDeconstructionTable;
import thaumcraft.common.tiles.TileInfusionMatrix;
import thaumcraft.common.tiles.TileJarFillable;
import thaumcraft.common.tiles.TileJarNode;
import thaumcraft.common.tiles.TileNode;
import thaumcraft.common.tiles.TileTubeFilter;
import thaumcraft.common.tiles.TileWandPedestal;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.block.tile.TileFunnel;
import vazkii.tinkerer.common.block.tile.TileRepairer;
import vazkii.tinkerer.common.block.tile.peripheral.PeripheralHandler;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.enchantment.ModEnchantments;
import vazkii.tinkerer.common.enchantment.core.EnchantmentManager;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.network.GuiHandler;
import vazkii.tinkerer.common.network.PlayerTracker;
import vazkii.tinkerer.common.potion.ModPotions;
import vazkii.tinkerer.common.research.ModRecipes;
import vazkii.tinkerer.common.research.ModResearch;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computer.api.ComputerCraftAPI;
import dan200.computer.api.IPeripheralHandler;

public class TTCommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		ModBlocks.initBlocks();
		ModItems.initItems();

		initCCPeripherals();
	}

	public void init(FMLInitializationEvent event) {
		ModEnchantments.initEnchantments();
		EnchantmentManager.initEnchantmentData();
		ModPotions.initPotions();
		ModBlocks.initTileEntities();
		NetworkRegistry.instance().registerGuiHandler(ThaumicTinkerer.instance, new GuiHandler());
		GameRegistry.registerPlayerTracker(new PlayerTracker());
	}

	public void postInit(FMLPostInitializationEvent event) {
		ModRecipes.initRecipes();
		ModResearch.initResearch();
	}

	protected void initCCPeripherals() {
		IPeripheralHandler handler = new PeripheralHandler();
		ComputerCraftAPI.registerExternalPeripheral(TileAlembic.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileCentrifuge.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileCrucible.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileFunnel.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileInfusionMatrix.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileJarFillable.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileJarNode.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileNode.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileRepairer.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileTubeFilter.class, handler);
		ComputerCraftAPI.registerExternalPeripheral(TileWandPedestal.class, handler);

		ComputerCraftAPI.registerExternalPeripheral(TileDeconstructionTable.class, handler);
	}

	public boolean isClient() {
		return false;
	}
}
