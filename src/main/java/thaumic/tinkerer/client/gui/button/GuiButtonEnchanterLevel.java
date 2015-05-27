/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [16 Sep 2013, 16:13:50 (GMT)]
 */
package thaumic.tinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import thaumic.tinkerer.client.lib.LibResources;

public class GuiButtonEnchanterLevel extends GuiButton {

    private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ENCHANTER);

    boolean plus;

    public GuiButtonEnchanterLevel(int par1, int par2, int par3, boolean plus) {
        super(par1, par2, par3, 7, 7, "");
        this.plus = plus;
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        if (!enabled)
            return;

        int x = 218 + (plus ? 7 : 0);

        par1Minecraft.renderEngine.bindTexture(gui);
        drawTexturedModalRect(xPosition, yPosition, x, 0, 7, 7);
    }

}
