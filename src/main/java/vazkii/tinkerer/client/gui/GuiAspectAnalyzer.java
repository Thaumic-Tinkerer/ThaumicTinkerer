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
 * File Created @ [Dec 11, 2013, 10:46:14 PM (GMT)]
 */
package vazkii.tinkerer.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.research.ScanManager;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.tile.TileAspectAnalyzer;
import vazkii.tinkerer.common.block.tile.container.ContainerAspectAnalyzer;

import java.util.Arrays;
import java.util.List;

public class GuiAspectAnalyzer extends GuiContainer {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ASPECT_ANALYZER);

	int x, y;
	TileAspectAnalyzer analyzer;
	Aspect aspectHovered = null;

	public GuiAspectAnalyzer(TileAspectAnalyzer analyzer, InventoryPlayer inv) {
		super(new ContainerAspectAnalyzer(analyzer, inv));
		this.analyzer = analyzer;
	}

	@Override
	public void initGui() {
		super.initGui();
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mx, int my) {
		aspectHovered = null;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(gui);
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		ItemStack stack = analyzer.getStackInSlot(0);
		if(stack != null) {
			int h = ScanManager.generateItemHash(stack.getItem(), stack.getItemDamage());

			List<String> list = Thaumcraft.proxy.getScannedObjects().get(ClientHelper.clientPlayer().getGameProfile().getName());
			if ( list != null && (list.contains("@" + h) || list.contains("#" + h))) {
				AspectList tags = ThaumcraftCraftingManager.getObjectTags(stack);
				tags = ThaumcraftCraftingManager.getBonusTags(stack, tags);
				if (tags != null) {
					int i = 0;
					for(Aspect aspect : tags.getAspectsSortedAmount()) {
						int x = this.x + 20 + i * 18;
						int y = this.y + 58;
						UtilsFX.drawTag(x, y, aspect, tags.getAmount(aspect), 0, zLevel);

						if(mx > x && mx < x + 16 && my > y && my < y + 16)
							aspectHovered = aspect;

						i++;
					}
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mx, int my) {
		if(aspectHovered != null)
			ClientHelper.renderTooltip(mx - x, my - y, Arrays.asList(EnumChatFormatting.AQUA + aspectHovered.getName(), EnumChatFormatting.GRAY + aspectHovered.getLocalizedDescription()));

		super.drawGuiContainerForegroundLayer(mx, my);
	}

}
