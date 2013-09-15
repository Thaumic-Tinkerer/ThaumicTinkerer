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
 * File Created @ [15 Sep 2013, 00:58:22 (GMT)]
 */
package vazkii.tinkerer.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumcraft.common.config.Config;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.gui.button.GuiButtonEnchant;
import vazkii.tinkerer.client.gui.button.GuiButtonEnchantment;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.tile.TileEnchanter;
import vazkii.tinkerer.common.block.tile.container.ContainerEnchanter;

public class GuiEnchanting extends GuiContainer {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ENCHANTER);
	
	int x, y;
	
	TileEnchanter enchanter;
	
	GuiButtonEnchantment[] enchantButtons = new GuiButtonEnchantment[8];
	
	public List<String> tooltip = new ArrayList();
 	
	public GuiEnchanting(TileEnchanter enchanter, InventoryPlayer inv) {
		super(new ContainerEnchanter(enchanter, inv));
		this.enchanter = enchanter;
	}

	@Override
	public void initGui() {
		super.initGui();
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
		
		buildButtonList();
	}
	
	public void buildButtonList() {
		buttonList.clear();
		buttonList.add(new GuiButtonEnchant(enchanter, 0, x + 151, y + 33));
		for(int i = 0; i < enchantButtons.length; i++) {
			GuiButtonEnchantment button = new GuiButtonEnchantment(this, 1 + i, x + 34 + i * 16, y + 54);
			enchantButtons[i] = button;
			buttonList.add(button);
		}
		
		asignEnchantButtons();
	}
	
	public void asignEnchantButtons() {
		// TODO Proper method
		enchantButtons[0].enchant = Enchantment.efficiency;
		enchantButtons[1].enchant = Enchantment.silkTouch;
		enchantButtons[2].enchant = Enchantment.unbreaking;
		enchantButtons[3].enchant = Enchantment.fortune;
		enchantButtons[4].enchant = Config.enchRepair;
		enchantButtons[5].enchant = null;
		enchantButtons[6].enchant = null;
		enchantButtons[7].enchant = null;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.func_110577_a(gui);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		ItemStack itemToEnchant = enchanter.getStackInSlot(0);
		if(itemToEnchant != null && !itemToEnchant.isItemEnchanted())
			drawTexturedModalRect(x + 30, y + 50, 0, ySize, 147, 24);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if(!tooltip.isEmpty())
			ClientHelper.renderTooltip(par1 - x, par2 - y, tooltip);
		tooltip.clear();
	}

}
