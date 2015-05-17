package com.nekokittygames.Thaumic.Tinkerer.client.gui

import java.util

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.IModGuiFactory.{RuntimeOptionGuiHandler, RuntimeOptionCategoryElement}

/**
 * Factory for the gui configs
 * Created by Katrina on 17/05/2015.
 */
class TTGuiFactory extends IModGuiFactory{
  override def runtimeGuiCategories(): util.Set[RuntimeOptionCategoryElement] = null

  override def initialize(minecraftInstance: Minecraft): Unit = {}

  override def getHandlerFor(element: RuntimeOptionCategoryElement): RuntimeOptionGuiHandler = null

  override def mainConfigGuiClass(): Class[_ <: GuiScreen] = null
}
