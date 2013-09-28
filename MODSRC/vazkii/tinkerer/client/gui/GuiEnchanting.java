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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.aspects.Aspect;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.gui.button.GuiButtonEnchant;
import vazkii.tinkerer.client.gui.button.GuiButtonEnchanterLevel;
import vazkii.tinkerer.client.gui.button.GuiButtonEnchantment;
import vazkii.tinkerer.client.gui.button.GuiButtonFramedEnchantment;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.tile.TileEnchanter;
import vazkii.tinkerer.common.block.tile.container.ContainerEnchanter;
import vazkii.tinkerer.common.enchantment.core.EnchantmentManager;
import vazkii.tinkerer.common.lib.LibFeatures;
import vazkii.tinkerer.common.network.PacketManager;
import vazkii.tinkerer.common.network.packet.PacketEnchanterAddEnchant;
import vazkii.tinkerer.common.network.packet.PacketEnchanterStartWorking;
import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiEnchanting extends GuiContainer {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ENCHANTER);

	int x, y;

	public TileEnchanter enchanter;

	GuiButtonEnchantment[] enchantButtons = new GuiButtonEnchantment[8];

	public List<String> tooltip = new ArrayList();

	List<Integer> lastTickEnchants;
	List<Integer> lastTickLevels;
	ItemStack lastTickStack;
	ItemStack currentStack;

	public GuiEnchanting(TileEnchanter enchanter, InventoryPlayer inv) {
		super(new ContainerEnchanter(enchanter, inv));
		this.enchanter = enchanter;
		lastTickStack = enchanter.getStackInSlot(0);
		currentStack = enchanter.getStackInSlot(0);
		lastTickEnchants = new ArrayList(enchanter.enchantments);
		lastTickLevels = new ArrayList(enchanter.levels);
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
		GuiButton enchantButton = new GuiButtonEnchant(this, enchanter, 0, x + 151, y + 33);
		buttonList.add(enchantButton);
		enchantButton.enabled = !enchanter.enchantments.isEmpty();

		for(int i = 0; i < enchantButtons.length; i++) {
			GuiButtonEnchantment button = new GuiButtonEnchantment(this, 1 + i, x + 34 + i * 16, y + 54);
			enchantButtons[i] = button;
			buttonList.add(button);
		}

		asignEnchantButtons();

		int i = 0;
		for(Integer enchant : enchanter.enchantments) {
			GuiButtonEnchantment button = new GuiButtonFramedEnchantment(this, 9 + i * 3, x + xSize + 4, y + i * 26);
			button.enchant = Enchantment.enchantmentsList[enchant];
			buttonList.add(button);
			buttonList.add(new GuiButtonEnchanterLevel(9 + i * 3 + 1, x + xSize + 24, y + i * 26 - 4, false));
			buttonList.add(new GuiButtonEnchanterLevel(9 + i * 3 + 2, x + xSize + 31, y + i * 26 - 4, true));
			++i;
		}
	}

	public void asignEnchantButtons() {
		for(int i = 0; i < 8; i++) {
			enchantButtons[i].enchant = null;
			enchantButtons[i].enabled = false;
		}

		if(currentStack == null || currentStack.isItemEnchanted())
			return;

		int it = 0;
		for(int enchant : EnchantmentManager.enchantmentData.keySet())
			if(EnchantmentManager.canApply(currentStack, Enchantment.enchantmentsList[enchant], enchanter.enchantments)) {
				enchantButtons[it].enchant = Enchantment.enchantmentsList[enchant];
				enchantButtons[it].enabled = true;

				it++;
				if(it >= 8)
					break;
			}
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if(par1GuiButton.id == 0) {
			PacketDispatcher.sendPacketToServer(PacketManager.buildPacket(new PacketEnchanterStartWorking(enchanter)));
		} else if(par1GuiButton.id <= 8) {
			GuiButtonEnchantment button = enchantButtons[par1GuiButton.id - 1];
			if(button != null && button.enchant != null)
				PacketDispatcher.sendPacketToServer(PacketManager.buildPacket(new PacketEnchanterAddEnchant(enchanter, button.enchant.effectId, 0)));
		} else {
			if(enchanter.enchantments.isEmpty() || enchanter.levels.isEmpty())
				return;

			int type = (par1GuiButton.id - 9) % 3;
			int index = (par1GuiButton.id - 9) / 3;
			int level = enchanter.levels.get(index);

			Enchantment enchant = Enchantment.enchantmentsList[enchanter.enchantments.get(index)];

			switch(type) {
			case 0 : {
				PacketDispatcher.sendPacketToServer(PacketManager.buildPacket(new PacketEnchanterAddEnchant(enchanter, enchant.effectId, -1)));
				break;
			}
			case 1 : {
				PacketDispatcher.sendPacketToServer(PacketManager.buildPacket(new PacketEnchanterAddEnchant(enchanter, enchant.effectId, level == 1 ? -1 : level - 1)));
				break;
			}
			case 2 : {
				PacketDispatcher.sendPacketToServer(PacketManager.buildPacket(new PacketEnchanterAddEnchant(enchanter, enchant.effectId, level + 1)));
				break;
			}
		}
		}

		buildButtonList();
	}

	@Override
	public void updateScreen() {
		currentStack = enchanter.getStackInSlot(0);

		if(currentStack != lastTickStack || !lastTickEnchants.equals(enchanter.enchantments) || !lastTickLevels.equals(enchanter.levels))
			buildButtonList();

		lastTickStack = currentStack;
		lastTickEnchants = new ArrayList(enchanter.enchantments);
		lastTickLevels = new ArrayList(enchanter.enchantments);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		mc.renderEngine.func_110577_a(gui);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		ItemStack itemToEnchant = enchanter.getStackInSlot(0);
		if(itemToEnchant != null && !itemToEnchant.isItemEnchanted())
			drawTexturedModalRect(x + 30, y + 50, 0, ySize, 147, 24);

		if(!enchanter.enchantments.isEmpty()) {
			int x = this.x + 40;
			GL11.glEnable(GL11.GL_BLEND);
			int xo = 15;
			for(Aspect aspect : LibFeatures.PRIMAL_ASPECTS) {
				drawAspectBar(aspect, x + xo, y + 50, i, j);
				xo += 15;
			}
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		if(!tooltip.isEmpty())
			ClientHelper.renderTooltip(par1 - x, par2 - y, tooltip);
		tooltip.clear();
	}

	private void drawAspectBar(Aspect aspect, int x, int y, int mx, int my) {
		int totalCost = enchanter.totalAspects.getAmount(aspect);
		int current = enchanter.currentAspects.getAmount(aspect);

		int size = totalCost == 0 ? 11 : 59;

		if(totalCost == 0) {
			drawTexturedModalRect(x, y - size, 200, 0, 10, 4);
			drawTexturedModalRect(x, y - size + 4, 200, 52, 10, 10);
		} else {
			int pixels = (int) (48D * ((double) current / (double) totalCost));
			Color color = new Color(aspect.getColor());
			GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
			drawTexturedModalRect(x + 1, y - size + 4 + 48 - pixels, 210, 48 - pixels, 8, pixels);
			GL11.glColor3f(1F, 1F, 1F);
			drawTexturedModalRect(x, y - size, 200, 0, 10, size);
		}

		if(mx > x && mx <= x + 10 && my > y - size && my <= y) {
			List<String> tooltip = new ArrayList();
			tooltip.add('\u00a7' + aspect.getChatcolor() + aspect.getName());
			tooltip.add(current + "/" + totalCost);
			this.tooltip = tooltip;
		}
	}

}
