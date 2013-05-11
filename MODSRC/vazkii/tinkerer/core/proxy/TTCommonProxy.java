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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import vazkii.tinkerer.ThaumicTinkerer;
import vazkii.tinkerer.entity.EntityDeathRune;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketVerification;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.tile.TileEntityWardChest;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class TTCommonProxy {

	public void initTickHandlers() { }

	public void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntityTransmutator.class, tileName(LibBlockNames.TRANSMUTATOR));
		GameRegistry.registerTileEntity(TileEntityWardChest.class, tileName(LibBlockNames.WARD_CHEST));
	}
	
	public void initRenders() { }
	
	public void initEntities() {
		EntityRegistry.registerModEntity(EntityDeathRune.class, LibMisc.MOD_ID + "_" + LibItemNames.DEATH_RUNE, 0, ThaumicTinkerer.modInstance, 64, 10, false);
	}

	public void initPackets() {
		PacketManager.packetHandlers.add(new PacketVerification());
	}

	private static String tileName(String name) {
		return LibMisc.MOD_ID + "_" + name;
	}

	public void sigilLightning(World world, Entity entity, Vec3 end) { }
}
