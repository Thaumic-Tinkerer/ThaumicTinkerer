package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiTexturedButton;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiTexturedRadioButton;
import com.nekokittygames.thaumictinkerer.client.gui.button.IRadioButton;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.containers.AnimationTabletContainer;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketTabletButton;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityAnimationTablet;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI for the Animation tablet.
 */
public class GuiAnimationTablet extends GuiContainer {
    private static final int HEIGHT = 166;
    private static final int WIDTH = 176;
    private TileEntityAnimationTablet animation_tablet;
    private List<GuiTexturedButton> buttonListAT = new ArrayList<>();
    private List<IRadioButton> radioButtons = new ArrayList<>();
    private int x, y;

    /**
     * Constructor
     *
     * @param animation_tablet animation tablet to display GUI for
     * @param inventorySlotsIn animation tablet's container
     */
    public GuiAnimationTablet(TileEntityAnimationTablet animation_tablet, AnimationTabletContainer inventorySlotsIn) {
        super(inventorySlotsIn);
        this.animation_tablet = animation_tablet;
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    /**
     * Gets the animation tablet
     *
     * @return animation tablet
     */
    public TileEntityAnimationTablet getAnimationTablet() {
        return animation_tablet;
    }

    /**
     * Sets the animation table
     * @param animation_tablet animation tablet to set
     */
    public void setEnchanter(TileEntityAnimationTablet animation_tablet) {
        this.animation_tablet = animation_tablet;
    }

    /**
     * Draws the GUI screen
     * @param mouseX xPos of mouse
     * @param mouseY yPos of mouse
     * @param partialTicks ticks for update
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Initialize the GUI
     */
    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();
        buttonListAT.clear();
        addButton(new GuiTexturedRadioButton(0, x + 110, y + 28, LibClientResources.GUI_ANIMATION_TABLET, !animation_tablet.isRightClick(), radioButtons));
        addButton(new GuiTexturedRadioButton(1, x + 110, y + 48, LibClientResources.GUI_ANIMATION_TABLET, animation_tablet.isRightClick(), radioButtons));
        buttonList.addAll(buttonListAT);
    }

    /**
     * Callback for button Button clicked
     * @param button button that was clicked
     */
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof IRadioButton)
            ((IRadioButton) button).enableFromClick();
        else buttonListAT.get(1).setButtonEnabled(!buttonListAT.get(1).isButtonEnabled());

        //mobMagnet.adult = buttonListMM.get(0).enabled;
        animation_tablet.setRightClick(buttonListAT.get(1).isButtonEnabled());
        PacketHandler.INSTANCE.sendToServer(new PacketTabletButton(animation_tablet, animation_tablet.isRightClick()));
    }

    /**
     * Adds a button to the screen
     * @param button button to add to the screen
     * @param <T> Type of button to add
     * @return returns the button
     */
    @NotNull
    @Override
    protected <T extends GuiButton> T addButton(@NotNull T button) {
        if (button instanceof GuiTexturedButton) {
            buttonListAT.add((GuiTexturedButton) button);

            if (button instanceof IRadioButton)
                radioButtons.add((IRadioButton) button);
            return button;
        } else
            return super.addButton(button);
    }


    /**
     * Draws the GUI background
     * @param partialTicks ticks in update
     * @param mouseX xPos of mouse
     * @param mouseY yPos of mouse
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibClientResources.GUI_ANIMATION_TABLET);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        String leftClick = ThaumicTinkerer.proxy.localize("ttmisc.animation_tablet.left");
        String rightClick = ThaumicTinkerer.proxy.localize("ttmisc.animation_tablet.right");

        fontRenderer.drawString(leftClick, x + 130, y + 30, 0x999999);
        fontRenderer.drawString(rightClick, x + 130, y + 50, 0x999999);
    }
}
