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
 * File Created @ [9 Sep 2013, 16:31:14 (GMT)]
 */
package vazkii.tinkerer.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import vazkii.tinkerer.client.gui.button.GuiButtonAT;
import vazkii.tinkerer.client.gui.button.GuiButtonATRadio;
import vazkii.tinkerer.client.gui.button.IRadioButton;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.tile.container.ContainerAnimationTablet;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;
import vazkii.tinkerer.common.network.packet.PacketTabletButton;

import java.util.ArrayList;
import java.util.List;

public class GuiAnimationTablet extends GuiContainer {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ANIMATION_TABLET);

	int x, y;

	TileAnimationTablet tablet;
	List<GuiButtonAT> buttonListAT = new ArrayList();
	List<IRadioButton> radioButtons = new ArrayList();

	public GuiAnimationTablet(TileAnimationTablet tablet, InventoryPlayer playerInv) {
		super(new ContainerAnimationTablet(tablet, playerInv));
		this.tablet = tablet;
	}

	@Override
	public void initGui() {
		super.initGui();
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
		buttonListAT.clear();
		addButton(new GuiButtonAT(0, x + xSize / 2 - 7, y + 60, tablet.redstone));
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
		else buttonListAT.get(0).buttonEnabled = !buttonListAT.get(0).buttonEnabled;

		tablet.leftClick = buttonListAT.get(1).buttonEnabled;
		tablet.redstone = buttonListAT.get(0).buttonEnabled;

        ThaumicTinkerer.netHandler.sendToServer(new PacketTabletButton(tablet));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(gui);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        String left = StatCollector.translateToLocal("ttmisc.leftClick");
        String right = StatCollector.translateToLocal("ttmisc.rightClick");
        String redstone = StatCollector.translateToLocal("ttmisc.redstoneControl");

        fontRendererObj.drawString(left, x + 48 - fontRendererObj.getStringWidth(left), y + 18, 0x999999);
        fontRendererObj.drawString(right, x + 128, y + 18, 0x999999);
        fontRendererObj.drawString(redstone, x + xSize / 2 - fontRendererObj.getStringWidth(redstone) / 2, y + 50, 0x999999);
        GL11.glColor3f(1F, 1F, 1F);
	}
}