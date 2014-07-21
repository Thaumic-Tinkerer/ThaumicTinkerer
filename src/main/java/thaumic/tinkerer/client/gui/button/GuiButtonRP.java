package thaumic.tinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import thaumic.tinkerer.client.lib.LibResources;

/**
 * Created by nekosune on 30/06/14.
 */
public class GuiButtonRP extends GuiButton {
    private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_REMOTE_PLACER);

    public boolean buttonEnabled = false;

    public GuiButtonRP(int par1, int par2, int par3, boolean enabled) {
        super(par1, par2, par3, 13, 13, "");
        this.buttonEnabled = enabled;
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        if (buttonEnabled) {
            par1Minecraft.renderEngine.bindTexture(gui);
            int y = buttonEnabled ? 13 : 0;
            drawTexturedModalRect(xPosition, yPosition, 176, y, width, height);
        }
    }
}
