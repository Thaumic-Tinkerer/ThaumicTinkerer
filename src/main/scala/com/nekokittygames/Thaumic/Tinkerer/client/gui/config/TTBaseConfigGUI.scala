package com.nekokittygames.Thaumic.Tinkerer.client.gui.config

import com.nekokittygames.Thaumic.Tinkerer.common.core.misc.TTConfig
import net.minecraft.client.gui.{GuiButton, GuiScreen}

/**
 * Created by Katrina on 22/05/2015.
 */
class TTBaseConfigGUI(parent: GuiScreen) extends GuiScreen {
  override def initGui(): Unit = {

    this.buttonList.clear()
    this.buttonList.asInstanceOf[java.util.List[GuiButton]].add(new GuiButton(0, 10, 10, TTConfig.lang.translate("general")))


    this.buttonList.asInstanceOf[java.util.List[GuiButton]].add(new GuiButton(1, 10, 50, this.width - 20, 20, TTConfig.lang.translate("back")))
  }

  override def actionPerformed(button: GuiButton): Unit = {
    button.id match {
      case 1 => mc.displayGuiScreen(parent)
      case 0 => mc.displayGuiScreen(new TTGeneralConfigGUI(this))
      case _ => {}
    }
  }
}
