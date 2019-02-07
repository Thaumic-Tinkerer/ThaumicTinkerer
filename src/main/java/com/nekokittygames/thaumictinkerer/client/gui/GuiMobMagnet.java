package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiTexturedButton;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiTexturedRadioButton;
import com.nekokittygames.thaumictinkerer.client.gui.button.IRadioButton;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.containers.MagnetContainer;
import com.nekokittygames.thaumictinkerer.common.items.ItemSoulMould;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketMobMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI for the mob magnet.
 */
public class GuiMobMagnet extends GuiContainer {


    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;
    private int x, y;
    private List<GuiTexturedButton> buttonListMM = new ArrayList<>();
    private List<IRadioButton> radioButtons = new ArrayList<>();
    private TileEntityMobMagnet mobMagnet;

    /**
     * Constructor
     *
     * @param tileEntity mob magnet to display GUI for
     * @param container  mo magnet's container
     */
    public GuiMobMagnet(TileEntityMobMagnet tileEntity, MagnetContainer container) {
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.mobMagnet = tileEntity;
    }

    /**
     * Initializes the GUI
     */
    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        buttonListMM.clear();
        addButton(new GuiTexturedRadioButton(0, x + 100, y + 28, LibClientResources.GUI_MOBMAGNET, mobMagnet.isPullAdults(), radioButtons));
        addButton(new GuiTexturedRadioButton(1, x + 100, y + 48, LibClientResources.GUI_MOBMAGNET, !mobMagnet.isPullAdults(), radioButtons));
        buttonList.addAll(buttonListMM);

    }

    /**
     * Adds button to GUI
     *
     * @param button button to add
     * @param <T>    type of button to add
     * @return added button
     */
    @NotNull
    @Override
    protected <T extends GuiButton> T addButton(@NotNull T button) {
        if (button instanceof GuiTexturedButton) {
            buttonListMM.add((GuiTexturedButton) button);

            if (button instanceof IRadioButton)
                radioButtons.add((IRadioButton) button);
            return button;
        } else
            return super.addButton(button);
    }

    /**
     * Callback for button pressed
     * @param button button that was pressed
     */
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button instanceof IRadioButton)
            ((IRadioButton) button).enableFromClick();
        else buttonListMM.get(1).setButtonEnabled(!buttonListMM.get(1).isButtonEnabled());

        //mobMagnet.adult = buttonListMM.get(0).enabled;
        mobMagnet.setPullAdults(buttonListMM.get(0).isButtonEnabled());
        PacketHandler.INSTANCE.sendToServer(new PacketMobMagnet(mobMagnet, mobMagnet.isPullAdults()));
    }

    /**
     * Draws the background layer
     * @param partialTicks update ticks
     * @param mouseX xPos of mouse
     * @param mouseY yPos of mouse
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibClientResources.GUI_MOBMAGNET);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        String adult = ThaumicTinkerer.proxy.localize("ttmisc.mobmagnet.adult");
        String child = ThaumicTinkerer.proxy.localize("ttmisc.mobmagnet.child");
        ItemStack stack = mobMagnet.getInventory().getStackInSlot(0);
        String filter;
        if (stack != ItemStack.EMPTY) {
            String name = ItemSoulMould.getEntityName(stack);
            if (name != null) {
                filter = ThaumicTinkerer.proxy.localize("entity." + name + ".name");
            } else
                filter = ThaumicTinkerer.proxy.localize("ttmisc.mobmagnet.none");

        } else
            filter = ThaumicTinkerer.proxy.localize("ttmisc.mobmagnet.all");
        fontRenderer.drawString(filter, x + xSize / 2 - fontRenderer.getStringWidth(filter) / 2 - 26, y + 16, 0x999999);
        fontRenderer.drawString(adult, x + 120, y + 30, 0x999999);
        fontRenderer.drawString(child, x + 120, y + 50, 0x999999);
        GL11.glColor3f(1F, 1F, 1F);
    }
}
