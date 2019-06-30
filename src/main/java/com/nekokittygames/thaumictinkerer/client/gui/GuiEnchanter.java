package com.nekokittygames.thaumictinkerer.client.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiEnchantmentButton;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiEnchantmentLevelButton;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiEnchantmentStartButton;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiFramedEnchantmentButton;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.client.misc.ClientHelper;
import com.nekokittygames.thaumictinkerer.common.containers.EnchanterContainer;
import com.nekokittygames.thaumictinkerer.common.packets.*;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.resources.ItemCrystalEssence;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI for the Enchanter.
 */
public class GuiEnchanter extends GuiContainer {

    private static final int HEIGHT = 166;
    private static final int WIDTH = 176;
    private TileEntityEnchanter enchanter;
    private GuiEnchantmentButton[] enchantButtons = new GuiEnchantmentButton[16];
    private GuiEnchantmentStartButton startButton;
    private List<String> tooltip = new ArrayList<>();
    private int visRequireWidth = 0;
    private int visRequireHeight = 0;
    private int x, y;
    private ItemStack lastTickItem;
    private ItemStack stack;

    /**
     * Constructor
     *
     * @param tileEntity Enchanter to display GUI for
     * @param container  enchanter's container
     */
    public GuiEnchanter(TileEntityEnchanter tileEntity, EnchanterContainer container) {
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.enchanter = tileEntity;
        stack = enchanter.getInventory().getStackInSlot(0);
        lastTickItem = stack;
    }

    /**
     * gets the Enchanter this GUI is for
     *
     * @return enchanter object
     */
    public TileEntityEnchanter getEnchanter() {
        return enchanter;
    }

    /**
     * Sets the Enchanter
     * @param enchanter enchanter to set
     */
    public void setEnchanter(TileEntityEnchanter enchanter) {
        this.enchanter = enchanter;
    }

    /**
     * Gets the current tooltip
     * @return tooltip
     */
    public List<String> getTooltip() {
        return tooltip;
    }

    /**
     * gets the width required to display Vis
     * @return required width
     */
    public int getVisRequireWidth() {
        return visRequireWidth;
    }

    /**
     * Sets the width required to display vis
     * @param visRequireWidth width required
     */
    public void setVisRequireWidth(int visRequireWidth) {
        this.visRequireWidth = visRequireWidth;
    }

    /**
     * gets the height required to display Vis
     * @return required height
     */
    public int getVisRequireHeight() {
        return visRequireHeight;
    }

    /**
     * sets the required height to display vis
     * @param visRequireHeight required height
     */
    public void setVisRequireHeight(int visRequireHeight) {
        this.visRequireHeight = visRequireHeight;
    }

    /**
     * gets the xPos of the GUI
     * @return xPos
     */
    public int getX() {
        return x;
    }

    /**
     * gets the yPos of the GUI
     * @return yPos
     */
    public int getY() {
        return y;
    }

    /**
     * Initializes the GUI
     */
    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        this.visRequireWidth = this.fontRenderer.getStringWidth("Required Vis Crystals") + 2;
        this.visRequireHeight = this.fontRenderer.FONT_HEIGHT + 2;
        buildButtonList();

    }

    /**
     * Builds the main button list
     */
    private void buildButtonList() {
        buttonList.clear();
        for (int i = 0; i < 16; i++) {
            int z = -11;
            if (i > 7) {
                z = 26;
            }
            if ((enchantButtons[8] == null || !enchantButtons[8].enabled)) {
                z = 0;
            }
            GuiEnchantmentButton button = new GuiEnchantmentButton(this, 1 + i, x + 38 + ((i) % 8) * 16, y + 32 + z);
            enchantButtons[i] = button;
            buttonList.add(button);
        }

        asignEnchantButtons();

        int i = 0;
        for (Integer enchant : enchanter.getEnchantments()) {
            GuiFramedEnchantmentButton button = new GuiFramedEnchantmentButton(this, 17 + i * 3, x + xSize + 4, y + (i * 26) + 30 + 15);
            button.enchant = Enchantment.getEnchantmentByID(enchant);
            buttonList.add(button);
            buttonList.add(new GuiEnchantmentLevelButton(17 + i * 3 + 1, x + xSize + 24, y + (i * 26) + 30 + 15 - 4, false));
            buttonList.add(new GuiEnchantmentLevelButton(17 + i * 3 + 2, x + xSize + 31, y + (i * 26) + 30 + 15 - 4, true));
            ++i;
        }
        startButton = new GuiEnchantmentStartButton(0, x + 8, y + 58);
        buttonList.add(startButton);
    }

    /**
     * Draws the foreground screen
     * @param mouseX xPos of mouse
     * @param mouseY yPos of mouse
     * @param partialTicks update ticks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Assigns enchants to the buttons
     */
    private void asignEnchantButtons() {
        for (int i = 0; i < 16; i++) {
            enchantButtons[i].enchant = null;
            enchantButtons[i].enabled = false;
        }
        ItemStack currentStack = enchanter.getInventory().getStackInSlot(0);
        if (currentStack == ItemStack.EMPTY || currentStack.isItemEnchanted())
            return;
        int it = 0;
        for (int enchant : enchanter.getCachedEnchantments()) {
            enchantButtons[it].enchant = Enchantment.getEnchantmentByID(enchant);
            enchantButtons[it].enabled = true;
            it++;
            if (it >= 16)
                break;
        }
    }

    /**
     * updates the screen per tick
     */
    @Override
    public void updateScreen() {
        super.updateScreen();

        stack = enchanter.getInventory().getStackInSlot(0);
        if (!stack.equals(lastTickItem))
            buildButtonList();

        lastTickItem = stack;

        if (enchanter.getEnchantments().size() > 0 && !enchanter.isWorking() && !stack.isItemEnchanted() && enchanter.playerHasIngredients(enchanter.getEnchantmentCost(), Minecraft.getMinecraft().player)) {
            startButton.setEnabled(true);
        } else {
            startButton.setEnabled(false);
        }

    }

    /**
     * Draws the background GUI layer
     * @param partialTicks update Ticks
     * @param mouseX xPos of mouse
     * @param mouseY yPos of mouse
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibClientResources.GUI_ENCHANTER);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        if (enchanter.getCachedEnchantments().size() > 0 && enchanter.getCachedEnchantments().size() < 9) {

            // 34,61
            drawTexturedModalRect(x + 34, y + 28, 0, ySize, 147, 24);
        } else if (enchanter.getCachedEnchantments().size() >= 8) {
            drawTexturedModalRect(x + 34, y + 17, 0, ySize, 147, 24);
            // 34,61
            drawTexturedModalRect(x + 34, y + 54, 0, ySize, 147, 24);
        }

        this.fontRenderer.drawString("Required Vis Crystals", x + 177, y + 7, 0x999999);
        GlStateManager.color(1F, 1F, 1F);
        int j = 0;
        zLevel = 100.0F;
        itemRender.zLevel = 100.0F;

        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableDepth();
        RenderHelper.enableGUIStandardItemLighting();

        for (ItemStack itemStack : enchanter.getEnchantmentCost()) {
            if (!itemStack.isEmpty()) {

                //OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, short1 / 1.0F, short2 / 1.0F);
                itemRender.renderItemAndEffectIntoGUI(itemStack, x + 177 + (j * 17), y + 7 + fontRenderer.FONT_HEIGHT);
                ItemCrystalEssence crystalEssence = (ItemCrystalEssence) itemStack.getItem();
                Aspect aspect = crystalEssence.getAspects(itemStack).getAspectsSortedByAmount()[0];
                itemRender.renderItemOverlayIntoGUI(fontRenderer, itemStack, x + 177 + (j * 17), y + 7 + fontRenderer.FONT_HEIGHT, "" + crystalEssence.getAspects(itemStack).getAmount(aspect));
                GlStateManager.enableAlpha();
                if (mouseX >= x + 177 + (j * 17) && mouseX < x + 177 + (j * 17) + 16 && mouseY >= y + 7 + fontRenderer.FONT_HEIGHT && mouseY < y + 7 + fontRenderer.FONT_HEIGHT + 16) {
                    List<String> tooltip = new ArrayList<>();
                    tooltip.add(ChatFormatting.AQUA + ThaumicTinkerer.proxy.localize(aspect.getName()));
                    getTooltip().addAll(tooltip);
                }
                j++;
            }
        }


        itemRender.zLevel = 0.0F;
        zLevel = 0.0F;


    }

    /**
     * Callback for button pressed
     * @param button button that was pressed
     */
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            PacketHandler.INSTANCE.sendToServer(new PacketStartEnchant(enchanter, Minecraft.getMinecraft().player));
        } else if (button.id <= 16) {
            GuiEnchantmentButton enchantButton = enchantButtons[button.id - 1];
            if (enchantButton != null && enchantButton.enchant != null) {
                PacketHandler.INSTANCE.sendToServer(new PacketAddEnchant(enchanter, Enchantment.getEnchantmentID(enchantButton.enchant)));
            }
        } else {

            int type = (button.id - 17) % 3;
            int index = (button.id - 17) / 3;
            if (index >= enchanter.getEnchantments().size() || index >= enchanter.getLevels().size())
                return;

            if (type == 0) {
                PacketHandler.INSTANCE.sendToServer(new PacketRemoveEnchant(enchanter, enchanter.getEnchantments().get(index)));
            } else {
                GuiEnchantmentLevelButton levelButton = (GuiEnchantmentLevelButton) button;
                PacketHandler.INSTANCE.sendToServer(new PacketIncrementEnchantLevel(enchanter, enchanter.getEnchantments().get(index), levelButton.plus));
            }

        }
    }

    /**
     * Draws the foreground layer, draws the tooltips added by enchanter
     * @param mouseX xPos of mouse
     * @param mouseY yPos of mouse
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        if (!tooltip.isEmpty())
            ClientHelper.renderTooltip(mouseX - x, mouseY - y, tooltip);
        tooltip.clear();
    }

}
