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
 * File Created @ [14 Sep 2013, 18:05:48 (GMT)]
 */
package vazkii.tinkerer.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.tile.TileEnchanter;
import vazkii.tinkerer.common.block.tile.container.ContainerEnchanter;

public class GuiEnchanter extends GuiContainer {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ENCHANTER);
	
	int x, y;
	
	TileEnchanter enchanter;
	
	public GuiEnchanter(TileEnchanter enchanter, InventoryPlayer inv) {
		super(new ContainerEnchanter(enchanter, inv));
		this.enchanter = enchanter;
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
		mc.renderEngine.func_110577_a(gui);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		ItemStack itemToEnchant = enchanter.getStackInSlot(0);
		if(itemToEnchant != null && !itemToEnchant.isItemEnchanted())
			drawTexturedModalRect(x + 20, y + 45, 0, ySize, 142, 25);
	}

}
