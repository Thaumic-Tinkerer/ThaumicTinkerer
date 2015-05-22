package com.nekokittygames.Thaumic.Tinkerer.common.core.misc

import java.io.File

import codechicken.lib.util.LangProxy
import net.minecraftforge.common.config.Configuration

/**
 * Created by Katrina  on 22/05/2015.
 */
object TTConfig {


  final var CATEGORY_GENERAL = Configuration.CATEGORY_GENERAL
  final var lang: LangProxy = new LangProxy("ttconfig")
  var configFile: Configuration = null

  def init(file: File) = {
    configFile = new Configuration(file)
    sync()
  }

  def sync() = {
    configFile.setCategoryComment(CATEGORY_GENERAL, "ATTENTION: Editing this file manually is no longer necessary.\n" +
      "On the Mods list screen, select the entry for Thaumic Tinkerer, then click the Config button to modify these settings.")

    General.useTooltipIndicators = configFile.getBoolean(lang.translate("tooltip.name"), CATEGORY_GENERAL, General.useTooltipIndicators, lang.translate("tooltip.comment"))

    configFile.save
  }


  object General {
    var useTooltipIndicators: Boolean = true // Tootlip, although funny, did not make the cut
  }

}
