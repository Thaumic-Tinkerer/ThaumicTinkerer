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
 * File Created @ [24 Apr 2013, 21:02:12 (GMT)]
 */
package vazkii.tinkerer.core.proxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.entity.EntityDeathRune;
import vazkii.tinkerer.entity.EntityLovePotion;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketAnimationTabletButton;
import vazkii.tinkerer.network.packet.PacketMobMagnetButton;
import vazkii.tinkerer.tile.TileEntityAnimationTablet;
import vazkii.tinkerer.tile.TileEntityInterface;
import vazkii.tinkerer.tile.TileEntityMagnet;
import vazkii.tinkerer.tile.TileEntityMobMagnet;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.tile.TileEntityWardChest;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class TTCommonProxy {

	public void initTickHandlers() { }

	public void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntityTransmutator.class, tileName(LibBlockNames.TRANSMUTATOR));
		GameRegistry.registerTileEntity(TileEntityWardChest.class, tileName(LibBlockNames.WARD_CHEST));
		GameRegistry.registerTileEntity(TileEntityAnimationTablet.class, tileName(LibBlockNames.ANIMATION_TABLET));
		GameRegistry.registerTileEntity(TileEntityMagnet.class, tileName(LibBlockNames.MAGNET));
		GameRegistry.registerTileEntity(TileEntityMobMagnet.class, tileName(LibBlockNames.MOB_MAGNET));
		GameRegistry.registerTileEntity(TileEntityInterface.class, tileName(LibBlockNames.INTERFACE));
	}

	public void initRenders() { }

	public void initEntities() {
		int id = 0;
		EntityRegistry.registerModEntity(EntityDeathRune.class, LibMisc.MOD_ID + "_" + LibItemNames.DEATH_RUNE, id++, ThaumicTinkerer.modInstance, 64, 10, false);
		EntityRegistry.registerModEntity(EntityLovePotion.class, LibMisc.MOD_ID + "_" + LibItemNames.LOVE_POTION, id++, ThaumicTinkerer.modInstance, 64, 10, true);
	}

	public void initPackets() {
		PacketManager.packetHandlers.add(new PacketAnimationTabletButton());
		PacketManager.packetHandlers.add(new PacketMobMagnetButton());
	}

	private static String tileName(String name) {
		return LibMisc.MOD_ID + "_" + name;
	}

	public void sigilLightning(World world, Entity entity, Vec3 end) { }

	public void sanityCheckedFrozenParticles(EntityLiving entity) { }

	public void sanityCheckedPossessedParticles(EntityLiving entity) { }
}