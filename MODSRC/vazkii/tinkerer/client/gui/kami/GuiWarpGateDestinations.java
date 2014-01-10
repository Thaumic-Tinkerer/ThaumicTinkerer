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
 * File Created @ [Jan 10, 2014, 6:08:23 PM (GMT)]
 */
package vazkii.tinkerer.client.gui.kami;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import thaumcraft.client.codechicken.core.vec.Vector3;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.core.helper.MiscHelper;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.item.kami.ItemSkyPearl;
import vazkii.tinkerer.common.network.PacketManager;
import vazkii.tinkerer.common.network.packet.kami.PacketWarpGateTeleport;

public class GuiWarpGateDestinations extends GuiScreen {

	private static ResourceLocation enderField = new ResourceLocation("textures/entity/end_portal.png");
	TileWarpGate warpGate;
	RenderItem render = new RenderItem(); 

	int lastMouseX, lastMouseY;
	int x, y;
	int ticks;

	public GuiWarpGateDestinations(TileWarpGate warpGate) {
		this.warpGate = warpGate;
	}

	@Override
	public void initGui() {
		super.initGui();

		x = warpGate.xCoord - width / 2;
		y = warpGate.zCoord - height / 2;
	}

	@Override
	public void updateScreen() {
		++ticks;
		
		ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int i = res.getScaledWidth();
		int j = res.getScaledHeight();
		int mx = Mouse.getX() * i / this.mc.displayWidth;
		int my = j - Mouse.getY() * j / this.mc.displayHeight - 1;

		if(Mouse.isButtonDown(0)) {
			int deltaX = mx - lastMouseX;
			int deltaY = my - lastMouseY;
			x -= deltaX;
			y -= deltaY;
		}

		lastMouseX = mx;
		lastMouseY = my;
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);

		if(par2 == 57) { // space
			x = warpGate.xCoord - width / 2;
			y = warpGate.zCoord - height / 2;
			return;
		}
	}
	
	List<String> tooltip = new ArrayList();

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);

		tooltip.clear();
		
		int gateX = warpGate.xCoord - x;
		int gateY = warpGate.zCoord - y;
		mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);

		List<Object[]> coords = new ArrayList();
		
		for(int i = 0; i < warpGate.getSizeInventory(); i++) {
			ItemStack stack = warpGate.getStackInSlot(i);
			if(stack != null && ItemSkyPearl.isAttuned(stack)) {
				int dim = ItemSkyPearl.getDim(stack);
				if(warpGate.worldObj.provider.dimensionId != dim)
					continue;

				int x = ItemSkyPearl.getX(stack);
				int y = ItemSkyPearl.getY(stack);
				int z = ItemSkyPearl.getZ(stack);

				if(y != -1)
					coords.add(new Object[] { x - this.x, z - this.y, stack, i});
			}
		}
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1F, 1F, 1F, (float) ((Math.sin((double) ticks / 10D) + 1F) / 4F + 0.25F));
		GL11.glLineWidth(2F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		for(Object[] coords_ : coords) {
			int x = (Integer) coords_[0];
			int y = (Integer) coords_[1];
			
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2i(gateX, gateY);
			GL11.glVertex2i(x, y);
			GL11.glEnd();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		drawPearlAt(0, null, (Integer) gateX, (Integer) gateY, par1, par2);
		for(Object[] coords_ : coords)
			drawPearlAt((Integer) coords_[3], (ItemStack) coords_[2], (Integer) coords_[0], (Integer) coords_[1], par1, par2);
		
		if(!tooltip.isEmpty())
			ClientHelper.renderTooltip(par1, par2, tooltip);
		
		drawCenteredString(fontRenderer, StatCollector.translateToLocal("ttmisc.spaceToReset"), width / 2, 5, 0xFFFFFF);
	}

	public void drawPearlAt(int index, ItemStack stack, int xp, int yp, int mx, int my) {
		int x = xp + this.x;
		int y = yp + this.y;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(xp, yp, 0);
		GL11.glScalef(0.5F, 0.5F, 1F);
		render.renderIcon(-8, -8, ModItems.skyPearl.getIconFromDamage(0), 16, 16);
		GL11.glPopMatrix();
		
		if(mx >= xp - 4 && mx <= xp + 4 && my >= yp - 4 && my < yp + 4) {
			if(stack != null && stack.hasDisplayName())
				tooltip.add(EnumChatFormatting.AQUA + stack.getDisplayName());
			else tooltip.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal(stack == null ? "ttmisc.entrancePoint" : "ttmisc.destination"));
			
			if(stack != null) {
				ItemSkyPearl.addInfo(stack, warpGate.worldObj.provider.dimensionId, Vector3.fromTileEntity(warpGate), tooltip, true);
				tooltip.add(StatCollector.translateToLocal("ttmisc.clickToTeleport"));
			} else {
				tooltip.add("X: " + x);
				tooltip.add("Z: " + y);
			}
			
			if(Mouse.isButtonDown(0) && isShiftKeyDown() && stack != null) {
				PacketDispatcher.sendPacketToServer(PacketManager.buildPacket(new PacketWarpGateTeleport(warpGate, index)));
				mc.displayGuiScreen(null);
			}
		}
	}

	@Override
	public void drawDefaultBackground() {
		int par1 = 0;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator = Tessellator.instance;
		this.mc.getTextureManager().bindTexture(enderField);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f = 256.0F;
		tessellator.startDrawingQuads();
		//        tessellator.setColorOpaque_I(4210752);
		tessellator.addVertexWithUV(0.0D, (double)this.height, 0.0D, 0.0D, (double)((float)this.height / f + (float)par1));
		tessellator.addVertexWithUV((double)this.width, (double)this.height, 0.0D, (double)((float)this.width / f), (double)((float)this.height / f + (float)par1));
		tessellator.addVertexWithUV((double)this.width, 0.0D, 0.0D, (double)((float)this.width / f), (double)par1);
		tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, (double)par1);
		tessellator.draw();
	}

}
