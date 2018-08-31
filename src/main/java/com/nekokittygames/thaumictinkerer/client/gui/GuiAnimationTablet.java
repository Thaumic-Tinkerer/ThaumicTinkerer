package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.containers.AnimationTabletContainer;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityAnimationTablet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;

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
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibClientResources.GUI_ANIMATION_TABLET);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
    }
}
