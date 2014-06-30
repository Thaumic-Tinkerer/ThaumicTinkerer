package vazkii.tinkerer.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.research.ScanManager;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.gui.button.*;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.tile.TileAspectAnalyzer;
import vazkii.tinkerer.common.block.tile.TileRPlacer;
import vazkii.tinkerer.common.block.tile.container.ContainerAspectAnalyzer;
import vazkii.tinkerer.common.block.tile.container.ContainerRemotePlacer;
import vazkii.tinkerer.common.network.packet.PacketTabletButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nekosune on 30/06/14.
 */
public class GuiRemotePlacer extends GuiContainer {
    private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ASPECT_ANALYZER);

    int x, y;
    List<IRadioButton> radioButtons = new ArrayList();
    List<GuiButtonRP> buttonListRP = new ArrayList();
    TileRPlacer placer;
    Aspect aspectHovered = null;

    public GuiRemotePlacer(TileRPlacer placer, InventoryPlayer inv) {
        super(new ContainerRemotePlacer(placer, inv));
        this.placer = placer;
    }

    @Override
    public void initGui() {
        super.initGui();
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        buttonListRP.clear();
        addButton(new GuiButtonRPRadio(0, x + 100, y + 0, true, radioButtons));
        addButton(new GuiButtonRPRadio(1, x + 100, y + 13, false, radioButtons));
        addButton(new GuiButtonRPRadio(2, x + 100, y + 26, false, radioButtons));
        addButton(new GuiButtonRPRadio(3, x + 100, y + 39, false, radioButtons));
        buttonList = buttonListRP;

    }
    private void addButton(GuiButtonRP button) {
        buttonListRP.add(button);
        if (button instanceof IRadioButton)
            radioButtons.add((IRadioButton) button);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton instanceof IRadioButton)
            ((IRadioButton) par1GuiButton).enableFromClick();
        else buttonListRP.get(0).buttonEnabled = !buttonListRP.get(0).buttonEnabled;

        //tablet.leftClick = buttonListAT.get(1).buttonEnabled;
        //tablet.redstone = buttonListAT.get(0).buttonEnabled;

        //ThaumicTinkerer.netHandler.sendToServer(new PacketTabletButton(tablet));
    }
    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(gui);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        fontRendererObj.drawString("1", x + 120, y + 2, 0x999999);
        fontRendererObj.drawString("2", x + 120, y + 15, 0x999999);
        fontRendererObj.drawString("3", x + 120, y + 28, 0x999999);
        fontRendererObj.drawString("4", x + 120, y + 41, 0x999999);
        GL11.glColor3f(1F, 1F, 1F);
    }


}
