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

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.common.block.tile.kami.TileWarpGate;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.item.kami.ItemSkyPearl;

public class GuiWarpGateDestinations extends GuiScreen {

	private static ResourceLocation enderField = new ResourceLocation("textures/entity/end_portal.png");
	TileWarpGate warpGate;
	RenderItem render = new RenderItem(); 

	int x, y;

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
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);

		switch(par2) {
			case 57 : { // space
				x = warpGate.xCoord - width / 2;
				y = warpGate.zCoord - height / 2;
				return;
			}
			case 200 : { // up
				y -= 10;
				return;
			}
			case 208 : { // down
				y += 10;
				return;
			}			
			case 203 : { // left
				x += 10;
				return;
			}			
			case 205 : { // right
				x -= 10;
				return;
			}
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);

		int gateX = warpGate.xCoord;
		int gateY = warpGate.zCoord;
		mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
		drawPearlAt(gateX - x, gateY - y, par1, par2);
		
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
					drawPearlAt(x - this.x, z - this.y, par1, par2);
			}
		}
	}

	public void drawPearlAt(int x, int y, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(0.5F, 0.5F, 1F);
		render.renderIcon(-8, -8, ModItems.skyPearl.getIconFromDamage(0), 16, 16);
		GL11.glPopMatrix();
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
