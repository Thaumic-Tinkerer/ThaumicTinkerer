package com.nekokittygames.Thaumic.Tinkerer.common.research

import com.nekokittygames.Thaumic.Tinkerer.common.items.baubles.{ItemEnderDisruption, ItemStabilizerBelt}
import com.nekokittygames.Thaumic.Tinkerer.common.items.quartz.ItemDarkQuartz
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibResearch
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry
import thaumcraft.api.ThaumcraftApi
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.api.crafting.InfusionRecipe
import thaumcraft.api.items.ItemsTC
import thaumcraft.common.config.ConfigResearch

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

    def registerInfusionRecipes(): Unit =
    {
      registerResearchItemI(LibResearch.KEY_STABILIZER_BELT+0,new ItemStack(ItemStabilizerBelt),3,
      new AspectList().add(Aspect.ORDER,12).add(Aspect.EARTH,12).add(Aspect.ARMOR,4).add(Aspect.TRAVEL,8),
      new ItemStack(Blocks.iron_block),
        new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
        new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot),
      new ItemStack(ItemsTC.shard,1,4),new ItemStack(ItemsTC.shard,1,3))

      registerResearchItemI(LibResearch.KEY_ENDER_DISRUPTER+0,new ItemStack(ItemEnderDisruption),4,
        new AspectList().add(Aspect.FLUX,12).add(Aspect.ELDRITCH,6).add(Aspect.EXCHANGE,12),
      new ItemStack(Items.ender_pearl),
        new ItemStack(Items.leather),new ItemStack(ItemsTC.quicksilver),
        new ItemStack(ItemsTC.tainted,1,1),new ItemStack(Items.iron_ingot))
    }

    registerConstructRecipes()
    registerInfusionRecipes()
  }




  def registerResearchItemC(string:String,asList:AnyRef)=
  {
    ConfigResearch.recipes.put(string,asList)
  }



  def registerResearchItemI(name:String, research: String, output: AnyRef, instability: Int, aspects: AspectList, input: ItemStack, stuff: ItemStack*)=
  {
    val recipe:InfusionRecipe=ThaumcraftApi.addInfusionCraftingRecipe(name,output,instability,aspects,input,stuff.toArray)
    ConfigResearch.recipes.put(research,recipe)
  }

  def registerResearchItemI(name:String, output: AnyRef, instability: Int, aspects: AspectList, input: ItemStack, stuff: ItemStack*)=
  {
    val recipe:InfusionRecipe=ThaumcraftApi.addInfusionCraftingRecipe(name,output,instability,aspects,input,stuff.toArray)
    ConfigResearch.recipes.put(name,recipe)
  }
}
