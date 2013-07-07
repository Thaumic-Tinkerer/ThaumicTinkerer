/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 * 
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [6 Jul 2013, 15:16:08 (GMT)]
 */
package vazkii.tinkerer.client.gui;

import org.lwjgl.opengl.GL11;

import thaumcraft.common.Config;
import vazkii.tinkerer.inventory.container.ContainerMobMagnet;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityMobMagnet;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiMobMagnet extends GuiContainer {

    	int x, y;
    	ItemStack stack = null;
	TileEntityMobMagnet mobMagnet;
	
	public GuiMobMagnet(TileEntityMobMagnet tile, InventoryPlayer player) {
		super(new ContainerMobMagnet(tile, player));
		mobMagnet = tile;
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
	        mc.renderEngine.bindTexture(LibResources.GUI_MOB_MAGNET);
	        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	        String filter = "None";
	        stack = mobMagnet.getStackInSlot(0);
	        if(stack != null) {
	            	int counter = 0;
	        	for(int filterItem : LibMisc.filterItems) {
	        	    if(stack.itemID == filterItem) {
	        		filter = LibMisc.filterItemNames[counter];
	        	    }
	        	    else {
	        		counter++;
	        	    }
	        	}
	        	
	        	counter = 0;
	        	for(int filterBlock : LibMisc.filterBlocks) {
	        	    if(stack.itemID == filterBlock) {
	        		filter = LibMisc.filterBlockNames[counter];
	        	    }
	        	    else {
	        		counter++;
	        	    }
	        	}
	        }
	        fontRenderer.drawString(filter, x + (xSize/2) - (fontRenderer.getStringWidth(filter)/2), y + 16, 0x999999);
	        GL11.glColor3f(1F, 1F, 1F);
	}

}
