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

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import thaumcraft.client.fx.FXLightningBolt;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.client.render.block.RenderMagnet;
import vazkii.tinkerer.client.render.block.RenderMobMagnet;
import vazkii.tinkerer.client.render.block.RenderWardChest;
import vazkii.tinkerer.client.render.entity.RenderDeathRune;
import vazkii.tinkerer.client.render.item.RenderItemConnector;
import vazkii.tinkerer.client.render.item.RenderItemFluxDetector;
import vazkii.tinkerer.client.render.tile.RenderTileAnimationTablet;
import vazkii.tinkerer.client.render.tile.RenderTileMagnet;
import vazkii.tinkerer.client.render.tile.RenderTileMobMagnet;
import vazkii.tinkerer.client.render.tile.RenderTileTransmutator;
import vazkii.tinkerer.client.render.tile.RenderTileWardChest;
import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.client.util.handler.SoulHeartClientHandler;
import vazkii.tinkerer.entity.EntityDeathRune;
import vazkii.tinkerer.entity.EntityLovePotion;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibRenderIDs;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketAnimationTabletSync;
import vazkii.tinkerer.network.packet.PacketMobMagnetSync;
import vazkii.tinkerer.network.packet.PacketSoulHeartSync;
import vazkii.tinkerer.network.packet.PacketTransmutatorSync;
import vazkii.tinkerer.network.packet.PacketVerification;
import vazkii.tinkerer.tile.TileEntityAnimationTablet;
import vazkii.tinkerer.tile.TileEntityMagnet;
import vazkii.tinkerer.tile.TileEntityMobMagnet;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.tile.TileEntityWardChest;
import vazkii.tinkerer.util.helper.MiscHelper;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class TTClientProxy extends TTCommonProxy {

	@Override
	public void initTickHandlers() {
		super.initTickHandlers();

		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);

		MinecraftForge.EVENT_BUS.register(new SoulHeartClientHandler());
	}

	@Override
	public void initTileEntities() {
		super.initTileEntities();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTransmutator.class, new RenderTileTransmutator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWardChest.class, new RenderTileWardChest());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnimationTablet.class, new RenderTileAnimationTablet());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagnet.class, new RenderTileMagnet());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMobMagnet.class, new RenderTileMobMagnet());
	}

	@Override
	public void initEntities() {
		super.initEntities();

		RenderingRegistry.registerEntityRenderingHandler(EntityDeathRune.class, new RenderDeathRune());
		RenderingRegistry.registerEntityRenderingHandler(EntityLovePotion.class, new RenderSnowball(ModItems.lovePotion, 0));
	}

	@Override
	public void initPackets() {
		super.initPackets();

		PacketManager.packetHandlers.add(new PacketVerification());
		PacketManager.packetHandlers.add(new PacketTransmutatorSync());
		PacketManager.packetHandlers.add(new PacketAnimationTabletSync());
		PacketManager.packetHandlers.add(new PacketSoulHeartSync());
		PacketManager.packetHandlers.add(new PacketMobMagnetSync());
	}

	@Override
	public void initRenders() {
		LibRenderIDs.idWardChest = RenderingRegistry.getNextAvailableRenderId();
		LibRenderIDs.idMagnet = RenderingRegistry.getNextAvailableRenderId();
		LibRenderIDs.idMobMagnet = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new RenderWardChest());
		RenderingRegistry.registerBlockHandler(new RenderMagnet());
		RenderingRegistry.registerBlockHandler(new RenderMobMagnet());

		MinecraftForgeClient.registerItemRenderer(ModItems.fluxDetector.itemID, new RenderItemFluxDetector());
		MinecraftForgeClient.registerItemRenderer(ModItems.connector.itemID, new RenderItemConnector());
	}

	@Override
	public void sigilLightning(World world, Entity entity, Vec3 end) {
		FXLightningBolt lightning = new FXLightningBolt(world, entity.posX + entity.width / 2, entity.posY - entity.height / 2, entity.posZ + entity.width / 2, end.xCoord, end.yCoord, end.zCoord, world.rand.nextLong(), 10, 2F, 5);
		lightning.defaultFractal();
		lightning.setType(5);
		lightning.finalizeBolt();
	}

	@Override
	public void sanityCheckedFrozenParticles(EntityLiving entity) {
		for(int i = 0; i < 6; i++) {
			float x = (float) (entity.posX + (entity.worldObj.rand.nextDouble() - 0.5F) * (entity.width * 2F));
            float y = (float) (entity.posY + entity.worldObj.rand.nextDouble() * entity.height);
            float z = (float) (entity.posZ  + (entity.worldObj.rand.nextDouble() - 0.5F) * (entity.width * 2F));

            float size = (float) Math.random();
            float gravity = (float) (Math.random() / 20F);

            if(MiscHelper.getMc().renderViewEntity != null)
            	ThaumicTinkerer.tcProxy.sparkle(x, y, z, size, 2, gravity);
		}
	}

	@Override
	public void sanityCheckedPossessedParticles(EntityLiving entity) {
		for(int i = 0; i < 3; i++) {
			float x = (float) (entity.posX + (entity.worldObj.rand.nextDouble() - 0.5F) * (entity.width * 2F));
            float y = (float) (entity.posY + entity.worldObj.rand.nextDouble() * entity.height);
            float z = (float) (entity.posZ  + (entity.worldObj.rand.nextDouble() - 0.5F) * (entity.width * 2F));

            float size = (float) (Math.random() / 2F);
            float gravity = (float) (Math.random() / 20F);

            if(MiscHelper.getMc().renderViewEntity != null)
            	ThaumicTinkerer.tcProxy.wispFX2(entity.worldObj, x, y, z, size, 5, false, gravity);
		}
	}
}
