package com.nekokittygames.Thaumic.Tinkerer.common.research

import com.nekokittygames.Thaumic.Tinkerer.common.blocks.quartz.{BlockDarkQuartzPatterned, BlockDarkQuartz}
import com.nekokittygames.Thaumic.Tinkerer.common.core.enums.EnumQuartzType
import com.nekokittygames.Thaumic.Tinkerer.common.items.{ItemMeteorBoots, ItemCometBoots, ItemMobAspect}
import com.nekokittygames.Thaumic.Tinkerer.common.items.baubles.{ItemEnderDisruption, ItemStabilizerBelt}
import com.nekokittygames.Thaumic.Tinkerer.common.items.quartz.ItemDarkQuartz
import com.nekokittygames.Thaumic.Tinkerer.common.libs.LibResearch
import com.nekokittygames.Thaumic.Tinkerer.common.recipes.{InfusedMobAspectInfusionRecipe, CondensedMobAspectRecipe}
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing.Axis
import net.minecraftforge.fml.common.registry.GameRegistry
import thaumcraft.api.ThaumcraftApi
import thaumcraft.api.aspects.{Aspect, AspectList}
import thaumcraft.api.blocks.BlocksTC
import thaumcraft.api.crafting.InfusionRecipe
import thaumcraft.api.items.ItemsTC
import thaumcraft.common.config.{ConfigBlocks, ConfigItems, ConfigResearch}

/**
 * Created by Katrina on 22/05/2015.
 */
object ModRecipes {


  def registerRecipes()=
  {
    def registerConstructRecipes()=
    {

      registerResearchItemC(LibResearch.KEY_DARK_QUARTZ+0,GameRegistry.addShapedRecipe(new ItemStack(ItemDarkQuartz,4),"Q Q"," C ","Q Q",Character.valueOf('Q'),Items.quartz,Character.valueOf('C'),Items.coal))
      registerResearchItemC(LibResearch.KEY_DARK_QUARTZ_BLOCKS+0,GameRegistry.addShapedRecipe(new ItemStack(BlockDarkQuartz),"QQ","QQ",Character.valueOf('Q'),new ItemStack(ItemDarkQuartz)))
      registerResearchItemC(LibResearch.KEY_DARK_QUARTZ_BLOCKS+1,GameRegistry.addShapedRecipe(new ItemStack(BlockDarkQuartzPatterned,1,BlockDarkQuartzPatterned.getMetaFromState(BlockDarkQuartzPatterned.getDefaultState.withProperty(BlockDarkQuartzPatterned.VARIANT,EnumQuartzType.CHISEL).withProperty(BlockDarkQuartzPatterned.AXIS,Axis.X))),"Q",Character.valueOf('Q'),new ItemStack(BlockDarkQuartz)))
      registerResearchItemC(LibResearch.KEY_DARK_QUARTZ_BLOCKS+2,GameRegistry.addShapedRecipe(new ItemStack(BlockDarkQuartzPatterned,1,BlockDarkQuartzPatterned.getMetaFromState(BlockDarkQuartzPatterned.getDefaultState.withProperty(BlockDarkQuartzPatterned.VARIANT,EnumQuartzType.PILLAR).withProperty(BlockDarkQuartzPatterned.AXIS,Axis.X))),"Q",Character.valueOf('Q'),new ItemStack(BlockDarkQuartzPatterned,1,BlockDarkQuartzPatterned.getMetaFromState(BlockDarkQuartzPatterned.getDefaultState.withProperty(BlockDarkQuartzPatterned.VARIANT,EnumQuartzType.CHISEL).withProperty(BlockDarkQuartzPatterned.AXIS,Axis.X)))))
      val cRecipe=new CondensedMobAspectRecipe()
      GameRegistry.addRecipe(cRecipe)
      registerResearchItemC(LibResearch.KEY_MOB_SUMMON+1,cRecipe)

    }

    def registerInfusionRecipes(): Unit =
    {
      registerResearchItemI(LibResearch.KEY_STABILIZER_BELT+0,new ItemStack(ItemStabilizerBelt),3,
      new AspectList().add(Aspect.ORDER,12).add(Aspect.EARTH,12).add(Aspect.PROTECT,4).add(Aspect.MOTION,8),
      new ItemStack(Blocks.iron_block),
        new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot),
        new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot),
      new ItemStack(ItemsTC.shard,1,4),new ItemStack(ItemsTC.shard,1,3))

      registerResearchItemI(LibResearch.KEY_ENDER_DISRUPTER+0,new ItemStack(ItemEnderDisruption),4,
        new AspectList().add(Aspect.FLUX,12).add(Aspect.ELDRITCH,6).add(Aspect.EXCHANGE,12),
      new ItemStack(Items.ender_pearl),
        new ItemStack(Items.leather),new ItemStack(ItemsTC.quicksilver),
        new ItemStack(ItemsTC.tainted,1,1),new ItemStack(Items.iron_ingot))

      registerResearchItemI(LibResearch.KEY_COMET_BOOTS+0,new ItemStack(ItemCometBoots),4,new AspectList().add(Aspect.WATER, 25).add(Aspect.COLD, 25).add(Aspect.FLIGHT, 25).add(Aspect.MOTION,25),new ItemStack(ItemsTC.travellerBoots, 1, 32767), new ItemStack(BlocksTC.crystalWater),
      new ItemStack(Blocks.snow), new ItemStack(Blocks.snow),
      new ItemStack(Blocks.snow), new ItemStack(ItemsTC.focusFrost))

      registerResearchItemI(LibResearch.KEY_METEOR_BOOTS+0,new ItemStack(ItemMeteorBoots),4,new AspectList().add(Aspect.FIRE,25).add(Aspect.ENERGY,25).add(Aspect.FLIGHT,25),new ItemStack(ItemsTC.travellerBoots,1,32767),new ItemStack(BlocksTC.crystalFire),new ItemStack(Blocks.netherrack),new ItemStack(Blocks.netherrack),new ItemStack(Blocks.netherrack),new ItemStack(ItemsTC.focusFire))
      val itemStack=new ItemStack(ItemMobAspect)
      ItemMobAspect.setAspect(itemStack,Aspect.LIFE)
      ItemMobAspect.setInfused(itemStack,true)

      val cItemStack=new ItemStack(ItemMobAspect)
      ItemMobAspect.setAspect(cItemStack,Aspect.LIFE)
      ItemMobAspect.setCondensed(cItemStack,true)
      registerResearchItemSpecial(LibResearch.KEY_MOB_SUMMON+2,LibResearch.KEY_MOB_SUMMON,itemStack,4,
        new AspectList().add(Aspect.LIFE,12).add(Aspect.DEATH,6).add(Aspect.EXCHANGE,12),
        cItemStack,
        classOf[InfusedMobAspectInfusionRecipe],
        cItemStack,cItemStack,cItemStack,cItemStack,cItemStack,cItemStack,cItemStack,cItemStack)


    }

    registerConstructRecipes()
    registerInfusionRecipes()
  }




  def registerResearchItemC(string:String,asList:AnyRef)=
  {
    ConfigResearch.recipes.put(string,asList)
  }



  def registerResearchItemI(name:String, research: String, output: AnyRef, instability: Int, aspects: AspectList, input: ItemStack, stuff: AnyRef*)=
  {
    val recipe:InfusionRecipe=ThaumcraftApi.addInfusionCraftingRecipe(name,output,instability,aspects,input,stuff.toArray)
    ConfigResearch.recipes.put(research,recipe)
  }

  def registerResearchItemI(name:String, output: AnyRef, instability: Int, aspects: AspectList, input: ItemStack, stuff: AnyRef*)=
  {
    val recipe:InfusionRecipe=ThaumcraftApi.addInfusionCraftingRecipe(name,output,instability,aspects,input,stuff.toArray)
    ConfigResearch.recipes.put(name,recipe)
  }

  def registerResearchItemSpecial(name:String,research:String, output: AnyRef, instability: Int, aspects: AspectList, input: ItemStack, clazz:Class[_ <: InfusionRecipe] ,stuff: ItemStack*)=
  {
    val test:Array[Class[_]]=Array[Class[_]](classOf[String],classOf[Any],classOf[Int],classOf[AspectList],classOf[Any],classOf[Array[Object]])
    val recipe:InfusionRecipe=clazz.getConstructor(classOf[Array[String]],classOf[Any],classOf[Int],classOf[AspectList],classOf[Any],classOf[Array[Object]]).newInstance(Array[String](research),output,new Integer(instability),aspects,input,stuff.toArray)
    ThaumcraftApi.getCraftingRecipes.asInstanceOf[java.util.List[InfusionRecipe]].add(recipe)
    ConfigResearch.recipes.put(name,recipe)
  }
}
