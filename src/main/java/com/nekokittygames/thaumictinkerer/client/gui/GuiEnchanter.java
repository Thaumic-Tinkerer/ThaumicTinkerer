package com.nekokittygames.thaumictinkerer.client.gui;

import com.nekokittygames.thaumictinkerer.ThaumicTinkerer;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiButtonMM;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiEnchantmentButton;
import com.nekokittygames.thaumictinkerer.client.gui.button.GuiRadioButtonMM;
import com.nekokittygames.thaumictinkerer.client.gui.button.IRadioButton;
import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.client.misc.ClientHelper;
import com.nekokittygames.thaumictinkerer.common.containers.EnchanterContainer;
import com.nekokittygames.thaumictinkerer.common.containers.MagnetContainer;
import com.nekokittygames.thaumictinkerer.common.items.ItemSoulMould;
import com.nekokittygames.thaumictinkerer.common.packets.PacketHandler;
import com.nekokittygames.thaumictinkerer.common.packets.PacketMobMagnet;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityMobMagnet;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiEnchanter extends GuiContainer {


    private TileEntityEnchanter enchanter;
    public List<String> tooltip = new ArrayList();
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;
    public int visRequireWidth=0;
    public int visRequireHeight=0;
    public int x, y;
    public ItemStack lastTickItem;
    public ItemStack stack;
    GuiEnchantmentButton[] enchantButtons = new GuiEnchantmentButton[16];

    public GuiEnchanter(TileEntityEnchanter tileEntity, EnchanterContainer container) {
        super(container);
        xSize=WIDTH;
        ySize=HEIGHT;
        this.enchanter=tileEntity;
        stack=enchanter.getInventory().getStackInSlot(0);
        lastTickItem=stack;
    }

    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        this.visRequireWidth=this.fontRenderer.getStringWidth("Required Vis Crystals")+2;
        this.visRequireHeight=this.fontRenderer.FONT_HEIGHT+2;
        buildButtonList();

    }

    public void buildButtonList() {
        buttonList.clear();
        for (int i = 0; i < 16; i++) {
            int z = -24;
            if (i > 7 || (enchantButtons[8] == null || !enchantButtons[8].enabled)) {
                z = 0;
            }
            GuiEnchantmentButton button = new GuiEnchantmentButton(this, 1 + i, x + 38 + ((i) % 8) * 16, y + 32 + z);
            enchantButtons[i] = button;
            buttonList.add(button);
        }

        asignEnchantButtons();
    }
    @Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
}
    private void asignEnchantButtons() {
        for (int i = 0; i < 16; i++) {
            enchantButtons[i].enchant = null;
            enchantButtons[i].enabled = false;
        }
        ItemStack currentStack = enchanter.getInventory().getStackInSlot(0);
        if (currentStack == ItemStack.EMPTY || currentStack.isItemEnchanted())
            return;
        int it = 0;
        for (int enchant : enchanter.cachedEnchantments) {
            enchantButtons[it].enchant = Enchantment.getEnchantmentByID(enchant);
            enchantButtons[it].enabled = true;
            it++;
            if (it >= 16)
                break;
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        stack= enchanter.getInventory().getStackInSlot(0);
        if(stack!=lastTickItem)
            buildButtonList();

        lastTickItem=stack;

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(LibClientResources.GUI_ENCHANTER);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
        if(enchanter.cachedEnchantments.size()>0 && enchanter.cachedEnchantments.size()<9)
        {

            // 34,61
            drawTexturedModalRect(x+34,y+28,0,ySize,147,24);
        }
        else if(enchanter.cachedEnchantments.size()>=9)
        {
            drawTexturedModalRect(x+34,y+17,0,ySize,147,24);
            // 34,61
            drawTexturedModalRect(x+34,y+54,0,ySize,147,24);
        }

        if(enchanter.cachedEnchantments.size()>0)
        {
            this.fontRenderer.drawString(ArrayUtils.toString(enchanter.cachedEnchantments.toArray()),x+30,y+5, 0x610B0B);
        }
        this.fontRenderer.drawString("Required Vis Crystals",x+177,y+7,0x999999);



    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        if (!tooltip.isEmpty())
            ClientHelper.renderTooltip(par1 - x, par2 - y, tooltip);
        tooltip.clear();
    }

}
