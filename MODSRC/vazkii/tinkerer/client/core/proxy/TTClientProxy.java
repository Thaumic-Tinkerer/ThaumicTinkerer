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
package vazkii.tinkerer.client.core.proxy;

import net.minecraftforge.common.MinecraftForge;
import vazkii.tinkerer.client.core.handler.ClientTickHandler;
import vazkii.tinkerer.client.core.handler.HUDHandler;
import vazkii.tinkerer.client.core.handler.LocalizationHandler;
import vazkii.tinkerer.client.lib.LibRenderIDs;
import vazkii.tinkerer.client.render.block.RenderMagnet;
import vazkii.tinkerer.client.render.tile.RenderTileAnimationTablet;
import vazkii.tinkerer.client.render.tile.RenderTileEnchanter;
import vazkii.tinkerer.client.render.tile.RenderTileFunnel;
import vazkii.tinkerer.client.render.tile.RenderTileMagnet;
import vazkii.tinkerer.common.block.tile.TileEnchanter;
import vazkii.tinkerer.common.block.tile.TileFunnel;
import vazkii.tinkerer.common.block.tile.TileMagnet;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.core.proxy.TTCommonProxy;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class TTClientProxy extends TTCommonProxy {

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);

		LocalizationHandler.loadLocalizations();
		MinecraftForge.EVENT_BUS.register(new HUDHandler());
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		registerTiles();
		registerRenderIDs();
	}

	private void registerTiles() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileAnimationTablet.class, new RenderTileAnimationTablet());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMagnet.class, new RenderTileMagnet());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEnchanter.class, new RenderTileEnchanter());
		ClientRegistry.bindTileEntitySpecialRenderer(TileFunnel.class, new RenderTileFunnel());
	}

	private void registerRenderIDs() {
		LibRenderIDs.idMagnet = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new RenderMagnet());
	}

	@Override
	protected void initCCPeripherals() {
		// NO-OP
	}

}
