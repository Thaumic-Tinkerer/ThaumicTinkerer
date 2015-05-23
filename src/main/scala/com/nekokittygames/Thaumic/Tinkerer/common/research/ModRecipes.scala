package com.nekokittygames.Thaumic.Tinkerer.common.research

import com.nekokittygames.Thaumic.Tinkerer.common.items.quartz.ItemDarkQuartz
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibResearch
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry
import thaumcraft.api.aspects.AspectList
import thaumcraft.common.config.{ConfigBlocks, ConfigResearch}

/**
 * Created by Katrina on 22/05/2015.
 */
object ModRecipes {


  def registerRecipes()=
  {
    def registerConstructRecipes()=
    {

      registerResearchItemC(LibResearch.KEY_DARK_QUARTZ+0,GameRegistry.addShapedRecipe(new ItemStack(ItemDarkQuartz,4),"Q Q"," C ","Q Q",Character.valueOf('Q'),Items.quartz,Character.valueOf('C'),Items.coal))
    }

    registerConstructRecipes()
  }




  def registerResearchItemC(string:String,asList:AnyRef)=
  {
    ConfigResearch.recipes.put(string,asList)
  }
}
