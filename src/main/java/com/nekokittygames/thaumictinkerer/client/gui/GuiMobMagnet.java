/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiTexturedRadioButton;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.containers.MagnetContainer;
import com.nekokittygames.thaumictinkerer.common.items.ItemSoulMould;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketMobMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * GUI for the mob magnet.
 */
public class GuiMobMagnet extends GuiMagnet {


    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;
    private int x, y;
    private final TileEntityMobMagnet mobMagnet;

    /**
     * Constructor
     *
     * @param tileEntity {@link TileEntityMobMagnet} to display GUI for
     * @param container  {@link MagnetContainer} for magnet
     */
    public GuiMobMagnet(TileEntityMobMagnet tileEntity, MagnetContainer container) {
        super(tileEntity, container);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.mobMagnet = tileEntity;
        mob=true;
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
        getButtons();
        buttonList.addAll(buttonListMM);
    }

    /**
     * Gets the buttons this magnet uses
     */
    @Override
    protected void getButtons() {
        super.getButtons();
        addButton(new GuiTexturedRadioButton(2, x + 100, y + 28, LibClientResources.GUI_MOBMAGNET, mobMagnet.isPullAdults(), "age",radioButtons));
        addButton(new GuiTexturedRadioButton(3, x + 100, y + 48, LibClientResources.GUI_MOBMAGNET, !mobMagnet.isPullAdults(), "age",radioButtons));
    }


    /**
     * Callback for {@link GuiButton} pressed
     * @param button {@link GuiButton} that was pressed
     */
    @Override
    protected void actionPerformed(GuiButton button) {
       super.actionPerformed(button);

        //mobMagnet.adult = buttonListMM.get(0).enabled;
        mobMagnet.setPullAdults(buttonListMM.get(2).isButtonEnabled());
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
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
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
