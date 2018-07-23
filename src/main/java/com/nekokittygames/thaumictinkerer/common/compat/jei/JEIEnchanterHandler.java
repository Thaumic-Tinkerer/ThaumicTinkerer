package com.nekokittygames.thaumictinkerer.common.compat.jei;

import com.nekokittygames.thaumictinkerer.client.gui.GuiEnchanter;
import mezz.jei.api.gui.IAdvancedGuiHandler;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JEIEnchanterHandler implements IAdvancedGuiHandler<GuiEnchanter> {
    @Override
    public Class<GuiEnchanter> getGuiContainerClass() {
        return GuiEnchanter.class;
    }

    @Nullable
    @Override
    public List<Rectangle> getGuiExtraAreas(GuiEnchanter guiContainer) {
        List<Rectangle> exclusions=new ArrayList<>();
        exclusions.add(new Rectangle(guiContainer.x+176,guiContainer.y+6,guiContainer.visRequireWidth,guiContainer.visRequireHeight));
        return exclusions;
    }

    @Nullable
    @Override
    public Object getIngredientUnderMouse(GuiEnchanter guiContainer, int mouseX, int mouseY) {
        return null;
    }
}
