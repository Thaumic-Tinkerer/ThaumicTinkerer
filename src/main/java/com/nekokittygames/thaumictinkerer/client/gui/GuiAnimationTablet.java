package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.button.*;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.containers.AnimationTabletContainer;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketMobMagnet;
import com.nekokittygames.thaumictinkerer.common.packets.PacketTabletButton;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityAnimationTablet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiAnimationTablet extends GuiContainer {
    public TileEntityAnimationTablet animation_tablet;

    public TileEntityAnimationTablet getAnimationTablet() {
        return animation_tablet;
    }

    public void setEnchanter(TileEntityAnimationTablet animation_tablet) {
        this.animation_tablet= animation_tablet;
    }
    private static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    private int x,y;
    List<GuiButtonAT> buttonListAT = new ArrayList();
    List<IRadioButton> radioButtons = new ArrayList();
    public GuiAnimationTablet(TileEntityAnimationTablet animation_tablet, AnimationTabletContainer inventorySlotsIn) {
        super(inventorySlotsIn);
        this.animation_tablet=animation_tablet;
        xSize=WIDTH;
        ySize=HEIGHT;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        buttonList.clear();
        buttonListAT.clear();
        addButton(new GuiRadioButtonAT(0, x + 110, y + 28, !animation_tablet.isRightClick(), radioButtons));
        addButton(new GuiRadioButtonAT(1, x + 110, y + 48, animation_tablet.isRightClick(), radioButtons));
        buttonList.addAll(buttonListAT);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button instanceof IRadioButton)
            ((IRadioButton) button).enableFromClick();
        else buttonListAT.get(1).enabled = !buttonListAT.get(1).enabled;

        //mobMagnet.adult = buttonListMM.get(0).enabled;
        animation_tablet.setRightClick(buttonListAT.get(1).enabled);
        PacketHandler.INSTANCE.sendToServer(new PacketTabletButton(animation_tablet,animation_tablet.isRightClick()));
    }

    @Override
    protected <T extends GuiButton> T addButton(T button) {
        if(button instanceof GuiButtonAT) {
            buttonListAT.add((GuiButtonAT) button);

            if (button instanceof IRadioButton)
                radioButtons.add((IRadioButton) button);
            return button;
        }
        else
            return super.addButton(button);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibClientResources.GUI_ANIMATION_TABLET);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
        String leftClick = ThaumicTinkerer.proxy.localize("ttmisc.animation_tablet.left");
        String rightClick = ThaumicTinkerer.proxy.localize("ttmisc.animation_tablet.right");

        fontRenderer.drawString(leftClick, x + 130, y + 30, 0x999999);
        fontRenderer.drawString(rightClick, x + 130, y + 50, 0x999999);
    }
}
