package com.nekokittygames.Thaumic.Tinkerer.common.research

import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibResearch
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import thaumcraft.api.aspects.AspectList
import thaumcraft.api.research.ResearchPage.PageType
import thaumcraft.api.research.{ResearchItem, ResearchPage}

/**
 * Created by Katrina on 22/05/2015.
 */
trait TTResearchItem extends ResearchItem {
  override def setPages(par: ResearchPage*): ResearchItem = {
    for (page <- par) {
      if (page.`type` == PageType.TEXT)
        page.text = "ttresearch.page." + key + "." + page.text
      if (checkInfusion && page.`type` == PageType.INFUSION_CRAFTING) {
        if (parentsHidden == null || parentsHidden.length == 0)
          parentsHidden = Array[String]("INFUSION")
        else {
          val newParents: Array[String] = new Array[String](parentsHidden.length + 1)
          newParents(0) = "INFUSION"
          Array.copy(parentsHidden, 0, newParents, 1, parentsHidden.length)
        }
      }
    }
    return super.setPages(par: _*)

  }

  def checkInfusion: Boolean = false

  @SideOnly(Side.CLIENT)
  override def getName: String = LibResearch.lang.translate("name." + key)

  override def getText: String = super.getText
}

object TTResearchItem {
  def apply(name: String) = new ResearchItem(name: String, LibResearch.CATEGORY_THAUMICTINKERER) with TTResearchItem

  def apply(name: String, tags: AspectList, col: Int, row: Int, complex: Int, icon: ResourceLocation, pages: ResearchPage*) = {
    val v = new ResearchItem(name, LibResearch.CATEGORY_THAUMICTINKERER, tags, row, col, complex, icon)
    v.setPages(pages: _*)
    v
  }

  def apply(name: String, tags: AspectList, col: Int, row: Int, complex: Int, icon: ItemStack, pages: ResearchPage*) = {
    val v = new ResearchItem(name, LibResearch.CATEGORY_THAUMICTINKERER, tags, row, col, complex, icon)
    v.setPages(pages: _*)
    v
  }


}
