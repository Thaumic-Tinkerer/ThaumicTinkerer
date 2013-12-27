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

import net.minecraft.block.Block;
import vazkii.tinkerer.common.block.quartz.BlockDarkQuartz;
import vazkii.tinkerer.common.block.quartz.BlockDarkQuartzSlab;
import vazkii.tinkerer.common.block.quartz.BlockDarkQuartzStairs;
import vazkii.tinkerer.common.block.tile.TileAspectAnalyzer;
import vazkii.tinkerer.common.block.tile.TileCamo;
import vazkii.tinkerer.common.block.tile.TileEnchanter;
import vazkii.tinkerer.common.block.tile.TileFunnel;
import vazkii.tinkerer.common.block.tile.TileMagnet;
import vazkii.tinkerer.common.block.tile.TileMobMagnet;
import vazkii.tinkerer.common.block.tile.TileRepairer;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorDislocator;
import vazkii.tinkerer.common.block.tile.transvector.TileTransvectorInterface;
import vazkii.tinkerer.common.block.transvector.BlockTransvectorDislocator;
import vazkii.tinkerer.common.block.transvector.BlockTransvectorInterface;
import vazkii.tinkerer.common.item.ItemBlockMagnet;
import vazkii.tinkerer.common.item.quartz.ItemDarkQuartzBlock;
import vazkii.tinkerer.common.item.quartz.ItemDarkQuartzSlab;
import vazkii.tinkerer.common.lib.LibBlockIDs;
import vazkii.tinkerer.common.lib.LibBlockNames;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

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

	public static void initBlocks() {
		darkQuartz = new BlockDarkQuartz(LibBlockIDs.idDarkQuartz).setUnlocalizedName(LibBlockNames.DARK_QUARTZ);
		darkQuartzSlab = new BlockDarkQuartzSlab(LibBlockIDs.idDarkQuartzSlab, false).setUnlocalizedName(LibBlockNames.DARK_QUARTZ_SLAB);
		darkQuartzSlabFull = new BlockDarkQuartzSlab(LibBlockIDs.idDarkQuartzSlabFull, true).setUnlocalizedName(LibBlockNames.DARK_QUARTZ_SLAB);
		darkQuartzStairs = new BlockDarkQuartzStairs(LibBlockIDs.idDarkQuartzStairs).setUnlocalizedName(LibBlockNames.DARK_QUARTZ_STAIRS);
		interfase = new BlockTransvectorInterface(LibBlockIDs.idInterface).setUnlocalizedName(LibBlockNames.INTERFACE);
		gaseousLight = new BlockGaseousLight(LibBlockIDs.idGaseousLight).setUnlocalizedName(LibBlockNames.GASEOUS_LIGHT);
		gaseousShadow = new BlockGaseousShadow(LibBlockIDs.idGaseousShadow).setUnlocalizedName(LibBlockNames.GASEOUS_SHADOW);
		animationTablet = new BlockAnimationTablet(LibBlockIDs.idAnimationTablet).setUnlocalizedName(LibBlockNames.ANIMATION_TABLET);
		nitorGas = new BlockNitorGas(LibBlockIDs.idNitorGas).setUnlocalizedName(LibBlockNames.NITOR_GAS);
		magnet = new BlockMagnet(LibBlockIDs.idMagnet).setUnlocalizedName(LibBlockNames.MAGNET);
		enchanter = new BlockEnchanter(LibBlockIDs.idEnchanter).setUnlocalizedName(LibBlockNames.ENCHANTER);
		funnel = new BlockFunnel(LibBlockIDs.idFunnel).setUnlocalizedName(LibBlockNames.FUNNEL);
		dislocator = new BlockTransvectorDislocator(LibBlockIDs.idDislocator).setUnlocalizedName(LibBlockNames.DISLOCATOR);
		repairer = new BlockRepairer(LibBlockIDs.idRepairer).setUnlocalizedName(LibBlockNames.REPAIRER);
		aspectAnalyzer = new BlockAspectAnalyzer(LibBlockIDs.idAspectAnalyzer).setUnlocalizedName(LibBlockNames.ASPECT_ANALYZER);
		
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
	}

	private static void registerMultiparts() {
		if(Loader.isModLoaded("ForgeMultipart")) {
			try {
				Class clazz = Class.forName("vazkii.tinkerer.common.block.multipart.MultipartHandler");
				clazz.newInstance();
			} catch(Throwable e) {}
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
	}

}