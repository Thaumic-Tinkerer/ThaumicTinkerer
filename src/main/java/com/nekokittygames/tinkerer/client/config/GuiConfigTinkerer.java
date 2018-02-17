package com.nekokittygames.tinkerer.client.config;

import com.nekokittygames.tinkerer.common.config.ModConfig;
import com.nekokittygames.tinkerer.common.libs.LibMisc;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;

public class GuiConfigTinkerer extends GuiConfig {

    private static final String LANG_PREFIX = LibMisc.MOD_ID+ ".category.";

    public GuiConfigTinkerer(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), LibMisc.MOD_ID, false, false, I18n.format(LibMisc.MOD_ID + ".config.title"));
    }

    private static List<IConfigElement> getConfigElements() {
        final Configuration configuration = ModConfig.ConfigurationHolder.getConfiguration();

        final ConfigCategory topLevelCategory = configuration.getCategory(Configuration.CATEGORY_GENERAL);
        topLevelCategory.getChildren().forEach(configCategory -> configCategory.setLanguageKey(LANG_PREFIX + configCategory.getName()));

        return new ConfigElement(topLevelCategory).getChildElements();
    }
}
