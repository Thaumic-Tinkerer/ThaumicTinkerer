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

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import vazkii.tinkerer.inventory.container.ContainerTransmutator;
import vazkii.tinkerer.inventory.slot.SlotTransmutator;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.util.helper.MiscHelper;

public class GuiTransmutator extends GuiContainer {

	int x, y;

	TileEntityTransmutator transmutator;

	public GuiTransmutator(TileEntityTransmutator tile, InventoryPlayer player) {
		super(new ContainerTransmutator(tile, player));
		transmutator = tile;
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
        mc.renderEngine.bindTexture(LibResources.GUI_TRANSMUTATOR);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		float deg = (float) (transmutator.ticksExisted % 360F);
		renderGlyphsAt(x + 154, y + 19, deg);
		if(transmutator.getStackInSlot(0) != null) {
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			ObjectTags tags = ThaumcraftCraftingManager.getObjectTags(transmutator.getStackInSlot(0));
			int value = SlotTransmutator.getTotalAspectValue(tags);
			String price = value + " vis";
			fontRenderer.drawString(price, (x + 162 - fontRenderer.getStringWidth(price) / 2) * 2, (y + 69) * 2, 0xFFFFFF);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
        List<String> renderString = null;

		if(transmutator.getStackInSlot(0) != null) {
			ObjectTags tags = ThaumcraftCraftingManager.getObjectTags(transmutator.getStackInSlot(0));
			int degPerTag = 360 / tags.size();
			int renderDeg = -90;
			for(EnumTag tag : tags.getAspects()) {
				int amount = tags.getAmount(tag);
				int xpos = (int) (50 + 35 * Math.cos(renderDeg * Math.PI / 180));
				int ypos = (int) (16 + 35 * Math.sin(renderDeg * Math.PI / 180));
				UtilsFX.drawTag(mc, xpos, ypos, tag, amount * 4, this, false, false);
				float deg = (float) (transmutator.ticksExisted * amount % 360F);

				int color = tag.color;
				int red = color >> 16 & 0xFF;
				int green = color >> 8 & 0xFF;
				int blue = color & 0xFF;
				GL11.glColor4ub((byte) red, (byte) green, (byte) blue, (byte) 128);
				if(transmutator.foundTags.getAmount(tag) >= amount * 4)
					renderGlyphsAt(xpos + 8, ypos + 8, deg);

				if(i > xpos + x && i < xpos + x + 16 && j > ypos + y && j < ypos + y + 16)
					renderString = Arrays.asList(tag.name, tag.meaning);
				renderDeg += degPerTag;
			}

			if(renderString != null)
				UtilsFX.drawCustomTooltip(this, itemRenderer, fontRenderer, renderString, i - x, j - y, EnumChatFormatting.AQUA.ordinal());
		}
	}

	private void renderGlyphsAt(int x, int y, float deg) {
		Minecraft mc = MiscHelper.getMc();
		mc.renderEngine.bindTexture(LibResources.MISC_GLYPHS);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(x, y, 0F);
		GL11.glRotatef(deg, 0F, 0F, 1F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(-16, 16, zLevel, 0, 1);
		tess.addVertexWithUV(16, 16, zLevel, 1, 1);
		tess.addVertexWithUV(16, -16, zLevel, 1, 0);
		tess.addVertexWithUV(-16, -16, zLevel, 0, 0);
		tess.draw();
		GL11.glPopMatrix();
	}
}
