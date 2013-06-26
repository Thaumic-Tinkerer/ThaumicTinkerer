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
 * File Created @ [28 Apr 2013, 17:54:47 (GMT)]
 */
package vazkii.tinkerer.util.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.client.gui.GuiAnimationTablet;
import vazkii.tinkerer.client.gui.GuiTransmutator;
import vazkii.tinkerer.inventory.container.ContainerAnimationTablet;
import vazkii.tinkerer.inventory.container.ContainerTransmutator;
import vazkii.tinkerer.lib.LibGuiIDs;
import vazkii.tinkerer.tile.TileEntityAnimationTablet;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import cpw.mods.fml.common.network.IGuiHandler;

public final class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		switch(ID) {
			case LibGuiIDs.ID_TRANSMUTATOR :
				return new ContainerTransmutator((TileEntityTransmutator) tile, player.inventory);

			case LibGuiIDs.ID_ANIMATION_TABLET :
				return new ContainerAnimationTablet((TileEntityAnimationTablet) tile, player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		switch(ID) {
			case LibGuiIDs.ID_TRANSMUTATOR :
				return new GuiTransmutator((TileEntityTransmutator) tile, player.inventory);

			case LibGuiIDs.ID_ANIMATION_TABLET :
				return new GuiAnimationTablet((TileEntityAnimationTablet) tile, player.inventory);
		}
		return null;
	}

}
