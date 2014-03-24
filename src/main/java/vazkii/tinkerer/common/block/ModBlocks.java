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
 * File Created @ [4 Sep 2013, 16:56:52 (GMT)]
 */
package vazkii.tinkerer.common.block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import vazkii.tinkerer.common.block.kami.BlockWarpGate;
import vazkii.tinkerer.common.block.mobilizer.BlockMobilizer;
import vazkii.tinkerer.common.block.mobilizer.BlockMobilizerRelay;
import vazkii.tinkerer.common.block.quartz.BlockDarkQuartz;
import vazkii.tinkerer.common.block.quartz.BlockDarkQuartzSlab;
import vazkii.tinkerer.common.block.quartz.BlockDarkQuartzStairs;
import vazkii.tinkerer.common.block.tile.*;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorDislocator;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import vazkii.tinkerer.common.block.transvector.BlockTransvectorDislocator;
import vazkii.tinkerer.common.block.transvector.BlockTransvectorInterface;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.item.ItemBlockMagnet;
import vazkii.tinkerer.common.item.kami.ItemBlockWarpGate;
import vazkii.tinkerer.common.item.quartz.ItemDarkQuartzBlock;
import vazkii.tinkerer.common.item.quartz.ItemDarkQuartzSlab;
import vazkii.tinkerer.common.lib.LibBlockNames;

public final class ModBlocks {

	public static Block darkQuartz;
	public static Block darkQuartzSlab;
	public static Block darkQuartzSlabFull;
	public static Block darkQuartzStairs;
	public static Block interfase;
	public static Block gaseousLight;
	public static Block gaseousShadow;
	public static Block animationTablet;
	public static Block nitorGas;
	public static Block magnet;
	public static Block enchanter;
	public static Block funnel;
	public static Block dislocator;
	public static Block repairer;
	public static Block aspectAnalyzer;
	public static Block platform;
    public static Block spawner;
	public static Block warpGate;

	public static Block mobilizerRelay;

	public static Block mobilizer;

	public static Block bedrock;


	public static Block portal;
	public static Block golemConnector;
	public static void initBlocks() {
		darkQuartz = new BlockDarkQuartz().setBlockName(LibBlockNames.DARK_QUARTZ);
		darkQuartzSlab = new BlockDarkQuartzSlab(false).setBlockName(LibBlockNames.DARK_QUARTZ_SLAB);
		darkQuartzSlabFull = new BlockDarkQuartzSlab(true).setBlockName(LibBlockNames.DARK_QUARTZ_SLAB);
		darkQuartzStairs = new BlockDarkQuartzStairs().setBlockName(LibBlockNames.DARK_QUARTZ_STAIRS);
		interfase = new BlockTransvectorInterface().setBlockName(LibBlockNames.INTERFACE);
		gaseousLight = new BlockGaseousLight().setBlockName(LibBlockNames.GASEOUS_LIGHT);
		gaseousShadow = new BlockGaseousShadow().setBlockName(LibBlockNames.GASEOUS_SHADOW);
		animationTablet = new BlockAnimationTablet().setBlockName(LibBlockNames.ANIMATION_TABLET);
		nitorGas = new BlockNitorGas().setBlockName(LibBlockNames.NITOR_GAS);
		magnet = new BlockMagnet().setBlockName(LibBlockNames.MAGNET);
		enchanter = new BlockEnchanter().setBlockName(LibBlockNames.ENCHANTER);
		funnel = new BlockFunnel().setBlockName(LibBlockNames.FUNNEL);
		dislocator = new BlockTransvectorDislocator().setBlockName(LibBlockNames.DISLOCATOR);
		repairer = new BlockRepairer().setBlockName(LibBlockNames.REPAIRER);
		aspectAnalyzer = new BlockAspectAnalyzer().setBlockName(LibBlockNames.ASPECT_ANALYZER);
		platform = new BlockPlatform().setBlockName(LibBlockNames.PLATFORM);

		mobilizerRelay = new BlockMobilizerRelay().setBlockName(LibBlockNames.MOBILIZER_RELAY);
		mobilizer = new BlockMobilizer().setBlockName(LibBlockNames.MOBILIZER);
		golemConnector=new BlockGolemConnector().setBlockName(LibBlockNames.GOLEMCONNECTOR);

		spawner = new BlockSummon().setBlockName(LibBlockNames.SPAWNER);
		if(ConfigHandler.enableKami) {

			warpGate = new BlockWarpGate().setBlockName(LibBlockNames.WARP_GATE);
			if(ConfigHandler.bedrockDimensionID != 0) {
				/*
				Block.blocksList[7]=null;
				bedrock = new BlockBedrockKAMI();

				try {
					ReflectionHelper.findField(Block.class, LibObfuscation.BEDROCK);
					Field bedrockField=ReflectionHelper.findField(Block.class, LibObfuscation.BEDROCK);
					bedrockField.setAccessible(true);
					Field modifiersField = Field.class.getDeclaredField("modifiers");
					modifiersField.setAccessible(true);
					int modifiers = modifiersField.getInt(bedrockField);
					modifiers &= ~Modifier.FINAL;
					modifiersField.setInt(bedrockField, modifiers);
					bedrockField.set(null, bedrock);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				*/
				//portal = new BlockBedrockPortal().setBlockName(LibBlockNames.PORTAL);
			}
		}

		registerBlocks();
		registerMultiparts();
	}

	private static void registerBlocks() {
		GameRegistry.registerBlock(darkQuartz, ItemDarkQuartzBlock.class, LibBlockNames.DARK_QUARTZ);
		GameRegistry.registerBlock(darkQuartzStairs, LibBlockNames.DARK_QUARTZ_STAIRS);
		GameRegistry.registerBlock(darkQuartzSlab, ItemDarkQuartzSlab.class, LibBlockNames.DARK_QUARTZ_SLAB);
		GameRegistry.registerBlock(darkQuartzSlabFull, ItemDarkQuartzSlab.class, LibBlockNames.DARK_QUARTZ_SLAB_FULL);
		GameRegistry.registerBlock(interfase, LibBlockNames.INTERFACE);
		GameRegistry.registerBlock(gaseousLight, LibBlockNames.GASEOUS_LIGHT);
		GameRegistry.registerBlock(gaseousShadow, LibBlockNames.GASEOUS_SHADOW);
		GameRegistry.registerBlock(animationTablet, LibBlockNames.ANIMATION_TABLET);
		GameRegistry.registerBlock(magnet, ItemBlockMagnet.class, LibBlockNames.MAGNET);
		GameRegistry.registerBlock(enchanter, LibBlockNames.ENCHANTER);
		GameRegistry.registerBlock(funnel, LibBlockNames.FUNNEL);
		GameRegistry.registerBlock(dislocator, LibBlockNames.DISLOCATOR);
		GameRegistry.registerBlock(repairer, LibBlockNames.REPAIRER);
		GameRegistry.registerBlock(aspectAnalyzer, LibBlockNames.ASPECT_ANALYZER);
		GameRegistry.registerBlock(platform, LibBlockNames.PLATFORM);

		GameRegistry.registerBlock(mobilizerRelay, LibBlockNames.MOBILIZER_RELAY);

		GameRegistry.registerBlock(mobilizer, LibBlockNames.MOBILIZER);
		GameRegistry.registerBlock(golemConnector, LibBlockNames.GOLEMCONNECTOR);

		GameRegistry.registerBlock(spawner, LibBlockNames.SPAWNER);
		if(ConfigHandler.enableKami) {
			GameRegistry.registerBlock(warpGate, ItemBlockWarpGate.class, LibBlockNames.WARP_GATE);

			//if(ConfigHandler.bedrockDimensionID != 0) {
			//	GameRegistry.registerBlock(bedrock, LanguageRegistry.instance().getStringLocalization("bedrock"));

			//	GameRegistry.registerBlock(portal, LibBlockNames.PORTAL);
			//}
		}
	}

	private static void registerMultiparts() {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				Class clazz = Class.forName("vazkii.tinkerer.common.block.multipart.MultipartHandler");
				clazz.newInstance();
			} catch(Throwable e) {
				e.printStackTrace();
			}
		}
	}


	public static void initTileEntities() {
		GameRegistry.registerTileEntity(TileTransvectorInterface.class, LibBlockNames.INTERFACE);
		GameRegistry.registerTileEntity(TileAnimationTablet.class, LibBlockNames.ANIMATION_TABLET);
		GameRegistry.registerTileEntity(TileMagnet.class, LibBlockNames.MAGNET);
		GameRegistry.registerTileEntity(TileMobMagnet.class, LibBlockNames.MOB_MAGNET);
		GameRegistry.registerTileEntity(TileEnchanter.class, LibBlockNames.ENCHANTER);
		GameRegistry.registerTileEntity(TileFunnel.class, LibBlockNames.FUNNEL);
		GameRegistry.registerTileEntity(TileTransvectorDislocator.class, LibBlockNames.DISLOCATOR);
		GameRegistry.registerTileEntity(TileRepairer.class, LibBlockNames.REPAIRER);
		GameRegistry.registerTileEntity(TileAspectAnalyzer.class, LibBlockNames.ASPECT_ANALYZER);
		GameRegistry.registerTileEntity(TileCamo.class, LibBlockNames.CAMO);


		GameRegistry.registerTileEntity(TileEntityMobilizer.class, LibBlockNames.MOBILIZER);

		GameRegistry.registerTileEntity(TileEntityRelay.class, LibBlockNames.MOBILIZER_RELAY);
		GameRegistry.registerTileEntity(TileGolemConnector.class, LibBlockNames.GOLEMCONNECTOR);

		GameRegistry.registerTileEntity(TileSummon.class, LibBlockNames.SPAWNER);
		if(ConfigHandler.enableKami) {
			GameRegistry.registerTileEntity(TileWarpGate.class, LibBlockNames.WARP_GATE);
		}
	}

}