package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiButtonMM;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiRadioButtonMM;
import com.nekokittygames.thaumictinkerer.client.gui.button.IRadioButton;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.containers.EnchanterContainer;
import com.nekokittygames.thaumictinkerer.common.containers.MagnetContainer;
import com.nekokittygames.thaumictinkerer.common.items.ItemSoulMould;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketMobMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiEnchanter extends GuiContainer {


    private TileEntityEnchanter enchanter;
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    public int visRequireWidth=0;
    public int visRequireHeight=0;
    public int x, y;

    public GuiEnchanter(TileEntityEnchanter tileEntity, EnchanterContainer container) {
        super(container);
        xSize=WIDTH;
        ySize=HEIGHT;
        this.enchanter=tileEntity;
    }

    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        this.visRequireWidth=this.fontRenderer.getStringWidth("Required Vis Crystals")+2;
        this.visRequireHeight=this.fontRenderer.FONT_HEIGHT+2;

    }





    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibClientResources.GUI_ENCHANTER);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);

        this.fontRenderer.drawString("Required Vis Crystals",x+177,y+7,0x999999);



    }
}
