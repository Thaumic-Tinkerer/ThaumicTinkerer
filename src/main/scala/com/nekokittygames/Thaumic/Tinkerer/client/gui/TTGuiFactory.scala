package com.nekokittygames.Thaumic.Tinkerer.client.gui

import java.util

import com.nekokittygames.Thaumic.Tinkerer.client.gui.config.TTBaseConfigGUI
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.IModGuiFactory.{RuntimeOptionCategoryElement, RuntimeOptionGuiHandler}

/**
 * Factory for the gui configs
 * Created by Katrina on 17/05/2015.
 */
class TTGuiFactory extends IModGuiFactory{
  override def runtimeGuiCategories(): util.Set[RuntimeOptionCategoryElement] = null

  override def initialize(minecraftInstance: Minecraft): Unit = {}

  override def getHandlerFor(element: RuntimeOptionCategoryElement): RuntimeOptionGuiHandler = null

  override def mainConfigGuiClass(): Class[_ <: GuiScreen] = {
    classOf[TTBaseConfigGUI]
  }
}
