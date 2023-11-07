package com.nekokittygames.thaumictinkerer.common.items.Kami.ichorpouch;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiPouch extends GuiContainer {

    private static final ResourceLocation gui = new ResourceLocation("thaumictinkerer", "textures/gui/" + "pouch.png");

    int x, y;

    public GuiPouch(Container par1Container) {
        super(par1Container);
    }

    protected boolean checkHotbarKeys(int par1) {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();
        xSize = ySize = 256;

        guiLeft = x = (width - xSize) / 2;
        guiTop = y = (height - ySize) / 2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        mc.renderEngine.bindTexture(gui);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
