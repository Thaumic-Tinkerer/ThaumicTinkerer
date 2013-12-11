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
 * File Created @ [Dec 11, 2013, 10:46:14 PM (GMT)]
 */
package vazkii.tinkerer.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import vazkii.tinkerer.client.gui.button.GuiButtonMMRadio;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.tile.TileAspectAnalyzer;
import vazkii.tinkerer.common.block.tile.container.ContainerAspectAnalyzer;

public class GuiAspectAnalyzer extends GuiContainer {
	
	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ASPECT_ANALYZER);

	int x, y;
	TileAspectAnalyzer analyzer;
	
	public GuiAspectAnalyzer(TileAspectAnalyzer analyzer, InventoryPlayer inv) {
		super(new ContainerAspectAnalyzer(analyzer, inv));
		this.analyzer = analyzer;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(gui);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
