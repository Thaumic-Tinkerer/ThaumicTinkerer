package com.nekokittygames.Thaumic.Tinkerer.client.gui.config

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTConfig
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibMisc
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.common.config.ConfigElement
import net.minecraftforge.fml.client.config.GuiConfig

/**
 * Created by Katrina on 22/05/2015.
 */
class TTGeneralConfigGUI(parent: GuiScreen) extends GuiConfig(parent, new ConfigElement(TTConfig.configFile.getCategory(TTConfig.CATEGORY_GENERAL)).getChildElements, LibMisc.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(TTConfig.configFile.toString)) {

}
