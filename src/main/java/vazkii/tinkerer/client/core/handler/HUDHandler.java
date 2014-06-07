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
 * File Created @ [10 Sep 2013, 12:16:36 (GMT)]
 */
package vazkii.tinkerer.client.core.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import thaumcraft.api.wands.IWandFocus;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.wands.ItemWandCasting;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.common.item.foci.ItemFocusDislocation;

public final class HUDHandler {

	RenderItem renderItem = new RenderItem();

	@SubscribeEvent
	public void drawDislocationFocusHUD(RenderGameOverlayEvent.Post event) {
		if (event.type == ElementType.HOTBAR && ClientHelper.minecraft().currentScreen == null) {
			boolean up = !Config.dialBottom;
			int xpos = 4;
			int ypos = up ? 50 : event.resolution.getScaledHeight() - 70;

			ItemStack item = ClientHelper.clientPlayer().getCurrentEquippedItem();
			if (item != null && item.getItem() instanceof ItemWandCasting) {
				ItemWandCasting wand = (ItemWandCasting) item.getItem();
				wand.getFocusItem(item);
				IWandFocus focus = wand.getFocus(item);

				if (focus != null && focus instanceof ItemFocusDislocation) {
					ItemStack pickedBlock = ((ItemFocusDislocation) focus).getPickedBlock(item);
					if (pickedBlock != null) {
						Gui.drawRect(xpos - 1, ypos - 1, xpos + 18, ypos + 18, 0x66000000);

						FontRenderer font = ClientHelper.fontRenderer();
						boolean unicode = font.getUnicodeFlag();
						font.setUnicodeFlag(true);
						String name = StatCollector.translateToLocal("ttmisc.focusDislocation.tooltip");
						int strLength = font.getStringWidth(name);

						Gui.drawRect(xpos + 18, ypos, xpos + 18 + strLength + 4, ypos + 9, 0x66000000);
						font.drawStringWithShadow(name, xpos + 20, ypos, 0xFFAA00);

						NBTTagCompound cmp = ((ItemFocusDislocation) focus).getStackTileEntity(item);
						if (cmp != null && !cmp.hasNoTags()) {
							String content = StatCollector.translateToLocal("ttmisc.focusDislocation.tooltipExtra");
							font.getStringWidth(content);

							Gui.drawRect(xpos + 18, ypos + 9, xpos + 18 + strLength + 4, ypos + 18, 0x66000000);
							font.drawStringWithShadow(content, xpos + 20, ypos + 9, 0xFFAA00);
						}

						if (new ItemStack(((ItemBlock) pickedBlock.getItem()).field_150939_a).getItem() != null)
							renderItem.renderItemIntoGUI(font, ClientHelper.minecraft().renderEngine, new ItemStack(((ItemBlock) pickedBlock.getItem()).field_150939_a), xpos, ypos);
						else {
							if (((ItemBlock) pickedBlock.getItem()).field_150939_a == Blocks.reeds)
								renderItem.renderItemIntoGUI(font, ClientHelper.minecraft().renderEngine, new ItemStack(Items.reeds), xpos, ypos);
						}
						font.setUnicodeFlag(unicode);
					}
				}
			}
		}
	}

}
