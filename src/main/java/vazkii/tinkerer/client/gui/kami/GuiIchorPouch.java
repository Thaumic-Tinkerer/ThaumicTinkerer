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
 * File Created @ [Dec 29, 2013, 10:54:58 PM (GMT)]
 */
package vazkii.tinkerer.client.gui.kami;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.lib.LibResources;

public class GuiIchorPouch extends GuiContainer {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ICHOR_POUCH);

	int x, y;

	public GuiIchorPouch(Container par1Container) {
		super(par1Container);
	}

	@Override
	public void initGui() {
		super.initGui();
		xSize = ySize = 256;

		guiLeft = x = (width - xSize) / 2;
		guiTop = y = (height - ySize) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		mc.renderEngine.bindTexture(gui);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		GL11.glDisable(GL11.GL_BLEND);
	}

}
