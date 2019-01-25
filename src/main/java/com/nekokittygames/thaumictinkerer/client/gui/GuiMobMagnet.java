package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiButtonMM;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiRadioButtonMM;
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
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiMobMagnet extends GuiContainer {


    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;
    private int x, y;
    private List<GuiButtonMM> buttonListMM = new ArrayList<>();
    private List<IRadioButton> radioButtons = new ArrayList<>();
    private TileEntityMobMagnet mobMagnet;

    public GuiMobMagnet(TileEntityMobMagnet tileEntity, MagnetContainer container) {
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
        this.mobMagnet = tileEntity;
    }

    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        buttonListMM.clear();
        addButton(new GuiRadioButtonMM(0, x + 100, y + 28, mobMagnet.isPullAdults(), radioButtons));
        addButton(new GuiRadioButtonMM(1, x + 100, y + 48, !mobMagnet.isPullAdults(), radioButtons));
        buttonList.addAll(buttonListMM);

    }

    @Override
    protected <T extends GuiButton> T addButton(T button) {
        if (button instanceof GuiButtonMM) {
            buttonListMM.add((GuiButtonMM) button);

            if (button instanceof IRadioButton)
                radioButtons.add((IRadioButton) button);
            return button;
        } else
            return super.addButton(button);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button instanceof IRadioButton)
            ((IRadioButton) button).enableFromClick();
        else buttonListMM.get(0).enabled = !buttonListMM.get(0).enabled;

        //mobMagnet.adult = buttonListMM.get(0).enabled;
        mobMagnet.setPullAdults(buttonListMM.get(0).enabled);
        PacketHandler.INSTANCE.sendToServer(new PacketMobMagnet(mobMagnet, mobMagnet.isPullAdults()));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
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
