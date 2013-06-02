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
 * File Created @ [2 Jun 2013, 16:09:33 (GMT)]
 */
package vazkii.tinkerer.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.inventory.container.ContainerFluxCollector;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityFluxCollector;

public class GuiFluxCollector extends GuiContainer {

	int x, y;

	TileEntityFluxCollector collector;

	public GuiFluxCollector(TileEntityFluxCollector tile, InventoryPlayer playerInv) {
		super(new ContainerFluxCollector(tile, playerInv));
		collector = tile;
	}

	@Override
	public void initGui() {
		super.initGui();
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(LibResources.GUI_FLUX_COLLECTOR);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        GL11.glColor3f(1F, 1F, 1F);
	}
}