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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.inventory.container.ContainerTransmutator;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.util.helper.MiscHelper;

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
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		Minecraft mc = MiscHelper.getMc();
		mc.renderEngine.bindTexture(LibResources.MISC_GLYPHS);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(x + 64, y, 0F);
		float deg = ClientTickHandler.clientTicksElapsed / 1F % 360F;
		GL11.glRotatef(deg, 0F, 0F, 1F);
		//GL11.glColor4f(1F, 1F, 1F, 1F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(-64, 64, zLevel, 0, 1);
		tess.addVertexWithUV(64, 64, zLevel, 1, 1);
		tess.addVertexWithUV(64, -64, zLevel, 1, 0);
		tess.addVertexWithUV(-64, -64, zLevel, 0, 0);
		tess.draw();
		GL11.glPopMatrix();
	}

}
