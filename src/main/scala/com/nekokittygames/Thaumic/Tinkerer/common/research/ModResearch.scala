package com.nekokittygames.Thaumic.Tinkerer.common.research

import com.nekokittygames.Thaumic.Tinkerer.client.libs.LibResources
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibResearch
import net.minecraft.util.ResourceLocation
import thaumcraft.api.research.ResearchCategories

/**
 * Created by fiona on 22/05/2015.
 */
object ModResearch {


  def registerResearch() = {
    registerResearchPage()
  }

  def registerResearchPage() = {
    ResearchCategories.registerCategory(LibResearch.CATEGORY_THAUMICTINKERER, new ResourceLocation(LibResources.MISC_R_ENCHANTING), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"))
  }
}
