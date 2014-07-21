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
 * File Created @ [9 Sep 2013, 16:29:28 (GMT)]
 */
package thaumic.tinkerer.common.network;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumic.tinkerer.client.gui.*;
import thaumic.tinkerer.client.gui.kami.GuiIchorPouch;
import thaumic.tinkerer.client.gui.kami.GuiWarpGate;
import thaumic.tinkerer.client.gui.kami.GuiWarpGateDestinations;
import thaumic.tinkerer.common.block.tile.TileAspectAnalyzer;
import thaumic.tinkerer.common.block.tile.TileEnchanter;
import thaumic.tinkerer.common.block.tile.TileMobMagnet;
import thaumic.tinkerer.common.block.tile.TileRPlacer;
import thaumic.tinkerer.common.block.tile.container.*;
import thaumic.tinkerer.common.block.tile.container.kami.ContainerIchorPouch;
import thaumic.tinkerer.common.block.tile.container.kami.ContainerWarpGate;
import thaumic.tinkerer.common.block.tile.kami.TileWarpGate;
import thaumic.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import thaumic.tinkerer.common.lib.LibGuiIDs;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (ID) {
			case LibGuiIDs.GUI_ID_TABLET:
				return new ContainerAnimationTablet((TileAnimationTablet) tile, player.inventory);

			case LibGuiIDs.GUI_ID_MOB_MAGNET:
				return new ContainerMobMagnet((TileMobMagnet) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ENCHANTER:
				return new ContainerEnchanter((TileEnchanter) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ASPECT_ANALYZER:
				return new ContainerAspectAnalyzer((TileAspectAnalyzer) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ICHOR_POUCH:
				return new ContainerIchorPouch(player);

			case LibGuiIDs.GUI_ID_WARP_GATE:
				return new ContainerWarpGate((TileWarpGate) tile, player.inventory);
            case LibGuiIDs.GUI_ID_REMOTE_PLACER:
                return new ContainerRemotePlacer((TileRPlacer)tile,player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		switch (ID) {
			case LibGuiIDs.GUI_ID_TABLET:
				return new GuiAnimationTablet((TileAnimationTablet) tile, player.inventory);

			case LibGuiIDs.GUI_ID_MOB_MAGNET:
				return new GuiMobMagnet((TileMobMagnet) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ENCHANTER:
				return new GuiEnchanting((TileEnchanter) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ASPECT_ANALYZER:
				return new GuiAspectAnalyzer((TileAspectAnalyzer) tile, player.inventory);

			case LibGuiIDs.GUI_ID_ICHOR_POUCH:
				return new GuiIchorPouch(new ContainerIchorPouch(player));

			case LibGuiIDs.GUI_ID_WARP_GATE:
				return new GuiWarpGate((TileWarpGate) tile, player.inventory);

			case LibGuiIDs.GUI_ID_WARP_GATE_DESTINATIONS:
				return new GuiWarpGateDestinations((TileWarpGate) tile);
            case LibGuiIDs.GUI_ID_REMOTE_PLACER:
                return new GuiRemotePlacer((TileRPlacer)tile,player.inventory);
		}
		return null;
	}

}
