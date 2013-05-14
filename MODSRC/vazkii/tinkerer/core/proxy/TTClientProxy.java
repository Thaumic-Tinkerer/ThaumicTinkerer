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

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import thaumcraft.client.fx.FXLightningBolt;
import vazkii.tinkerer.client.render.block.RenderWardChest;
import vazkii.tinkerer.client.render.entity.RenderDeathRune;
import vazkii.tinkerer.client.render.tile.RenderTileAnimationTablet;
import vazkii.tinkerer.client.render.tile.RenderTileTransmutator;
import vazkii.tinkerer.client.render.tile.RenderTileWardChest;
import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.entity.EntityDeathRune;
import vazkii.tinkerer.lib.LibRenderIDs;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketAnimationTabletSync;
import vazkii.tinkerer.network.packet.PacketTransmutatorSync;
import vazkii.tinkerer.network.packet.PacketVerification;
import vazkii.tinkerer.tile.TileEntityAnimationTablet;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.tile.TileEntityWardChest;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnimationTablet.class, new RenderTileAnimationTablet());
	}

	@Override
	public void initEntities() {
		super.initEntities();

		RenderingRegistry.registerEntityRenderingHandler(EntityDeathRune.class, new RenderDeathRune());
	}

	@Override
	public void initPackets() {
		super.initPackets();

		PacketManager.packetHandlers.add(new PacketVerification());
		PacketManager.packetHandlers.add(new PacketTransmutatorSync());
		PacketManager.packetHandlers.add(new PacketAnimationTabletSync());
	}

	@Override
	public void initRenders() {
		LibRenderIDs.idWardChest = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new RenderWardChest());
	}

	@Override
	public void sigilLightning(World world, Entity entity, Vec3 end) {
		FXLightningBolt lightning = new FXLightningBolt(world, entity.posX + entity.width / 2, entity.posY - entity.height / 2, entity.posZ + entity.width / 2, end.xCoord, end.yCoord, end.zCoord, world.rand.nextLong(), 10, 2F, 5);
		lightning.defaultFractal();
		lightning.setType(5);
		lightning.finalizeBolt();
	}

}
