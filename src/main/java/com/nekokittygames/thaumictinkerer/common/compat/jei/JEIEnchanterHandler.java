package com.nekokittygames.thaumictinkerer.common.compat.jei;

import com.nekokittygames.thaumictinkerer.client.gui.GuiEnchanter;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JEIEnchanterHandler implements IAdvancedGuiHandler<GuiEnchanter> {
    @Override
    public @NotNull Class<GuiEnchanter> getGuiContainerClass() {
        return GuiEnchanter.class;
    }

    @Nullable
    @Override
    public List<Rectangle> getGuiExtraAreas(GuiEnchanter guiContainer) {
        List<Rectangle> exclusions = new ArrayList<>();
        exclusions.add(new Rectangle(guiContainer.getX() + 176, guiContainer.getY() + 6, guiContainer.getVisRequireWidth(), guiContainer.getVisRequireHeight() + (guiContainer.getEnchanter().getEnchantmentCost().size() > 0 ? 18 : 0)));
        int xOffset=0;
        int y=0;
        for (int i = 0; i < guiContainer.getEnchanter().getEnchantments().size(); i++) {

            if((guiContainer.getY() + (y * 26) + 30 + 15) +24 >guiContainer.height)
            {
                xOffset+=26+14+1;
                y=0;
            }
            exclusions.add(new Rectangle(guiContainer.getX() + 176+xOffset, guiContainer.getY() + (y * 26) + 30 + 15, 34, 25));
            y++;
        }
        return exclusions;
    }

    @Nullable
    @Override
    public Object getIngredientUnderMouse(@NotNull GuiEnchanter guiContainer, int mouseX, int mouseY) {
        return null;
    }
}
