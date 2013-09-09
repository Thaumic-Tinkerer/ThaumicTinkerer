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
package vazkii.tinkerer.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.tinkerer.client.gui.GuiAnimationTablet;
import vazkii.tinkerer.common.block.tile.container.ContainerAnimationTablet;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.lib.LibGuiIDs;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler  implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		switch(ID) {
			case LibGuiIDs.GUI_ID_TABLET :
				return new ContainerAnimationTablet((TileAnimationTablet) tile, player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		switch(ID) {
			case LibGuiIDs.GUI_ID_TABLET :
				return new GuiAnimationTablet((TileAnimationTablet) tile, player.inventory);
		}
		return null;
	}

}
