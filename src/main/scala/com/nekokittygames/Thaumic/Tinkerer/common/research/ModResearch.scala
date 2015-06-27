package com.nekokittygames.Thaumic.Tinkerer.common.research

import com.nekokittygames.Thaumic.Tinkerer.client.libs.LibResources
import com.nekokittygames.Thaumic.Tinkerer.common.items.baubles.{ItemEnderDisruption, ItemStabilizerBelt}
import com.nekokittygames.Thaumic.Tinkerer.common.items.quartz.ItemDarkQuartz
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibResearch
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.api.crafting.InfusionRecipe
import thaumcraft.api.research.{ResearchCategories, ResearchPage}
import thaumcraft.common.config.ConfigResearch

/**
 * Created by fiona on 22/05/2015.
 */
object ModResearch {


  def registerResearch() = {
    registerResearchPage()

    var research: TTResearchItem=null

    research=new TTResearchItem(LibResearch.KEY_DARK_QUARTZ,new AspectList(),-2,2,0,new ItemStack(ItemDarkQuartz),new ResearchPage("0"),recipePage(LibResearch.KEY_DARK_QUARTZ+0)).registerResearchItem().asInstanceOf[TTResearchItem]

    research=new TTResearchItem(LibResearch.KEY_STABILIZER_BELT,new AspectList().add(Aspect.ORDER, 4).add(Aspect.EARTH, 4).add(Aspect.TRAVEL, 2),1,-10,1,new ItemStack(ItemStabilizerBelt),new ResearchPage("0"),infusionPage(LibResearch.KEY_STABILIZER_BELT+0)).setParentsHidden("INFUSION").registerResearchItem().asInstanceOf[TTResearchItem]
    research=new TTResearchItem(LibResearch.KEY_ENDER_DISRUPTER,new AspectList().add(Aspect.FLUX, 4).add(Aspect.EXCHANGE, 4).add(Aspect.ELDRITCH, 2),2,-9,2,new ItemStack(ItemEnderDisruption),new ResearchPage("0"),infusionPage(LibResearch.KEY_ENDER_DISRUPTER+0)).setParentsHidden("INFUSION").registerResearchItem().asInstanceOf[TTResearchItem]
  }

  def registerResearchPage() = {
    ResearchCategories.registerCategory(LibResearch.CATEGORY_THAUMICTINKERER, new ResourceLocation(LibResources.MISC_R_ENCHANTING), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"))
  }


  def recipePage(name:String):ResearchPage ={
    new ResearchPage(ConfigResearch.recipes.get(name).asInstanceOf[IRecipe])
  }


  def infusionPage(name:String)=
  {
    new ResearchPage(ConfigResearch.recipes.get(name).asInstanceOf[InfusionRecipe])
  }
}
