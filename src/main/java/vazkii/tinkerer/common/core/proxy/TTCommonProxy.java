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

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import dan200.computer.api.ComputerCraftAPI;
import dan200.computer.api.IPeripheralHandler;
import dan200.turtle.api.TurtleAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.entities.golems.EnumGolemType;
import thaumcraft.common.tiles.*;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.block.tile.TileFunnel;
import vazkii.tinkerer.common.block.tile.TileRepairer;
import vazkii.tinkerer.common.block.tile.peripheral.PeripheralHandler;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import vazkii.tinkerer.common.compat.FumeTool;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.core.handler.kami.DimensionalShardDropHandler;
import vazkii.tinkerer.common.core.handler.kami.KamiArmorHandler;
import vazkii.tinkerer.common.core.handler.kami.SoulHeartHandler;
import vazkii.tinkerer.common.core.helper.NumericAspectHelper;
import vazkii.tinkerer.common.enchantment.ModEnchantments;
import vazkii.tinkerer.common.enchantment.core.EnchantmentManager;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.network.GuiHandler;
import vazkii.tinkerer.common.network.PlayerTracker;
import vazkii.tinkerer.common.network.packet.PacketEnchanterAddEnchant;
import vazkii.tinkerer.common.network.packet.PacketEnchanterStartWorking;
import vazkii.tinkerer.common.network.packet.PacketMobMagnetButton;
import vazkii.tinkerer.common.network.packet.PacketTabletButton;
import vazkii.tinkerer.common.network.packet.kami.PacketSoulHearts;
import vazkii.tinkerer.common.network.packet.kami.PacketToggleArmor;
import vazkii.tinkerer.common.network.packet.kami.PacketWarpGateButton;
import vazkii.tinkerer.common.network.packet.kami.PacketWarpGateTeleport;
import vazkii.tinkerer.common.potion.ModPotions;
import vazkii.tinkerer.common.research.ModRecipes;
import vazkii.tinkerer.common.research.ModResearch;

import java.lang.reflect.Field;

public class TTCommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		ModBlocks.initBlocks();
		ModItems.initItems();
        NumericAspectHelper.init();
        initCCPeripherals();
	}

	public void init(FMLInitializationEvent event) {
		ModEnchantments.initEnchantments();
		EnchantmentManager.initEnchantmentData();
		ModPotions.initPotions();
		ModBlocks.initTileEntities();
		NetworkRegistry.instance().registerGuiHandler(ThaumicTinkerer.instance, new GuiHandler());
        RegisterPackets();
        FMLCommonHandler.instance().bus().register(new PlayerTracker());


		if(ConfigHandler.enableKami) {
            FMLCommonHandler.instance().bus().register(new DimensionalShardDropHandler());
            FMLCommonHandler.instance().bus().register(new SoulHeartHandler());
		}
	}

    private void RegisterPackets() {
        ThaumicTinkerer.packetPipeline.registerPacket(PacketSoulHearts.class);
        ThaumicTinkerer.packetPipeline.registerPacket(PacketToggleArmor.class);
        ThaumicTinkerer.packetPipeline.registerPacket(PacketWarpGateButton.class);
        ThaumicTinkerer.packetPipeline.registerPacket(PacketWarpGateTeleport.class);
        ThaumicTinkerer.packetPipeline.registerPacket(PacketEnchanterAddEnchant.class);
        ThaumicTinkerer.packetPipeline.registerPacket(PacketEnchanterStartWorking.class);
        ThaumicTinkerer.packetPipeline.registerPacket(PacketMobMagnetButton.class);
        ThaumicTinkerer.packetPipeline.registerPacket(PacketTabletButton.class);
    }

    public void postInit(FMLPostInitializationEvent event) {
		ModRecipes.initRecipes();
		ModResearch.initResearch();
	}

	protected void initCCPeripherals() {
		IPeripheralHandler handler = new PeripheralHandler();

		Class[] peripheralClasses = new Class[] {
				TileAlembic.class, TileCentrifuge.class, TileCrucible.class, TileFunnel.class,
				TileInfusionMatrix.class, TileJarFillable.class, TileJarNode.class, TileNode.class,
				TileRepairer.class, TileTubeFilter.class, TileTransvectorInterface.class, TileWandPedestal.class,
				TileDeconstructionTable.class, TileJarBrain.class, TileSensor.class, TileArcaneBore.class,IEssentiaTransport.class
		};
        // DUMMY CHANGE
        for(Class clazz : peripheralClasses)
			ComputerCraftAPI.registerExternalPeripheral(clazz, handler);
			
		TurtleAPI.registerUpgrade(new FumeTool());
	}

	public boolean isClient() {
		return false;
	}

    public boolean armorStatus(EntityPlayer player)
    {
        return KamiArmorHandler.getArmorStatus(player);
    }

    public void setArmor(EntityPlayer player,boolean status)
    {
        KamiArmorHandler.setArmorStatus(player,status);
    }
	public EntityPlayer getClientPlayer() {
		return null;
	}

	public void shadowSparkle(World world, float x, float y, float z, int size) {
		// NO-OP
	}
}
