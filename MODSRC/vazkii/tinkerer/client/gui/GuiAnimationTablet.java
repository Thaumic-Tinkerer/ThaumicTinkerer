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
 * File Created @ [13 May 2013, 19:59:12 (GMT)]
 */
package vazkii.tinkerer.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.gui.button.GuiButtonAT;
import vazkii.tinkerer.client.gui.button.GuiButtonATRadio;
import vazkii.tinkerer.client.gui.button.IRadioButton;
import vazkii.tinkerer.inventory.container.ContainerAnimationTablet;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityAnimationTablet;

public class GuiAnimationTablet extends GuiContainer {

	int x, y;

	TileEntityAnimationTablet tablet;
	List<GuiButtonAT> buttonListAT = new ArrayList();
	List<IRadioButton> radioButtons = new ArrayList();

	public GuiAnimationTablet(TileEntityAnimationTablet tablet, InventoryPlayer playerInv) {
		super(new ContainerAnimationTablet(tablet, playerInv));
		this.tablet = tablet;
	}

	@Override
	public void initGui() {
		super.initGui();
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
		buttonListAT.clear();
		addButton(new GuiButtonAT(0, x + (xSize / 2) - 7, y + 60, tablet.redstone));
		addButton(new GuiButtonATRadio(1, x + 52, y + 15, tablet.leftClick, radioButtons));
		addButton(new GuiButtonATRadio(2, x + 111, y + 15, !tablet.leftClick, radioButtons));
		buttonList = buttonListAT;
	}
	
	private void addButton(GuiButtonAT button) {
		buttonListAT.add(button);
		if(button instanceof IRadioButton)
			radioButtons.add((IRadioButton) button);
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton instanceof IRadioButton)
			((IRadioButton) par1GuiButton).enableFromClick();
		else buttonListAT.get(0).enabled = !buttonListAT.get(0).enabled;
		
		tablet.leftClick = buttonListAT.get(1).enabled;
		tablet.redstone = buttonListAT.get(0).enabled;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(LibResources.GUI_ANIMATION_TABLET);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        String left = "Left";
        String right = "Right";
        String redstone = "Redstone";
        fontRenderer.drawString(left, x + 48 - fontRenderer.getStringWidth(left), y + 18, 0x999999);
        fontRenderer.drawString(right, x + 128, y + 18, 0x999999);
        fontRenderer.drawString(redstone, (x + xSize / 2) - fontRenderer.getStringWidth(redstone) / 2, y + 50, 0x999999);
        GL11.glColor3f(1F, 1F, 1F);
	}
}
