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
 * File Created @ [28 Apr 2013, 18:57:15 (GMT)]
 */
package vazkii.tinkerer.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import vazkii.tinkerer.inventory.container.ContainerTransmutator;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityTransmutator;

public class GuiTransmutator extends GuiContainer {

	TileEntityTransmutator transmutator;
	
	public GuiTransmutator(TileEntityTransmutator tile, InventoryPlayer player) {
		super(new ContainerTransmutator(tile, player));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(LibResources.GUI_TRANSMUTATOR);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
