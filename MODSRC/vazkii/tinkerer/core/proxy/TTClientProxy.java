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
 * File Created @ [24 Apr 2013, 21:02:22 (GMT)]
 */
package vazkii.tinkerer.core.proxy;

import vazkii.tinkerer.client.render.tile.RenderTileTransmutator;
import vazkii.tinkerer.client.render.tile.RenderTileWardChest;
import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketTransmutatorSync;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.tile.TileEntityWardChest;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class TTClientProxy extends TTCommonProxy {

	@Override
	public void initTickHandlers() {
		super.initTickHandlers();

		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
	}


	@Override
	public void initTileEntities() {
		super.initTileEntities();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransmutator.class, new RenderTileTransmutator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWardChest.class, new RenderTileWardChest());
	}

	@Override
	public void initPackets() {
		super.initPackets();
		PacketManager.packetHandlers.add(new PacketTransmutatorSync());
	}

}
