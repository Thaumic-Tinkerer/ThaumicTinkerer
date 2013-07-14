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

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import thaumcraft.common.Config;
import vazkii.tinkerer.client.gui.button.GuiButtonMM;
import vazkii.tinkerer.client.gui.button.GuiButtonMMRadio;
import vazkii.tinkerer.client.gui.button.IRadioButton;
import vazkii.tinkerer.inventory.container.ContainerMobMagnet;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.network.PacketManager;
import vazkii.tinkerer.network.packet.PacketMobMagnetButton;
import vazkii.tinkerer.tile.TileEntityMobMagnet;
import vazkii.tinkerer.util.helper.ItemNBTHelper;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GuiMobMagnet extends GuiContainer {

	int x, y;
	ItemStack stack = null;
	TileEntityMobMagnet mobMagnet;
	private static final String TAG_PATTERN = "pattern";
	private static final String NON_ASSIGNED = "Blank";
	private String filter = "All";

	List<GuiButtonMM> buttonListMM = new ArrayList();
	List<IRadioButton> radioButtons = new ArrayList();

	public GuiMobMagnet(TileEntityMobMagnet tile, InventoryPlayer playerInv) {
		super(new ContainerMobMagnet(tile, playerInv));
		this.mobMagnet = tile;
	}

	@Override
	public void initGui() {
		super.initGui();
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
		buttonListMM.clear();
		addButton(new GuiButtonMMRadio(0, x + 100, y + 28, mobMagnet.adult, radioButtons));
		addButton(new GuiButtonMMRadio(1, x + 100, y + 48, !mobMagnet.adult, radioButtons));
		buttonList = buttonListMM;
	}

	private void addButton(GuiButtonMM button) {
		buttonListMM.add(button);
		if(button instanceof IRadioButton)
			radioButtons.add((IRadioButton) button);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton instanceof IRadioButton)
			((IRadioButton) par1GuiButton).enableFromClick();
		else buttonListMM.get(0).enabled = !buttonListMM.get(0).enabled;

		mobMagnet.adult = buttonListMM.get(0).enabled;

		PacketManager.sendPacketToServer(new PacketMobMagnetButton(mobMagnet));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(LibResources.GUI_MOB_MAGNET);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		filter = "All";
		String adult = "Adult";
		String child = "Child";
		stack = mobMagnet.getStackInSlot(0);
		if(stack != null) {
			filter = ItemNBTHelper.getString(stack, TAG_PATTERN, NON_ASSIGNED);
		}
		fontRenderer.drawString(filter, (x + (xSize/2) - (fontRenderer.getStringWidth(filter)/2))-26, y + 16, 0x999999);
		fontRenderer.drawString(adult, x + 120, y + 30, 0x999999);
		fontRenderer.drawString(child, x + 120, y + 50, 0x999999);
		GL11.glColor3f(1F, 1F, 1F);
	}

}
